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

package com.raymonde.render.material;

import com.raymonde.core.Color;
import com.raymonde.render.Intersection;
import com.raymonde.render.Renderer;
import com.raymonde.render.RenderingContext;
import com.raymonde.scene.Scene;

/**
 * {@code Material}s represents physical properties of the object on which
 * they are applied. The alter the way objects are rendered (color, reflection,
 * transparency/refraction).
 * 
 * @author aurelman
 */
public abstract class AbstractMaterial {

    /**
     * The name of the material.
     */
    private String name;

    /**
     * The sub-material.
     */
    private AbstractMaterial material;

    public AbstractMaterial() {
        
    }
    
    /**
     * Constructs a Material with the specified name.
     * 
     * @param name The name of the material.
     */
    public AbstractMaterial(final String name) {
        this.name = name;
    }

    /**
     *
     * @param renderer The renderer.
     * @param scene The scene.
     * @param inter The intersection.
     * @param ctx  The rendering context.
     *
     * @return The color.
     */
    public abstract Color computeColor(final Renderer renderer,
            final Scene scene,
            final Intersection inter,
            final RenderingContext ctx);

    /**
     * @return the material
     */
    public AbstractMaterial getMaterial() {
        return this.material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(final AbstractMaterial material) {
        this.material = material;
    }

    /**
     * Returns the name of the material.
     * @return The name of the material.
     */
    public String getName() {
        return name;
    }
}
