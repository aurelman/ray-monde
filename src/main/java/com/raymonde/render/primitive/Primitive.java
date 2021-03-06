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

package com.raymonde.render.primitive;

import com.raymonde.core.Vector;
import com.raymonde.render.IntersectionResult;
import com.raymonde.render.Ray;
import com.raymonde.render.material.Material;

/**
 * {@code Primitive} objects are the <em>solids</em> elements of a scene.
 * They might be intersected by rays.
 */
public interface Primitive {


    /**
     * Compute the normal vector to the primitive surface for the specified
     * point. Suppose the specified point is on the surface.
     * 
     * @param point The point on the primitive surface.
     * 
     * @return The normal vector.
     */
    Vector normalAt(final Vector point);
    
    /**
     * Returns a an intersection distance from the ray origin.
     * 
     * @param ray The ray the primitive may be intersected by.
     * 
     * @return The intersection distance.
     */
    IntersectionResult intersect(Ray ray);

    Material getMaterial();
}

