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

package com.raymonde.render;

import com.raymonde.core.Vector;
import com.raymonde.render.primitive.Primitive;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * <code>Intersection</code> encapsulates data about intersection
 * between ray and primitive.
 * It stores the intersecting ray, the intersected primitive and the distance
 * from the ray origin the intersection occured.
 * 
 * @author aurelman
 */
@Immutable
@ThreadSafe
public final class Intersection {

    /**
     * Only consistent where intersect is true.
     */
    private final double distance;

    /**
     * The concerned primitive by the intersection.
     */
    private final Primitive primitive;

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
    private Ray reflectedRay;

    /**
     *
     * @param intersected The primitive intersected.
     * @param ray The ray that intersect.
     * @param distance The distance.
     */
    public Intersection(final Primitive intersected, final Ray ray, final double distance) {
        this.primitive = intersected;
        this.distance = distance;
        this.incomingRay = ray;
    }


    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @return the intersected primitive
     */
    public Primitive getPrimitive() {
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
    
    public Ray getReflectedRay() {
        if (reflectedRay == null) {
            final Vector intersectionPosition = getIntersectionPosition();
            final Vector normal = getPrimitive().normalAt(intersectionPosition);
            final Vector reflected = incomingRay.getDirection().reflected(normal);
            reflectedRay = new Ray(intersectionPosition, reflected);
        }
        
        return reflectedRay;
    }


    /**
     * Computes and returns the intersection point.
     * 
     * @return The intersection point.
     */
    public Vector getIntersectionPosition() {
        if (intersectionPoint == null) {
            // origin + dist*direction
            intersectionPoint = getIncomingRay().getDirection().
                    multiply(getDistance()).add(getIncomingRay().getOrigin());
        }
        return intersectionPoint;
    }
    
    
}
