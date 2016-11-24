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
import com.raymonde.render.IntersectionResult;
import com.raymonde.render.Renderer;
import com.raymonde.render.RenderingContext;
import com.raymonde.scene.Scene;
import lombok.Builder;

/**
 * This is a simple material. It represents a uniform {@link Color}.
 */
public class ColorMaterial extends AbstractMaterial implements Material {

    /**
     * The color of the material.
     */
    private final Color color;
    
    /**
     * Constructs a new {@link ColorMaterial} with the specified color.
     *
     * @param color The color of the material.
     */
    @Builder
    public ColorMaterial(final Color color, final Material subMaterial) {
        super(subMaterial);
        this.color = color;
    }

    @Override
    public Color computeColor(final IntersectionResult inter, final RenderingContext ctx) {
        return color;
    }
}
