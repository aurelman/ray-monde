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

import com.google.common.base.MoreObjects;
import com.raymonde.core.Vector;
import com.raymonde.render.Ray;
import com.raymonde.render.material.Material;
import lombok.Builder;

/**
 * {@code Sphere} represents spherical object in a scene.
 * The center of the sphere is represented by his position.
 * The radius is a double value.
 */
public class Sphere extends AbstractPrimitive {

    /**
     * The center position
     */
    private final Vector position;
    
    /**
     * The radius.
     */
    private final double radius;

    /**
     * Constructs a {@code Sphere} object with the specified name.
     *
     * @param position The sphere center position.
     * @param radius The radius of the sphere.
     */
    @Builder
    public Sphere(final Vector position, final double radius, final Material material) {
        super(material);
        this.position = position;
        this.radius = radius;
    }

    @Override
    public double intersect(final Ray ray) {
        
        Vector rayDir = ray.getDirection();
        Vector sphereVector = Vector.joining(ray.getOrigin(), getPosition());
        double scal = sphereVector.dot(rayDir);

        if (scal < 0.0) {
            return Double.POSITIVE_INFINITY;
        }

        double ch2 = sphereVector.squaredLength() - scal*scal;

        double r2 = getRadius()*getRadius();
        if (ch2 > r2) {
            return Double.POSITIVE_INFINITY;
        }

        double d = Math.sqrt(r2 - ch2);
        double t1 = scal + d;
        double t2 = scal - d;

        if (t1 > 0 || t2 > 0) {
            
            return Math.min(t1, t2);
        }

        return Double.POSITIVE_INFINITY;
    }

    @Override
    public Vector normalAt(final Vector point) {
        return Vector.joining(position, point)
                .normalized();
    }
    
    /**
     * Returns the radius of the sphere.
     * 
     * @return The radius of the sphere.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @return the position
     */
    public Vector getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("position", position)
                .add("radius", radius)
                .toString();
    }
}
