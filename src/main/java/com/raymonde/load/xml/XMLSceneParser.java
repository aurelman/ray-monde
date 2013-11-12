/*
 * Copyright (C) 2009 Manoury Aurélien
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

package com.raymonde.load.xml;

import com.raymonde.core.Color;
import com.raymonde.load.ParsingException;
import com.raymonde.load.SceneParser;
import com.raymonde.render.Camera;
import com.raymonde.render.light.Light;
import com.raymonde.render.material.AbstractMaterial;
import com.raymonde.render.primitive.Primitive;
import com.raymonde.scene.Scene;
import com.raymonde.render.Surface;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <code>XMLParser</code> is able to load a scene from a specified xml file.
 * 
 * @author aurelman
 */
public class XMLSceneParser implements SceneParser {
    
    /**
     * Available logger.
     */
    private static final Logger LOGGER =
            Logger.getLogger(XMLSceneParser.class.getName());

    /**
     * The light element name.
     */
    private static final String LIGHTS_ELEMENT_NAME = "lights";
    
    /**
     * The light element name.
     */
    private static final String LIGHT_ELEMENT_NAME = "light";

    /**
     * The material element name.
     */
    private static final String MATERIAL_ELEMENT_NAME = "material";

    /**
     * The primitives element name.
     */
    private static final String PRIMITIVES_ELEMENT_NAME = "primitives";

    /**
     * The camera element name.
     */
    private static final String CAMERA_ELEMENT_NAME = "camera";

    /**
     * The surface element name.
     */
    private static final String SURFACE_ELEMENT_NAME = "surface";

    /**
     * The primitive element name.
     */
    private static final String PRIMITIVE_ELEMENT_NAME = "primitive";

    /**
     * The name of the type attribute.
     */
    private static final String TYPE_ATTRIBUTE_NAME = "type";

     /**
     * The name of the color element.
     */
    private static final String BACKGROUND_ELEMENT_NAME = "ambient";

    /**
     * The name of the red attribute.
     */
    private static final String RED_ATTRIBUTE_NAME = "r";

    /**
     * The name of the green attribute.
     */
    private static final String GREEN_ATTRIBUTE_NAME = "g";

    /**
     * The name of the blue attribute.
     */
    private static final String BLUE_ATTRIBUTE_NAME = "b";

    /**
     * The primitive factory.
     */
    private XMLPrimitiveBuilderFactory primitiveBuilderFactory =
            new XMLPrimitiveBuilderFactory();

    /**
     * The light factory
     */
    private XMLLightBuilderFactory lightBuilderFactory =
            new XMLLightBuilderFactory();

    /**
     * The material factory.
     */
    private XMLMaterialBuilderFactory materialBuilderFactory =
            new XMLMaterialBuilderFactory();

    /**
     * Constructs a default <code>XMLSceneParser</code>.
     */
    public XMLSceneParser() {
    }
    
    public Scene parseScene(final Object toParse)
            throws ParsingException {
        Element root = (Element)toParse;

        Scene res = new Scene();
        
        Element currElt = (Element)root.getElementsByTagName(
                XMLSceneParser.BACKGROUND_ELEMENT_NAME).item(0);
        if (currElt == null) {
            throw new ParsingException("No ambient defined for scene");
        }
        double r = Double.parseDouble(currElt.getAttribute(
                XMLSceneParser.RED_ATTRIBUTE_NAME));
        double g = Double.parseDouble(currElt.getAttribute(
                XMLSceneParser.GREEN_ATTRIBUTE_NAME));
        double b = Double.parseDouble(currElt.getAttribute(
                XMLSceneParser.BLUE_ATTRIBUTE_NAME));

        res.setAmbiantColor(new Color(r, g, b));

        // Primitives
        currElt = (Element)root.getElementsByTagName(
                XMLSceneParser.PRIMITIVES_ELEMENT_NAME).item(0);
        if (currElt == null) {
            throw new ParsingException("No primitive defined for scene");
        }
        res.addPrimitives(parsePrimitives(currElt));

        // Lights
        currElt = (Element)root.getElementsByTagName(
                XMLSceneParser.LIGHTS_ELEMENT_NAME).item(0);
        if (currElt == null) {
            throw new ParsingException("No light defined for scene");
        }
        res.addLights(parseLights(currElt));

        // Camera
        currElt = (Element)root.getElementsByTagName(
                XMLSceneParser.CAMERA_ELEMENT_NAME).item(0);
        if (currElt == null) {
            throw new ParsingException("No camera defined for scene");
        }
        res.addCamera(parseCamera(currElt));

        // Surface
        currElt = (Element)root.getElementsByTagName(
                XMLSceneParser.SURFACE_ELEMENT_NAME).item(0);
        if (currElt == null) {
            throw new ParsingException("No surface defined for scene");
        }
        res.setSurface(parseSurface(currElt));
        
        return res;
    }

