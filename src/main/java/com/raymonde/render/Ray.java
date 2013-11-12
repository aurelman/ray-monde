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
import java.util.Objects;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * <code>Ray<code> object is made of two components {@link Vector} objects.
 * One representing the original position, and the second one representing
 * the direction of the ray.
 * <code>Ray</code> objects are immutable.
 * 
 * @see Vector
 * 
 * @author aurelman
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
     * Constructs a <code>Ray</code> with the specified origin and direction.
     * 
     * @param origin The start point of the ray.
     * @param direction The direction of the ray.
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
    public Vector getDirection() {
        return direction;
    }

    /**
     * Returns the origin position of the ray.
     *
     * @return the origin position of the ray.
     */
    public Vector getOrigin() {
        return origin;
    }

    /**
     * Returns the <code>Ray</code> object passing through the specified start
     * and end points.
     *
     * @param startPoint The starting position of the resulting ray.
     * @param endPoint The end position of the resulting ray.
     *
     * @return The resulting <code>Ray</code> object.
     */
    public static Ray joining(
            final Vector startPoint, final Vector endPoint) {
        Vector direction = Vector.joining(startPoint, endPoint);
        return new Ray(startPoint, direction);
    }

    /**
     * Returns a <code>String</code> representation of the current object.
     * @return a <code>String</code> representation of the current object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ray : \n")
                .append("   origin : ").append(getOrigin().toString())
                .append("\n")
                .append("   direction : ").append(getDirection().toString());

        return sb.toString();
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
       return direction.equals(otherRay.direction) && origin.equals(otherRay.origin);  
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(direction);
        hash = 67 * hash + Objects.hashCode(origin);
        return hash;
    }
}
