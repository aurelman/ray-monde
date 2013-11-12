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

import com.raymonde.core.Vector;
import com.raymonde.load.ParsingException;
import com.raymonde.render.Camera;
import org.w3c.dom.Element;

/**
 *
 * @author Aurélien
 */
public class XMLCameraBuilder {
    
    /**
     * The position element name.
     */
    private static final String POSITION_ELEMENT_NAME = "position";

    /**
     * The direction element name.
     */
    private static final String DIRECTION_ELEMENT_NAME = "direction";

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
            "missing <%s> node for surface";

    /**
     * Standard message.
     */
    private static final String MALFORMED_ATTRIBUTE_MESSAGE =
            "malformed value for attribute <%s> in element <%s>";
    

    /**
     * Builds a <code>Camera</code> object from the specified XML element.
     * 
     * @param element The XML element which describes the camera.
     *
     * @return The resulting camera.
     * 
     * @throws ParsingException If an errors occurs during parsing operation.
     */
    public Camera build(final Element element) throws ParsingException {
        // Position
        String eltName = XMLCameraBuilder.POSITION_ELEMENT_NAME;
        Element elt = (Element)element.getElementsByTagName(eltName).item(0);
        if (elt == null) {
            throw new ParsingException(
                    String.format(XMLCameraBuilder.NODE_MISSING_MESSAGE,
                    eltName));
        }
        
        String attributeName = "";

        double x, y, z;
        try {
            attributeName = XMLCameraBuilder.X_ATTRIBUTE_NAME;
            x = Double.parseDouble(elt.getAttribute(attributeName));

            attributeName = XMLCameraBuilder.Y_ATTRIBUTE_NAME;
            y = Double.parseDouble(elt.getAttribute(attributeName));

            attributeName = XMLCameraBuilder.Z_ATTRIBUTE_NAME;
            z = Double.parseDouble(elt.getAttribute(attributeName));
        } catch (NumberFormatException ex) {
            throw new ParsingException(
                String.format(
                    XMLCameraBuilder.MALFORMED_ATTRIBUTE_MESSAGE,
                    attributeName, eltName),
                ex);
        }

        Vector position = new Vector(x, y, z);

        // Direction
        eltName = XMLCameraBuilder.DIRECTION_ELEMENT_NAME;
        elt = (Element)element.getElementsByTagName(eltName).item(0);
        if (elt == null) {
            throw new ParsingException(
                    String.format(XMLCameraBuilder.NODE_MISSING_MESSAGE,
                    eltName));
        }
        
        try {
            attributeName = XMLCameraBuilder.X_ATTRIBUTE_NAME;
            x = Double.parseDouble(elt.getAttribute(attributeName));

            attributeName = XMLCameraBuilder.Y_ATTRIBUTE_NAME;
            y = Double.parseDouble(elt.getAttribute(attributeName));

            attributeName = XMLCameraBuilder.Z_ATTRIBUTE_NAME;
            z = Double.parseDouble(elt.getAttribute(attributeName));
        } catch (NumberFormatException ex) {
            throw new ParsingException(
                String.format(
                    XMLCameraBuilder.MALFORMED_ATTRIBUTE_MESSAGE,
                    attributeName, eltName));
        }

        Vector direction = new Vector(x, y, z);
        
        return new Camera("", position, direction);
    }
}
