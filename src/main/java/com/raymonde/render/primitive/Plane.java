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
import com.raymonde.render.Ray;
import com.raymonde.render.material.Material;
import lombok.Builder;

/**
 * A {@code Plane} object is defined by a normal vector and a distance from
 * the origine.
 */
public class Plane extends AbstractPrimitive {

    /**
     * The distance from the origin.
     */
    private final double distance;

    /**
     * The normal to the plan.
     */
    private final Vector normal;

    /**
     * Constructs the plane with the specified name.
     *
     * @param normal The normal of the plane.
     * @param distance The distance from origin to the plane.
     */
    @Builder
    public Plane(final Vector normal, final double distance, final Material material) {
        super(material);
        this.normal = normal.normalized();
        this.distance = distance;

    }

    @Override
    public Vector normalAt(final Vector point) {
        return getNormal().normalized();
    }

    @Override
    public double intersect(final Ray ray) {
        Vector planeNormal = getNormal();
        Vector origin = ray.origin();
        Vector direction = ray.direction();
        
        double dot = planeNormal.dot(direction);

        if (dot >= 0.0) {
            return Double.POSITIVE_INFINITY;
        }
        
        double dot2 = -(planeNormal.dot(origin) + getDistance());

        double t = dot2 / dot;
        if (t < 0.0) {
            return Double.POSITIVE_INFINITY;
        }
        return t;
    }

    /**
     * @return The normal vector.
     */
    public Vector getNormal() {
        return normal;
    }
    
    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }
}
