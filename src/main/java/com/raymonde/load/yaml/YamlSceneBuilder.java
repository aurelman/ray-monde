/*
 * Copyright (C) 2013 Manoury Aurélien
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.raymonde.load.yaml;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.raymonde.core.Color;
import com.raymonde.core.Vector;
import com.raymonde.load.SceneBuilder;
import com.raymonde.load.SceneBuildingException;
import com.raymonde.render.Camera;
import com.raymonde.render.RenderingSurface;
import com.raymonde.render.light.Light;
import com.raymonde.render.light.OmnidirectionalLight;
import com.raymonde.render.material.*;
import com.raymonde.render.primitive.Plane;
import com.raymonde.render.primitive.Primitive;
import com.raymonde.render.primitive.Sphere;
import com.raymonde.scene.Scene;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * This {@link SceneBuilder} reads a {@link Scene} object from a Yaml file.
 */
public class YamlSceneBuilder implements SceneBuilder {

    private static final Logger logger = LoggerFactory.getLogger(YamlSceneBuilder.class);

    private File file;

    public YamlSceneBuilder(final String filename) {
        file = new File(filename);
    }

    public YamlSceneBuilder(final File file) {
        this.file = file;
    }

    public YamlSceneBuilder() {
    }

    @Override
    public String getName() {
        return "yaml";
    }

    @Override
    public Scene build() throws SceneBuildingException {
        val yaml = new Yaml();
        Scene scene = null;

        try (FileReader fis = new FileReader(file)) {
            val yamlReader = new YamlReader(fis, );
            Map<String, Object> config = (Map<String, Object>) yamlReader.read();
            Map<String, Object> sceneConfig = (Map<String, Object>) config.get("scene");
            scene = parseScene(sceneConfig);
        } catch (FileNotFoundException ex) {
            logger.error("scene file {} cannot be found", file.getAbsolutePath(), ex);
        } catch (IOException ex) {
            logger.error("unable to read file {}", file.getAbsolutePath(), ex);
        }
        return scene;
    }

    public YamlSceneBuilder setFile(final File file) {
        this.file = file;
        return this;
    }

    public YamlSceneBuilder setFile(final String filename) {
        this.file = new File(filename);
        return this;
    }

    private Scene parseScene(final Map<String, Object> sceneConfig) {
        val scene = new Scene();
        scene.setAmbientColor(parseColor((Map<String, Double>) sceneConfig.get("ambient")));

        Map<String, Object> cameraConfig = (Map<String, Object>) sceneConfig.get("camera");
        Camera camera = parseCamera(cameraConfig);
        scene.addCamera((String) cameraConfig.get("name"), camera);

        /*
        RenderingSurface surface = parseSurface((Map<String, Object>)sceneConfig.get("surface"));
        scene.setSurface(surface);
        */

        Collection<Map> primitivesConfig = (Collection<Map>) sceneConfig.get("primitives");
        for (Map<String, Object> primitive : primitivesConfig) {
            scene.addPrimitive((String) primitive.get("name"), parsePrimitive(primitive));
        }

        Collection<Map> lightsConfig = (Collection<Map>) sceneConfig.get("lights");
        for (Map<String, Object> light : lightsConfig) {
            scene.addLight((String) light.get("name"), parseLight(light));
        }

        return scene;
    }

    // TODO: should be delegated to the camera parsing
    @Deprecated
    private RenderingSurface parseSurface(final Map<String, Object> surfaceConfig) {
        Map<String, Integer> dimension = (Map<String, Integer>) surfaceConfig.get("dimension");
        Vector position = parseVector((Map<String, Double>) surfaceConfig.get("position"));
        return new RenderingSurface(position, dimension.get("width"), dimension.get("height"));
    }

    private Light parseLight(final Map<String, Object> lightConfig) {
        String type = (String) lightConfig.get("type");

        Light light;
        switch (type) {
            case "omnidirectional":
                light = parseOmnidirectionalLight(lightConfig);
                break;
            default:
                light = parseOmnidirectionalLight(lightConfig);
        }

        return light;
    }

