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

import com.raymonde.load.ParsingException;
import com.raymonde.render.material.AbstractMaterial;
import com.raymonde.render.material.RefractiveMaterial;
import org.w3c.dom.Element;

/**
 *
 * @author aurelman
 */
public class XMLMaterialRefractiveBuilder extends XMLMaterialBuilder {

    /**
     * The name of the color element.
     */
    private static final String REFRACTION_ELEMENT_NAME = "refraction";

    /**
     * The name of the value attribute.
     */
    private static final String VALUE_ATTRIBUTE_NAME = "value";

    /**
     * The name of the name attribute.
     */
    private static final String NAME_ATTRIBUTE_NAME = "name";
    
    /**
     * Standard message.
     */
    private static final String NODE_MISSING_MESSAGE =
            "missing <%s> node for refractive material";

    /**
     * Standard message.
     */
    private static final String MALFORMED_ATTRIBUTE_MESSAGE =
            "malformed value for attribute <%s> in element <%s>";

    @Override
    public AbstractMaterial build(final Element element) throws ParsingException{
        String name = element.getAttribute(
                XMLMaterialRefractiveBuilder.NAME_ATTRIBUTE_NAME);

        String eltName = XMLMaterialRefractiveBuilder.REFRACTION_ELEMENT_NAME;
        Element elt = (Element)element.getElementsByTagName(eltName).item(0);

        if (elt == null) {
            throw new ParsingException(
                String.format(
                    XMLMaterialRefractiveBuilder.NODE_MISSING_MESSAGE,
                    eltName));
        }

        String attributeName = "";
        double refraction;
        try {
            attributeName = XMLMaterialRefractiveBuilder.VALUE_ATTRIBUTE_NAME;
            refraction = Double.parseDouble(elt.getAttribute(attributeName));
        } catch (NumberFormatException ex) {
            throw new ParsingException(
                String.format(
                    XMLMaterialRefractiveBuilder.MALFORMED_ATTRIBUTE_MESSAGE,
                    attributeName, eltName));
        }

        return new RefractiveMaterial(name, refraction);
    }
}
