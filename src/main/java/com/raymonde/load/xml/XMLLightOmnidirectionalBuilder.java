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
import com.raymonde.core.Vector;
import com.raymonde.load.ParsingException;
import com.raymonde.render.light.Light;
import com.raymonde.render.light.OmnidirectionalLight;
import org.w3c.dom.Element;

/**
 * <code>XMLLighOmnidirectionalBuilder</code> builds omnidirectional light from
 * an XML element which describes it.
 * 
 * @author aurelman
 */
public class XMLLightOmnidirectionalBuilder extends XMLLightBuilder {

    /**
     * The name attribute name.
     */
    private static final String NAME_ATTRIBUTE_NAME = "name";

    /**
     * The position element name.
     */
    private static final String POSITION_ELEMENT_NAME = "position";

    /**
     * The color element name.
     */
    private static final String COLOR_ELEMENT_NAME = "color";

    /**
     * The attenuation element name.
     */
    private static final String ATTENUATION_ELEMENT_NAME = "attenuation";

    /**
     * The value attribute name.
     */
    private static final String VALUE_ATTRIBUTE_NAME = "value";

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
     * The name of the x attribute.
     */
    private static final String X_ATTRIBUTE_NAME = "x";

    /**
     * The name of the y attribute.
     */
    private static final String Y_ATTRIBUTE_NAME = "y";

    /**
     * The name of the z attribute.
     */
    private static final String Z_ATTRIBUTE_NAME = "z";

    /**
     * Standard message.
     */
    private static final String NODE_MISSING_MESSAGE =
            "missing <%s> node for omnidirectional light [%s]";

    /**
     * Standard message.
     */
    private static final String MALFORMED_ATTRIBUTE_MESSAGE =
            "malformed value for attribute <%s> in element <%s>";
    
    @Override
    public Light build(final Element element) throws ParsingException {

        String name = element.getAttribute(
                XMLLightOmnidirectionalBuilder.NAME_ATTRIBUTE_NAME);

        // Color
        String eltName = XMLLightOmnidirectionalBuilder.COLOR_ELEMENT_NAME;
        Element elt = (Element)element.getElementsByTagName(eltName).item(0);
        if (elt == null) {
            throw new ParsingException(
                String.format(
                    XMLLightOmnidirectionalBuilder.NODE_MISSING_MESSAGE,
                    eltName, name ));
        }

        String attributeName = "";
        double r, g, b;
        try {
            attributeName = XMLLightOmnidirectionalBuilder.RED_ATTRIBUTE_NAME;
            r = Double.parseDouble(elt.getAttribute(attributeName));
            attributeName = XMLLightOmnidirectionalBuilder.GREEN_ATTRIBUTE_NAME;
            g = Double.parseDouble(elt.getAttribute(attributeName));
            attributeName = XMLLightOmnidirectionalBuilder.BLUE_ATTRIBUTE_NAME;
            b = Double.parseDouble(elt.getAttribute(attributeName));
        } catch (NumberFormatException ex) {
            throw new ParsingException(
                String.format(
                    XMLLightOmnidirectionalBuilder.MALFORMED_ATTRIBUTE_MESSAGE,
                    attributeName, eltName),
                ex);
        }
        
        Color color = new Color(r, g, b);

        // Position
        eltName = XMLLightOmnidirectionalBuilder.POSITION_ELEMENT_NAME;
        elt = (Element)element.getElementsByTagName(eltName).item(0);
        if (elt == null) {
            throw new ParsingException(
                String.format(
                    XMLLightOmnidirectionalBuilder.NODE_MISSING_MESSAGE,
                    eltName, name ));
        }
        
        double x, y, z;
        try {
            attributeName = XMLLightOmnidirectionalBuilder.X_ATTRIBUTE_NAME;
            x = Double.parseDouble(elt.getAttribute(attributeName));
            attributeName = XMLLightOmnidirectionalBuilder.Y_ATTRIBUTE_NAME;
            y = Double.parseDouble(elt.getAttribute(attributeName));
            attributeName = XMLLightOmnidirectionalBuilder.Z_ATTRIBUTE_NAME;
            z = Double.parseDouble(elt.getAttribute(attributeName));
        } catch (NumberFormatException ex) {
            throw new ParsingException(
                String.format(
                    XMLLightOmnidirectionalBuilder.MALFORMED_ATTRIBUTE_MESSAGE,
                    attributeName, eltName),
                ex);
        }

        Vector position = new Vector(x, y, z);

        // Attenuation
        eltName = XMLLightOmnidirectionalBuilder.ATTENUATION_ELEMENT_NAME;
        elt = (Element)element.getElementsByTagName(eltName).item(0);
        if (elt == null) {
            throw new ParsingException(
                String.format(
                    XMLLightOmnidirectionalBuilder.NODE_MISSING_MESSAGE,
                    eltName, name ));
        }
        
        double attenuation;
        try {
            attributeName = XMLLightOmnidirectionalBuilder.VALUE_ATTRIBUTE_NAME;
            attenuation = Double.parseDouble(elt.getAttribute(attributeName));
        } catch (NumberFormatException ex) {
            throw new ParsingException(
                String.format(
                    XMLLightOmnidirectionalBuilder.MALFORMED_ATTRIBUTE_MESSAGE,
                    attributeName, eltName),
                ex);
        }
        
        return new OmnidirectionalLight(
                name, position, color, new Vector(attenuation, 0., 0.));
    }
}