    private Light parseOmnidirectionalLight(Map<String, Object> lightConfig) {
        Double attenuation = (Double) lightConfig.get("attenuation");
        Vector position = parseVector((Map<String, Double>) lightConfig.get("position"));
        Color color = parseColor((Map<String, Double>) lightConfig.get("color"));
        return new OmnidirectionalLight(position, color, new Vector(attenuation, 0., 0.));
    }

    private Primitive parsePrimitive(Map<String, Object> primitiveConfig) {
        String type = (String) primitiveConfig.get("type");

        Primitive primitive;
        switch (type) {
            case "sphere":
                primitive = parseSphere(primitiveConfig);
                break;
            case "plane":
                primitive = parsePlane(primitiveConfig);
                break;
            default:
                primitive = parseSphere(primitiveConfig);
        }

        primitive.setMaterial(parseMaterial((Map<String, Object>) primitiveConfig.get("material")));
        return primitive;
    }

    private Primitive parsePlane(final Map<String, Object> primitiveConfig) {
        return new Plane(parseVector((Map<String, Double>) primitiveConfig.get("normal")), (Double) primitiveConfig.get("distance"));
    }

    private Primitive parseSphere(final Map<String, Object> primitiveConfig) {
        return new Sphere(
                parseVector((Map<String, Double>) primitiveConfig.get("position")),
                (Double) primitiveConfig.get("radius"));
    }

    private Camera parseCamera(final Map<String, Object> cameraConfig) {

        Camera camera = Camera.builder()
                .direction(parseVector((Map) cameraConfig.get("direction")))
                .position(parseVector((Map) cameraConfig.get("position")))
                .distance(1.0)
                .width(4.0)
                .height(3.0)
                .pixelWidth(1900)
                .pixelHeight(1080)
                .build();

        return camera;
    }

    private Material parseMaterial(final Map<String, Object> materialConfig) {
        String type = (String) materialConfig.get("type");
        Material material;
        switch (type) {
            case "color":
                material = parseColorMaterial(materialConfig);
                break;
            case "phong":
                material = parsePhongMaterial(materialConfig);
                break;
            case "refractive":
                material = parseRefractiveMaterial(materialConfig);
                break;
            case "reflective":
                material = parseReflectiveMaterial(materialConfig);
                break;
            default:
                material = parseColorMaterial(materialConfig);
        }

        if (materialConfig.containsKey("material")) {
            material.setMaterial(parseMaterial((Map<String, Object>) materialConfig.get("material")));
        }

        return material;
    }

    private Material parsePhongMaterial(Map<String, Object> materialConfig) {
        //diffuse: 0.8
        //specular: 12.
        Double diffuse = (Double) materialConfig.get("diffuse");
        Double specular = (Double) materialConfig.get("specular");
        return new PhongMaterial(diffuse, specular);
    }

    private Material parseRefractiveMaterial(Map<String, Object> materialConfig) {
        Double refraction = (Double) materialConfig.get("refraction");
        return new RefractiveMaterial(refraction);
    }

    private Material parseReflectiveMaterial(Map<String, Object> materialConfig) {
        Double reflectivity = (Double) materialConfig.get("reflectivity");
        return new ReflectiveMaterial(reflectivity);
    }

    private Material parseColorMaterial(Map<String, Object> materialConfig) {
        Color color = parseColor((Map<String, Double>) materialConfig.get("color"));
        return new ColorMaterial(color);
    }

    private Color parseColor(final Map<String, Double> colorConfig) {
        return new Color(colorConfig.get("r"), colorConfig.get("g"), colorConfig.get("b"));
    }

    private Vector parseVector(final Map<String, Double> vectorConfig) {
        return new Vector(vectorConfig.get("x"), vectorConfig.get("y"), vectorConfig.get("z"));
    }
}
