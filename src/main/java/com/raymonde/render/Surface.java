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

import com.raymonde.core.Color;
import com.raymonde.core.Vector;
import java.util.Arrays;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

/**
 * <code>Surface</code> is the plane where the scene will be rendered.
 * This class is thread-safe, a surface can be updated by many thread at the same time.
 * 
 * @author aurelman
 */
@ThreadSafe
public class Surface {

    /**
     * Position of the origin of the surface, in the absolute cooridinate.
     */
    private final Vector position;

    /**
     * Width of the surface.
     */
    private int width;

    /**
     * Height of the surface.
     */
    private int height;

    /**
     * Used to access colors array.
     */
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();
    
    /**
     * A <code>Color</code> array.
     * The size of the array is : <code>(width * height)</code> and each point
     * <code>(x, y)</code> on the surface is located at
     * <code>colors[y * width + x]</code>.
     */
    @GuardedBy("rwLock")
    // Maybe Should rely on concurrentCollection for thread safety
    private Color [] colors;

    /**
     * Constructs a <code>Surface</code> object
     * with the specified width and height (in pixel).
     *
     * @param width The width in pixel of the surface.
     * @param height The height in pixel of the surface.
     */
    public Surface(final Vector position,
            final int width, final int height) {
        this.position = position;
        this.width = width;
        this.height = height;
        colors = new Color[width*height];
    }

    /**
     * Getter for the surface width.
     * 
     * @return The width of the surface.
     */
    public int getWidth() {
        return width;
    }


    /**
     * Getter for the surface height.
     * 
     * @return The height of the surface.
     */
    public int getHeight() {
        return height;
    }


    /**
     * Sets the specified color at the specified position.
     * 
     * @param width The width position on the surface.
     * @param height The height position on the surface.
     *
     * @param color The color to set at the specified position.
     */
    public void setColor(final int width, final int height, final Color color) {    
        try {
            rwLock.writeLock().lock();
            colors[height*getWidth() + width] = color;
        } finally {
            rwLock.writeLock().unlock();
        }
        
        
    }

    /**
     * Returns the color at the specified position,
     * <code>null</code> if no color have been previously set.
     * 
     * @param width The width position on the surface.
     * @param height The height position on the surface.
     * 
     * @return The color at the specified position.
     */
    public Color getColor(final int width, final int height) {
        try {
            rwLock.readLock().lock();
            return colors[height*getWidth() + width];
        } finally {
            rwLock.readLock().unlock();
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
    public Vector getPosition() {
        return position;
    }

    /**
     * Returns a <code>Color</code> array of the colors on the surface.
     * The size of the array is : <code>(width * height)</code> 
     * and the color at the <code>(x, y)</code> position on the
     * surface is located at <code>[y * width + x]</code> in the returned array.
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
}
