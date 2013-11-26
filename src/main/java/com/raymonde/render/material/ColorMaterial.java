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

package com.raymonde.render.material;

import com.raymonde.core.Color;
import com.raymonde.render.Intersection;
import com.raymonde.render.Renderer;
import com.raymonde.render.RenderingContext;
import com.raymonde.scene.Scene;

/**
 * This is a simple material.
 *
 * 
 * @author aurelman
 */
public class ColorMaterial extends AbstractMaterial implements Material {

    /**
     * The color of the material.
     */
    private Color color;


    /**
     *
     * @param name The name of the material.
     */
    public ColorMaterial(final String name) {
        super(name);
    }
    
    /**
     * Constructs a new <code>ColorMaterial</code> with the specified color.
     *
     * @param name The name of the material.
     * @param color The color of the material.
     */
    public ColorMaterial(final String name, final Color color) {
        super(name);
        this.color = color;
    }

    @Override
    public Color computeColor(final Renderer renderer,
            final Scene scene,
            final Intersection inter,
            final RenderingContext ctx) {
        return getColor();
    }

    /**
     * Returns the color of the material.
     * 
     * @return The color of the material.
     */
    public Color getColor() {
        return color;
    }
}
