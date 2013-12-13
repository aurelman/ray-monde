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

import com.raymonde.core.Vector;

/**
 * <code>Camera</code> objects represents a point of view in a Scene
 * 
 * @author aurelman
 */
public class Camera {

    /**
     * The position of the camera.
     */
    private Vector position;

    /**
     * The look at position.
     */
    private Vector direction;

    /**
     * The width of the surface.
     */
    private int width;

    /**
     * The height of the surface.
     */
    private int height;

    /**
     * The distance from the camera origin to the surface.
     */
    private double distance;

    /**
     * 
     * @param name The name of the camera.
     */
    public Camera(final String name) {
    }

    /**
     *
     * @param name The name of the camera.
     * @param pos The position of the camera.
     * @param dir The direction of the camera.
     */
    public Camera(final String name, final Vector pos, final Vector dir) {
        this.position = pos;
        this.direction = dir;
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

    /**
     * @return the direction
     */
    public Vector getDirection() {
        return this.direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(final Vector direction) {
        this.direction = direction;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(final int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(final int height) {
        this.height = height;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return this.distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(final double distance) {
        this.distance = distance;
    }
    
    public Surface createSurface() {
        return null;
    }
}
