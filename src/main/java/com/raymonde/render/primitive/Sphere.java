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

package com.raymonde.render.primitive;

import com.raymonde.core.Vector;
import com.raymonde.render.Ray;

/**
 * <code>Sphere</code> represents spherical object in a scene.
 * The center of the sphere is represented by his position.
 * The radius is a double value.
 * 
 * @author aurelman
 */
public class Sphere extends Primitive {

    /**
     * The center position
     */
    private Vector position;
    
    /**
     * The radius.
     */
    private double radius;

    /**
     * Constructs a <code>Sphere</code> object with the specified name.
     *
     * @param name The name of the sphere entity.
     */
    public Sphere(final String name) {
        super(name);
    }

    /**
     * Constructs a <code>Sphere</code> object with the specified name.
     *
     * @param name The name of the sphere entity.
     * @param pos The sphere center position.
     * @param radius The radius of the sphere.
     */
    public Sphere(final String name, final Vector pos, final double radius) {
        super(name);
        this.position = pos;
        this.radius = radius;
    }

    @Override
    public double intersect(final Ray ray) {
        
        Vector rayDir = ray.getDirection();
        Vector sphereVector =
                Vector.joining(ray.getOrigin(), getPosition());
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

        Vector normal = Vector.joining(getPosition(), point);
        return normal.normalized();
    }
    
    /**
     * Returns the radius of the sphere.
     * 
     * @return The radius of the sphere.
     */
    public double getRadius() {
        return this.radius;
    }

    /**
     * @param radius The radius to set.
     */
    public void setRadius(final double radius) {
        this.radius = radius;
    }

    /**
     * @return the position
     */
    public Vector getPosition() {
        return this.position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(final Vector position) {
        this.position = position;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Sphere : name = ");
        sb.append(getName()).
                append(", radius = ").append(getRadius()).
                append(", position = ").append(getPosition());
        sb.append(']');
        return sb.toString();
    }
}
