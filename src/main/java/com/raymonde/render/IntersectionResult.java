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

package com.raymonde.render;

import com.google.common.base.MoreObjects;
import com.raymonde.core.Vector;
import com.raymonde.render.primitive.Primitive;
import lombok.Builder;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import static com.google.common.base.MoreObjects.firstNonNull;

/**
 * {@code IntersectionResult} wraps data about intersection between a ray and a primitive.
 * It stores the intersecting ray, the intersected primitive and the distance
 * from the ray origin the intersection occurred.
 */
@Immutable
@ThreadSafe
public final class IntersectionResult {

    /**
     * Only consistent where intersect is true.
     */
    private final double distance;
    /**
     * The concerned primitive by the intersection.
     */
    private final Primitive primitive;
    /**
     * Whether an intersection really occurred
     */
    private final boolean intersect;

    /**
     * The intersecting ray.
     */
    private final Ray incomingRay;
    /**
     * The computed intersection position. 
     * Lazy-initialized.
     */
    private Vector intersectionPoint;


    /**
     * The computed reflected ray.
     * Lazy-initialized.
     */
    private Ray _reflectedRay;


    /**
     * The computed normal.
     * Lazy-initialized.
     */
    private Vector _normal;

    /**
     *
     * @param primitive The primitive intersected.
     * @param ray The ray that intersect.
     * @param intersect whether an intersection really occurred or not
     * @param distance The distance from the ray origin where the intersection occurred.
     */
    @Builder
    public IntersectionResult(final Primitive primitive, final Ray ray, final Boolean intersect, final Double distance) {
        this.primitive = primitive;
        this.distance = firstNonNull(distance, Double.NaN) ;
        this.incomingRay = ray;
        this.intersect = firstNonNull(intersect, false);
    }

    /**
     * Returns whether an intersection occured between the ray and the primitive
     * @return
     */
    public boolean intersect() {
        return intersect;
    }

    /**
     * @return the distance
     */
    public double distance() {
        return distance;
    }

    /**
     * @return the intersected primitive
     */
    public Primitive primitive() {
        return primitive;
    }

    /**
     * Returns the {@see Ray} object that intersected.
     * 
     * @return The incoming ray.
     */
    public Ray getIncomingRay() {
        return incomingRay;
    }
    
    public Ray reflectedRay() {
        if (_reflectedRay == null) {
            final Vector intersectionPosition = getIntersectionPosition();
            final Vector normal = primitive().normalAt(intersectionPosition);
            final Vector reflected = incomingRay.direction().reflected(normal);
            _reflectedRay = new Ray(intersectionPosition, reflected);
        }
        
        return _reflectedRay;
    }


    public Vector normal() {
        if (_normal == null) {
            _normal = primitive.normalAt(intersectionPoint).normalized();
        }

        return _normal;
    }

    /**
     * Computes and returns the intersection point.
     * 
     * @return The intersection point.
     */
    public Vector getIntersectionPosition() {
        if (intersectionPoint == null) {
            // origin + dist * direction
            intersectionPoint = incomingRay
                    .direction()
                    .multiply(distance)
                    .add(incomingRay.origin());
        }
        return intersectionPoint;
    }
}
