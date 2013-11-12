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
import com.raymonde.render.primitive.Primitive;
import com.raymonde.render.primitive.Plane;
import org.w3c.dom.Element;

/**
 *
 * @author aurelman
 */
public class XMLPrimitivePlaneBuilder extends XMLPrimitiveBuilder {

    /**
     * The name attribute name.
     */
    private static final String NAME_ATTRIBUTE_NAME = "name";

    /**
     * The position element name.
     */
    private static final String NORMAL_ELEMENT_NAME = "normal";

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
     * The radius element name.
     */
    private static final String DISTANCE_ELEMENT_NAME = "distance";

    /**
     * The value attribute name.
     */
    private static final String VALUE_ATTRIBUTE_NAME = "value";

    /**
     * Standard message.
     */
    private static final String NODE_MISSING_MESSAGE =
            "missing <%s> node for sphere [%s]";

    /**
     * Standard message.
     */
    private static final String MALFORMED_ATTRIBUTE_MESSAGE =
            "malformed value for attribute <%s> in element <%s>";
    
    @Override
    public Primitive build(final Element element) throws ParsingException {
        String name = element.getAttribute(
                XMLPrimitivePlaneBuilder.NAME_ATTRIBUTE_NAME);

        // Position
        String eltName = XMLPrimitivePlaneBuilder.NORMAL_ELEMENT_NAME;
        Element elt = (Element)element.getElementsByTagName(eltName).item(0);
        if (elt == null) {
            throw new ParsingException(
                String.format(XMLPrimitivePlaneBuilder.NODE_MISSING_MESSAGE,
                    eltName, name));
        }

        String attributeName = "";
        double x, y, z;
        try {
            attributeName = XMLPrimitivePlaneBuilder.X_ATTRIBUTE_NAME;
            x = Double.parseDouble(elt.getAttribute(attributeName));
            attributeName = XMLPrimitivePlaneBuilder.Y_ATTRIBUTE_NAME;
            y = Double.parseDouble(elt.getAttribute(attributeName));
            attributeName = XMLPrimitivePlaneBuilder.Z_ATTRIBUTE_NAME;
            z = Double.parseDouble(elt.getAttribute(attributeName));
        } catch (NumberFormatException ex) {
            throw new ParsingException(
                String.format(
                    XMLPrimitivePlaneBuilder.MALFORMED_ATTRIBUTE_MESSAGE,
                    attributeName, eltName),
                ex);
        }
        
        Vector normal = new Vector(x, y, z);

        // Distance
        eltName = XMLPrimitivePlaneBuilder.DISTANCE_ELEMENT_NAME;
        elt = (Element)element.getElementsByTagName(eltName).item(0);
        if (elt == null) {
            throw new ParsingException(
                String.format(XMLPrimitivePlaneBuilder.NODE_MISSING_MESSAGE,
                    eltName, name));
        }

        double distance;
        
        try {
            attributeName = XMLPrimitivePlaneBuilder.VALUE_ATTRIBUTE_NAME;
            distance = Double.parseDouble(elt.getAttribute(attributeName));
        } catch (NumberFormatException ex) {
            throw new ParsingException(
                String.format(
                    XMLPrimitivePlaneBuilder.MALFORMED_ATTRIBUTE_MESSAGE,
                    attributeName, eltName),
                ex);
        }
        
        return new Plane(name, normal, distance);
    }
}
