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
import com.raymonde.render.primitive.Triangle;
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
import java.util.List;
import java.util.Map;

/**
 * This {@link SceneBuilder} allows to read a {@link Scene scene} from a YAML file.
 */
public class YamlSceneBuilder implements SceneBuilder {

    private static final Logger logger = LoggerFactory.getLogger(YamlSceneBuilder.class);

    /**
     * The file that describes the scene to render.
     */
    private File file;

    @Override
    public String getName() {
        return "yaml";
    }

    @Override
    @SuppressWarnings("unchecked")
    public Scene build() throws SceneBuildingException {

        try (FileReader fis = new FileReader(file)) {
            val yaml = new Yaml();
            Map<String, Object> config = yaml.loadAs(fis, Map.class);
            Map<String, Object> sceneConfig = (Map<String, Object>) config.get("scene");

            return parseScene(sceneConfig);
        } catch (FileNotFoundException ex) {
            logger.error("scene file {} cannot be found", file.getAbsolutePath(), ex);
            throw new SceneBuildingException("scene file cannot be found", ex);
        } catch (IOException ex) {
            logger.error("scene file {} cannot be parsed", file.getAbsolutePath(), ex);
            throw new SceneBuildingException("scene file cannot be parsed", ex);
        }
    }

    /**
     * Allow to specify the file that describes the scene to render.
     *
     * @param file the {@link File} object pointing to the file
     * @return itself (allow to chain calls)
     *
     * @see #fromFile(File)
     */
    public YamlSceneBuilder fromFile(final File file) {
        this.file = file;
        return this;
    }

    /**
     * Allow to specify the file that describes the scene to render.
     *
     * @param filename the path to the file
     * @return itself (allow to chain calls)
     *
     * @see #fromFile(File)
     */
    public YamlSceneBuilder fromFile(final String filename) {
        this.file = new File(filename);
        return this;
    }

    @SuppressWarnings("unchecked")
    private Scene parseScene(final Map<String, Object> sceneConfig) {
        val scene = new Scene();
        scene.setAmbientColor(parseColor(sceneConfig.get("ambient")));

        Map<String, Object> cameraConfig = castAs(sceneConfig.get("camera"), Map.class);
        Camera camera = parseCamera(cameraConfig);
        scene.addCamera(cameraConfig.get("name").toString(), camera);

        Collection<Map> primitivesConfig = castAs(sceneConfig.get("primitives"), Collection.class);
        for (Map<String, Object> primitive : primitivesConfig) {
            scene.addPrimitive(primitive.get("name").toString(), parsePrimitive(primitive));
        }

        Collection<Map> lightsConfig = castAs(sceneConfig.get("lights"), Collection.class);
        for (Map<String, Object> light : lightsConfig) {
            scene.addLight(light.get("name").toString(), parseLight(light));
        }

        return scene;
    }

    private static final <T> T castAs(Object object, Class<T> targetClass) {
        return targetClass.cast(object);
    }

    private Light parseLight(final Map<String, Object> lightConfig) {
        String type = (String) lightConfig.get("type");

        switch (type) {
            case "omnidirectional":
                return parseOmnidirectionalLight(lightConfig);
            default:
                return parseOmnidirectionalLight(lightConfig);
        }
    }

    @SuppressWarnings("unchecked")
    private Light parseOmnidirectionalLight(Map<String, Object> lightConfig) {
        return OmnidirectionalLight.builder()
                .position(parseVector(lightConfig.get("position")))
                .color(parseColor(lightConfig.get("color")))
                .attenuation(new Vector((double) lightConfig.get("attenuation"), 0., 0.))
                .build();
    }

    @SuppressWarnings("unchecked")
    private Primitive parsePrimitive(Map<String, Object> primitiveConfig) {
        String type = (String) primitiveConfig.get("type");

        switch (type) {
            case "sphere":
                return parseSphere(primitiveConfig);
            case "plane":
                return parsePlane(primitiveConfig);
            case "triangle":
                return parseTriangle(primitiveConfig);
            default:
                return parseSphere(primitiveConfig);
        }
    }

    private Primitive parseTriangle(final Map<String, Object> primitiveConfig) {

        List<Object> points = castAs(primitiveConfig.get("points"), List.class);

        return Triangle.builder()
                .first(parseVector(points.get(0)))
                .second(parseVector(points.get(0)))
                .third(parseVector(points.get(0)))
                .material(parseMaterial(primitiveConfig.get("material")))
                .build();
    }

    private Primitive parsePlane(final Map<String, Object> primitiveConfig) {
        return Plane.builder()
                .normal(parseVector(primitiveConfig.get("normal")))
                .distance((double) primitiveConfig.get("distance"))
                .material(parseMaterial(primitiveConfig.get("material")))
                .build();
    }

