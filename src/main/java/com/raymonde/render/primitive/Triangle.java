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
import lombok.Builder;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class Triangle extends AbstractPrimitive {

    private final Vector [] vertices = new Vector[3];

    private static final int FIRST = 0;

    private static final int SECOND = 1;

    private static final int THIRD = 2;

    private static final double EPSILON = 0.0000000001;

    @Builder
    public Triangle(final Vector first, final Vector second, final Vector third, final Material material) {
        super(material);
        vertices[FIRST] = first;
        vertices[SECOND] = second;
        vertices[THIRD] = third;
    }

    @Override
    public Vector normalAt(Vector point) {
        val edge1 = Vector.joining(vertices[FIRST], vertices[SECOND]);
        val edge2 = Vector.joining(vertices[FIRST], vertices[THIRD]);
        return edge1.cross(edge2);
    }

    @Override
    public IntersectionResult intersect(final Ray ray) {

        // Follow the Möller-Trumblore algorithm
        // see : https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm
        val edge1 = Vector.joining(vertices[FIRST], vertices[SECOND]);
        val edge2 = Vector.joining(vertices[FIRST], vertices[THIRD]);

        val pVec = ray.direction().cross(edge2);
        double det = edge1.dot(pVec);

        if (det > -EPSILON && det < EPSILON) {
            return IntersectionResult.builder()
                    .primitive(this)
                    .ray(ray)
                    .intersect(false)
                    .build();
        }

        double invDet = 1. / det;

        val tVec = Vector.joining(vertices[FIRST], ray.origin());

        double u = tVec.dot(pVec) * invDet;

        if (u < 0. || u > 1.) {
            return IntersectionResult.builder()
                    .primitive(this)
                    .ray(ray)
                    .intersect(false)
                    .build();
        }

        val qVec = tVec.cross(edge1);

        double v = ray.direction().dot(qVec) * invDet;

        if (v < 0. || u + v > 1.) {
            return IntersectionResult.builder()
                    .primitive(this)
                    .ray(ray)
                    .intersect(false)
                    .build();
        }

        double t = edge2.dot(qVec) * invDet;

        if (t > EPSILON) {
            return IntersectionResult.builder()
                    .primitive(this)
                    .ray(ray)
                    .intersect(true)
                    .distance(t)
                    .build();
        }
        return IntersectionResult.builder()
                .primitive(this)
                .ray(ray)
                .intersect(false)
                .build();
    }
}