    @Override
    public Scene parseFile(final String filename)
            throws ParsingException  {
        getLogger().info("Start parsing file " + filename);
        double start = System.currentTimeMillis();
        Scene scene = parseScene(getRoot(filename));
        double elapsed = System.currentTimeMillis() - start;
        getLogger().info(
                "Finish parsing file " + filename + " in " + elapsed + " ms");
        return scene;
    }
    
    public Primitive parsePrimitive(final Object toParse)
            throws ParsingException {
        Element primitiveElt = (Element)toParse;
        String type = primitiveElt
                    .getAttribute(XMLSceneParser.TYPE_ATTRIBUTE_NAME);

        XMLPrimitiveBuilder builder =
                getPrimitiveBuilderFactory().forType(type);

        Primitive res = builder.build(primitiveElt);

        Element materialElt = (Element)primitiveElt.getElementsByTagName(
                XMLSceneParser.MATERIAL_ELEMENT_NAME).item(0);

        res.setMaterial(parseMaterial(materialElt));
        return res;
    }

    public AbstractMaterial parseMaterial(final Object toParse)
            throws ParsingException {
        
        Element materialElt = (Element)toParse;
        String type = materialElt
                    .getAttribute(XMLSceneParser.TYPE_ATTRIBUTE_NAME);

        XMLMaterialBuilder builder = getMaterialBuilderFactory().forType(type);

        Element subMaterialElt = (Element)materialElt.getElementsByTagName(
                XMLSceneParser.MATERIAL_ELEMENT_NAME).item(0);

        AbstractMaterial res = builder.build(materialElt);
        
        if (subMaterialElt != null) {
            res.setMaterial(parseMaterial(subMaterialElt));
        }
        
        return res;
    }

    public Light parseLight(final Object toParse)
            throws ParsingException {
        Element lightElt = (Element)toParse;
        String type = lightElt
                    .getAttribute(XMLSceneParser.TYPE_ATTRIBUTE_NAME);

        XMLLightBuilder builder = getLightBuilderFactory().forType(type);

        Light res = builder.build(lightElt);
        return res;
    }

    public Camera parseCamera(final Object toParse) throws ParsingException {
        XMLCameraBuilder builder = new XMLCameraBuilder();
        return builder.build((Element)toParse);
    }

    public Surface parseSurface(final Object toParse) throws ParsingException {
        XMLSurfaceBuilder builder = new XMLSurfaceBuilder();
        return builder.build((Element)toParse);
    }

    /**
     * Parse the <code>&lt;primitives&gt;</code> tag.
     * 
     * @param primitives The element corresponding to the
     *  <code>&lt;primitives&gt;</code> tag.
     *
     * @return The corresponding <code>Primitive</code> collection
     */
    protected Collection<Primitive> parsePrimitives(final Element primitives)
            throws ParsingException {

        NodeList primitivesList = primitives.getElementsByTagName(
                XMLSceneParser.PRIMITIVE_ELEMENT_NAME);

        int length = primitivesList.getLength();
        ArrayList<Primitive> list = new ArrayList<Primitive>(length);

        for (int i = 0; i < length; i++) {
            list.add(parsePrimitive(primitivesList.item(i)));
        }

        return list;
    }

