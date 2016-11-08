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

import com.raymonde.core.Color;
import com.raymonde.core.Vector;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Arrays;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * {@code RenderingSurface} is the plane where the scene will be rendered onto.
 *
 * This class is thread-safe: a surface can be updated by many thread at the same time.
 *
 * todo: Rendering surfaces should be in a way bound to camera. they are part of the camera or build from camera properties.
 */
@ThreadSafe
public class RenderingSurface {

    /**
     * Position of the origin of the surface, in the absolute coordinate system.
     */
    @Deprecated
    private final Vector position = new Vector(0.0, 0.0, 0.0);

    /**
     * Width of the surface.
     */
    private int pixelWidth;

    /**
     * Height of the surface.
     */
    private int pixelHeight;

    /**
     * Used to access colors array.
     */
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();
    
    /**
     * An array of {@link Color} objects.
     * The size of the array is : {@code pixelWidth * pixelHeight} and each point
     * {@code (x, y)} on the surface is located at the position {@code [y * pixelWidth + x]} of the array.
     */
    @GuardedBy("rwLock")
    // Maybe Should rely on concurrentCollection for thread safety
    private Color [] colors;

    /**
     * Constructs a {@link RenderingSurface} instance
     * with the specified width and height (in pixels).
     *
     * @param pixelWidth The width in pixel of the surface.
     * @param pixelHeight The height in pixel of the surface.
     */
    public RenderingSurface(final int pixelWidth, final int pixelHeight) {
        this.pixelHeight = pixelHeight;
        this.pixelWidth = pixelWidth;
        colors = new Color[pixelWidth * pixelHeight];
    }

    /**
     * Getter for the surface pixelWidth.
     * 
     * @return The pixelWidth of the surface.
     */
    public int getPixelWidth() {
        return pixelWidth;
    }


    /**
     * Getter for the surface pixelHeight.
     * 
     * @return The pixelHeight of the surface.
     */
    public int getPixelHeight() {
        return pixelHeight;
    }


    /**
     * Sets the specified color at the specified position.
     * 
     * @param width The pixelWidth position on the surface.
     * @param height The pixelHeight position on the surface.
     *
     * @param color The color to set at the specified position.
     */
    public void setColor(final int width, final int height, final Color color) {    
        try {
            rwLock.writeLock().lock();
            colors[height* getPixelWidth() + width] = color;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    /**
     * Returns the position of the (0, 0) point if the surface in the
     * absolute system coordinate. 
     * 
     * @return The position of the surface.
     *
     * @see Vector
     */
    @Deprecated
    public Vector getPosition() {
        return position;
    }

    /**
     * Returns a <code>Color</code> array of the colors on the surface.
     * The size of the array is : <code>(pixelWidth * pixelHeight)</code>
     * and the color at the <code>(x, y)</code> position on the
     * surface is located at <code>[y * pixelWidth + x]</code> in the returned array.
     *
     * @return A <code>Color</code> array containing the colors of each position
     * of the surface.
     */
    public Color [] getColors() {
        try {
            rwLock.readLock().lock();
            return Arrays.copyOf(colors, colors.length);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    /**
     * Runs the provided {@link Consumer#accept(Object) consumer} against every pixel of the surface
     *
     * @param lambda
     */
    public void eachPixel(final Consumer<Pixel> lambda) {
        // TODO: Maybe should accept a function
        for (int w = 0; w < pixelWidth; w++) {
            for (int h = 0; h < pixelHeight; h++) {
                lambda.accept(new Pixel(w, h));
            }
        }
    }
}