    private Primitive parseSphere(final Map<String, Object> primitiveConfig) {
        return Sphere.builder()
                .origin(parseVector(primitiveConfig.get("position")))
                .radius((double) primitiveConfig.get("radius"))
                .material(parseMaterial(primitiveConfig.get("material")))
                .build();
    }


    private Camera parseCamera(final Map<String, Object> config) {

        @SuppressWarnings("unchecked") Map<String, Object> surfaceConfig = castAs(config.get("surface"), Map.class);
        Dimensions<Double> surfaceDimensions = parseDimensions(surfaceConfig.get("dimensions"));
        Dimensions<Integer> pixelDimensions = parseDimensions(surfaceConfig.get("pixels"));

        return Camera.builder()
                .direction(parseVector(config.get("direction")))
                .position(parseVector(config.get("position")))
                .up(parseVector(config.get("up")))
                .distance((double)surfaceConfig.get("distance"))
                .width(surfaceDimensions.getWidth())
                .height(surfaceDimensions.getHeight())
                .pixelWidth(pixelDimensions.getWidth())
                .pixelHeight(pixelDimensions.getHeight())
                .build();
    }

    private Material parseMaterial(Object materialConfig) {

        @SuppressWarnings("unchecked") final Map<String, Object> config = (Map<String, Object>)materialConfig;

        String type = (String) config.get("type");
        Material material;
        switch (type) {
            case "color":
                material = parseColorMaterial(config);
                break;
            case "phong":
                material = parsePhongMaterial(config);
                break;
            case "refractive":
                material = parseRefractiveMaterial(config);
                break;
            case "reflective":
                material = parseReflectiveMaterial(config);
                break;
            default:
                material = parseColorMaterial(config);
        }

        return material;
    }

    private Material parseSubMaterialIfExists(Map<String, Object> rootMaterialConfig) {
        if (rootMaterialConfig.containsKey("material")) {
            return parseMaterial(rootMaterialConfig.get("material"));
        }
        return null;
    }

    private Material parsePhongMaterial(Map<String, Object> materialConfig) {
        //diffuse: 0.8
        //specular: 12.
        Material subMaterial = parseSubMaterialIfExists(materialConfig);

        return PhongMaterial.builder()
                .diffuse((double) materialConfig.get("diffuse"))
                .specular((double) materialConfig.get("specular"))
                .subMaterial(subMaterial)
                .build();
    }

    private Material parseRefractiveMaterial(Map<String, Object> materialConfig) {
        Material subMaterial = parseSubMaterialIfExists(materialConfig);

        return RefractiveMaterial.builder()
                .refraction((double) materialConfig.get("refraction"))
                .subMaterial(subMaterial)
                .build();
    }

    private Material parseReflectiveMaterial(Map<String, Object> materialConfig) {
        Material subMaterial = parseSubMaterialIfExists(materialConfig);

        return ReflectiveMaterial.builder()
                .reflectivity((double)materialConfig.get("reflectivity"))
                .subMaterial(subMaterial)
                .build();
    }

    private Material parseColorMaterial(Map<String, Object> materialConfig) {
        Material subMaterial = parseSubMaterialIfExists(materialConfig);

        return ColorMaterial.builder()
                .color(parseColor(materialConfig.get("color")))
                .subMaterial(subMaterial)
                .build();
    }

    private Color parseColor(final Object colorConfig) {
        @SuppressWarnings("unchecked") Map<String, Double> conf = (Map<String, Double>)colorConfig;
        return Color.builder()
                .r(conf.get("r"))
                .g(conf.get("g"))
                .b(conf.get("b"))
                .build();
    }

    private Vector parseVector(final Object vectorConfig) {
        @SuppressWarnings("unchecked") Map<String, Double> conf = castAs(vectorConfig, Map.class);
        return Vector.builder()
                .x(conf.get("x"))
                .y(conf.get("y"))
                .z(conf.get("z"))
                .build();
    }

    private <T> Dimensions<T> parseDimensions(final Object config) {
        @SuppressWarnings("unchecked") Map<String, T> dimensionsConfig = castAs(config, Map.class);
        return new Dimensions<>(dimensionsConfig.get("width"), dimensionsConfig.get("height"));
    }

    private static class Dimensions<T> {
        private T width;
        private T height;

        public Dimensions(T width, T height) {
            this.width = width;
            this.height = height;
        }

        public T getWidth() {
            return this.width;
        }

        public T getHeight() {
            return this.height;
        }
    }
}
