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
import com.raymonde.render.IntersectionResult;
import com.raymonde.render.Ray;
import com.raymonde.render.material.Material;
import lombok.Builder;

/**
 * A {@code Plane} object is defined by a normal vector and a distance from
 * the origin.
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
        return normal.normalized();
    }

    @Override
    public IntersectionResult intersect(final Ray ray) {
        double dot = normal.dot(ray.direction());

        if (dot >= 0.0) {
            return IntersectionResult.builder()
                    .primitive(this)
                    .ray(ray)
                    .intersect(false)
                    .build();
        }
        
        double dot2 = -(normal.dot(ray.origin()) + distance);

        double t = dot2 / dot;
        if (t < 0.0) {
            return IntersectionResult.builder()
                    .primitive(this)
                    .ray(ray)
                    .intersect(false)
                    .build();
        }

        return IntersectionResult.builder()
                .primitive(this)
                .ray(ray)
                .intersect(true)
                .distance(t)
                .build();
    }

    /**
     * Returns a {@link String} representation of {@code Plane}.
     *
     * @return A {@link String} representation of {@code Plane}.
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("normal", normal)
                .add("distance", distance)
                .toString();
    }
}