    /**
     * Parse the <code>&lt;lights&gt;</code> tag.
     *
     * @param lights The element corresponding to the
     *  <code>&lt;lights&gt;</code> tag.
     *
     * @return The corresponding <code>Light</code> collection
     */
    protected Collection<Light> parseLights(final Element lights)
            throws ParsingException {

        NodeList lightsList = lights.getElementsByTagName(
                XMLSceneParser.LIGHT_ELEMENT_NAME);

        int length = lightsList.getLength();

        ArrayList<Light> list = new ArrayList<Light>(length);

        for (int i = 0; i < length; i++) {
            list.add(parseLight(lightsList.item(i)));
        }

        return list;
    }
    
    
    /**
     * Returns the root element of the specified xml filename.
     * 
     * @param filename The filename.
     * 
     * @return The root element.
     *
     * @throws ParsingException When an error occurs during parsing operation.
     */
    private Element getRoot(final String filename) throws ParsingException  {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = null;
        File xml = new File(filename);
        Document document = null;

        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(xml);
        } catch (ParserConfigurationException ex) {
            getLogger().log(Level.SEVERE, null, ex);
            throw new ParsingException("Unable to parse the file", ex);
        } catch (SAXException ex) {
            getLogger().log(Level.SEVERE, null, ex);
            throw new ParsingException("Unable to parse the file", ex);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, null, ex);
            throw new ParsingException("Unable to locate the file ", ex);
        }

        Element root = document.getDocumentElement();

        return root;
    }

    
    
    /**
     * Returns the available logger.
     * 
     * @return The available logger.
     */
    protected Logger getLogger() {
        return XMLSceneParser.LOGGER;
    }

    /**
     * Returns the available <code>PrimitiveBuilderFactory</code>.
     * 
     * @return The available <code>PrimitiveBuilderFactory</code>.
     */
    public XMLPrimitiveBuilderFactory getPrimitiveBuilderFactory() {
        return primitiveBuilderFactory;
    }

    /**
     * Sets the <code>PrimitiveBuilderFactory</code>.
     *
     * @param primitiveBuilderFactory The <code>PrimitiveBuilderFactory</code>.
     */
    public void setPrimitiveBuilderFactory(
            final XMLPrimitiveBuilderFactory primitiveBuilderFactory) {
        this.primitiveBuilderFactory = primitiveBuilderFactory;
    }

    /**
     * Returns the available <code>LightBuilderFactory</code>.
     *
     * @return The available <code>LightBuilderFactory</code>.
     */
    public XMLLightBuilderFactory getLightBuilderFactory() {
        return lightBuilderFactory;
    }

    /**
     * Sets the <code>LightBuilderFactory</code>.
     *
     * @param lightBuilderFactory The <code>LightBuilderFactory</code>.
     */
    public void setLightBuilderFactory(
            final XMLLightBuilderFactory lightBuilderFactory) {
        this.lightBuilderFactory = lightBuilderFactory;
    }

    /**
     * Returns the available <code>MaterialBuilderFactory</code>.
     *
     * @return The available <code>MaterialBuilderFactory</code>.
     */
    public XMLMaterialBuilderFactory getMaterialBuilderFactory() {
        return materialBuilderFactory;
    }

    /**
     * Sets the <code>MaterialBuilderFactory</code>.
     *
     * @param materialBuilderFactory The <code>MaterialBuilderFactory</code>.
     */
    public void setMaterialBuilderFactory(
            final XMLMaterialBuilderFactory materialBuilderFactory) {
        this.materialBuilderFactory = materialBuilderFactory;
    }
}
