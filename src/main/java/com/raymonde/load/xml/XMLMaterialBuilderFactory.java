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

/**
 *
 * @author aurelman
 */
public class XMLMaterialBuilderFactory {
    
    /**
     * The phong material.
     */
    private static final String PHONG_TYPE = "phong";

    /**
     * The color material.
     */
    private static final String COLOR_TYPE = "color";

    /**
     * The reflective material.
     */
    private static final String REFLECTIVE_TYPE = "reflective";

    /**
     * The refractive material.
     */
    private static final String REFRACTIVE_TYPE = "refractive";

    /**
     * Returns the material builder available for the specified type.
     * 
     * @param type The type of the material.
     * 
     * @return The material builder available for the specified type.
     */
    public XMLMaterialBuilder forType(final String type) {

        if (XMLMaterialBuilderFactory.PHONG_TYPE.equals(type)) {
            return new XMLMaterialPhongBuilder();
        }
        if (XMLMaterialBuilderFactory.COLOR_TYPE.equals(type)) {
            return new XMLMaterialColorBuilder();
        }
        if (XMLMaterialBuilderFactory.REFLECTIVE_TYPE.equals(type)) {
            return new XMLMaterialReflectiveBuilder();
        }
        if (XMLMaterialBuilderFactory.REFRACTIVE_TYPE.equals(type)) {
            return new XMLMaterialRefractiveBuilder();
        }
        return null;
    }
}
