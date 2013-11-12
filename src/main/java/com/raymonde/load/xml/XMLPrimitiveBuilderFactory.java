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
public class XMLPrimitiveBuilderFactory {

    /**
     * The name for the sphere type.
     */
    private static String SPHERE_TYPE = "sphere";

    /**
     * The name of the plane type.
     */
    private static String PLANE_TYPE = "plane";
    
    /**
     * Returns the primitive builder available for the specified type.
     * 
     * @param type The type.
     * 
     * @return The primitive builder available for the specified type.
     */
    public XMLPrimitiveBuilder forType(final String type) {
        
        if (XMLPrimitiveBuilderFactory.SPHERE_TYPE.equals(type)) {
            return new XMLPrimitiveSphereBuilder();
        }
        if (XMLPrimitiveBuilderFactory.PLANE_TYPE.equals(type)) {
            return new XMLPrimitivePlaneBuilder();
        }
        return null;
    }
}
