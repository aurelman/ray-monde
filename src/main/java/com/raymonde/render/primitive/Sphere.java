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
import com.raymonde.render.IntersectionResult;
import com.raymonde.render.Ray;
import com.raymonde.render.material.Material;
import lombok.Builder;
import lombok.val;

import javax.annotation.concurrent.ThreadSafe;

/**
 * {@code Sphere} represents spherical object in a scene.
 * The center of the sphere is represented by his origin.
 * The radius is a double value.
 */
@ThreadSafe
public class Sphere extends AbstractPrimitive {

    /**
     * The center origin
     */
    private final Vector origin;
    
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
     * @param origin The sphere center origin.
     * @param radius The radius of the sphere.
     */
    @Builder
    public Sphere(final Vector origin, final double radius, final Material material) {
        super(material);
        this.origin = origin;
        this.radius = radius;
    }

    // @Override
    public double intersect1(final Ray ray) {

        val rayOriginMinusSphereCenter = Vector.joining(origin, ray.origin());
        val a = ray.direction().squaredLength();
        val b = 2 * (ray.direction().dot(rayOriginMinusSphereCenter));
        val c = rayOriginMinusSphereCenter.squaredLength() - squaredRadius();

        val result = new QuadraticEquation(a, b, c).solve();

        if (result.rootNumber() == 0) {
            return Double.POSITIVE_INFINITY;
        }

        // First root is always the lower value
        return result.firstRoot();
    }

    @Override
    public IntersectionResult intersect(final Ray ray) {

        val rayOriginMinusSphereCenter = Vector.joining(origin, ray.origin());
        val a = ray.direction().squaredLength();
        val b = 2 * (ray.direction().dot(rayOriginMinusSphereCenter));
        val c = rayOriginMinusSphereCenter.squaredLength() - squaredRadius();

        val result = new QuadraticEquation(a, b, c).solve();

        if (result.rootNumber() == 0) {
            return IntersectionResult.builder()
                    .primitive(this)
                    .ray(ray)
                    .intersect(false)
                    .build();
        }

        // First root is always the lower value
        return IntersectionResult.builder()
                .primitive(this)
                .ray(ray)
                .intersect(true)
                .distance(result.firstRoot())
                .build();
    }

    @Override
    public Vector normalAt(final Vector point) {
        return Vector.joining(origin, point)
                .normalized();
    }

    private final double squaredRadius() {
        if (_squaredRadius == null) {
            _squaredRadius = radius * radius;
        }

        return _squaredRadius;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("origin", origin)
                .add("radius", radius)
                .toString();
    }
}
