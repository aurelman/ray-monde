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
 *
 * @author aurelman
 */
public interface Material {
    
    /**
     *
     * @param renderer The renderer.
     * @param scene The scene.
     * @param inter The intersection.
     * @param ctx  The rendering context.
     *
     * @return The color.
     */
    public Color computeColor(final Renderer renderer,
            final Scene scene,
            final Intersection inter,
            final RenderingContext ctx);
    
}
