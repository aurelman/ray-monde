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
import com.raymonde.render.material.AbstractMaterial;
import com.raymonde.render.material.ColorMaterial;
import org.w3c.dom.Element;

/**
 *
 * @author aurelman
 */
public class XMLMaterialColorBuilder extends XMLMaterialBuilder {

    /**
     * The name of the color element.
     */
    private static final String COLOR_ELEMENT_NAME = "color";

    /**
     * The name of the name attribute.
     */
    private static final String NAME_ATTRIBUTE_NAME = "name";

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
     * Standard message.
     */
    private static final String NODE_MISSING_MESSAGE =
            "missing <%s> node for color material";

    /**
     * Standard message.
     */
    private static final String MALFORMED_ATTRIBUTE_MESSAGE =
            "malformed value for attribute <%s> in element <%s>";
    
    @Override
    public AbstractMaterial build(final Element element) throws ParsingException {
        String name = element.getAttribute(
                XMLMaterialColorBuilder.NAME_ATTRIBUTE_NAME);
        
        String eltName = XMLMaterialColorBuilder.COLOR_ELEMENT_NAME;
        Element elt = (Element)element.getElementsByTagName(eltName).item(0);
        
        if (elt == null) {
            throw new ParsingException(
                String.format(XMLMaterialColorBuilder.NODE_MISSING_MESSAGE,
                    eltName));
        }

        String attributeName = "";
        double r, g, b;
        try {
            attributeName = XMLMaterialColorBuilder.RED_ATTRIBUTE_NAME;
            r = Double.parseDouble(elt.getAttribute(attributeName));
            attributeName = XMLMaterialColorBuilder.GREEN_ATTRIBUTE_NAME;
            g = Double.parseDouble(elt.getAttribute(attributeName));
            attributeName = XMLMaterialColorBuilder.BLUE_ATTRIBUTE_NAME;
            b = Double.parseDouble(elt.getAttribute(attributeName));
        } catch (NumberFormatException ex) {
            throw new ParsingException(
                String.format(
                    XMLMaterialColorBuilder.MALFORMED_ATTRIBUTE_MESSAGE,
                    attributeName, eltName));
        }
        
        return new ColorMaterial(name, new Color(r, g, b));
    }
}
