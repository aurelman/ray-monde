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

package com.raymonde.render.light;

import com.raymonde.core.Color;
import com.raymonde.core.Vector;
import com.raymonde.render.Element;

/**
 * <p>Base class for a light in a scene.</p>
 * 
 * @author aurelman
 */
public abstract class Light extends Element {
    
    /**
     * The element position.
     */
    private Vector position;

    /**
     * 
     * @param name The name of the light.
     */
    public Light(final String name) {
        super(name);
    }

    /**
     *
     * @param name The name of the light.
     * @param position The position of the light.
     */
    public Light(final String name, final Vector position) {
        super(name);
        this.position = position;
    }

    /**
     * Returns the perceived color at the specified position.
     * 
     * 
     * @param point The point the light color will be computed for.
     * 
     * @return The color the point has received.
     */
    public abstract Color colorAt(final Vector point);

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
}
