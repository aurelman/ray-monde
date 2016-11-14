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
import com.raymonde.core.QuadraticEquation;
import com.raymonde.core.Vector;
import com.raymonde.render.Ray;
import com.raymonde.render.material.Material;
import lombok.Builder;

import javax.annotation.concurrent.ThreadSafe;

/**
 * {@code Sphere} represents spherical object in a scene.
 * The center of the sphere is represented by his position.
 * The radius is a double value.
 */
@ThreadSafe
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
     * Cached value of the squared radius
     */
    private Double _squaredRadius;

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

    // @Override
    public double intersect1(final Ray ray) {
        
        final Vector rayDirection = ray.direction();
        final Vector rayOriginToSphereVector = Vector.joining(ray.origin(), position);


        double scal = rayOriginToSphereVector.dot(rayDirection);

        if (scal < 0.0) {
            return Double.POSITIVE_INFINITY;
        }

        double ch2 = rayOriginToSphereVector.squaredLength() - scal * scal;

        double r2 = radius * radius;
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
    public double intersect(final Ray ray) {

        final Vector rayOriginMinusSphereCenter = ray.origin().subtract(position);
        final double a = ray.direction().squaredLength();
        final double b = 2 * (ray.direction().dot(rayOriginMinusSphereCenter));

        final double c = rayOriginMinusSphereCenter.squaredLength() - squaredRadius();

        QuadraticEquation.Result result = new QuadraticEquation(a, b, c).solve();

        if (result.rootNumber() == 0) {
            return Double.POSITIVE_INFINITY;
        }

        return result.firstRoot();
    }


    @Override
    public Vector normalAt(final Vector point) {
        return Vector.joining(position, point)
                .normalized();
    }

    private final double squaredRadius() {
        if (_squaredRadius == null) {
            _squaredRadius = radius * radius;
        }

        return radius;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("position", position)
                .add("radius", radius)
                .toString();
    }


}
