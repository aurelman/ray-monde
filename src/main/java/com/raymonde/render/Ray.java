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

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Objects;

/**
 * A {@code Ray} instance is made of two {@link Vector} components.
 * <ul>
 *     <li>One representing the position of its source</li>
 *     <li>one representing its direction</li>
 * </ul>
 *
 * {@code Ray} instances are immutable.
 * 
 * @see Vector
 */
@Immutable
@ThreadSafe
public final class Ray {

    /**
     * Direction of the ray.
     */
    private final Vector direction;


    /**
     * Point the ray pass through
     */
    private final Vector origin;
    
    /**
     * Constructs a {@code Ray} with the specified origin and direction.
     * 
     * @param origin The start point of the ray.
     * @param direction A {@link Vector} representing the direction, will be normalized.
     */
    public Ray(final Vector origin, final Vector direction) {
        this.origin = origin;
        this.direction = direction.normalized();
    }

    /**
     * Returns the direction of the ray.
     * 
     * @return the direction of the ray.
     */
    public Vector direction() {
        return direction;
    }

    /**
     * Returns the origin position of the ray.
     *
     * @return the origin position of the ray.
     */
    public Vector origin() {
        return origin;
    }

    /**
     * Returns a {@link String} representation of the {@code Ray}.
     * @return a {@link String} representation of the {@code Ray}.
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("origin", origin)
                .add("direction", direction)
                .toString();
    }

    @Override
    public boolean equals(final Object other) {
       if (this == other) {
           return true;
       }

       if (!(other instanceof Ray)) {
           return false;
       }

       final Ray otherRay = (Ray)other;
       return Objects.equals(direction, otherRay.direction) && Objects.equals(origin, otherRay.origin);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(origin, direction);
    }

    /**
     * Returns an instance of {@code Ray} that goes from {@code startPoint} and pass through {@code endPoint}.
     *
     * @param startPoint The starting position of the resulting ray
     * @param passThroughPoint A point the ray should pass through
     *
     * @return The resulting {@code Ray} instance.
     */
    public static Ray joining(final Vector startPoint, final Vector passThroughPoint) {
        Vector direction = Vector.joining(startPoint, passThroughPoint);
        return new Ray(startPoint, direction);
    }
}
