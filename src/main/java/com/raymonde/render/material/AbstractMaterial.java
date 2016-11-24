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

/**
 * {@code AbstractMaterial} adds capacity to compose {@link Material}s.
 *
 */
public abstract class AbstractMaterial implements Material {

    /**
     * The sub-subMaterial.
     */
    private final Material subMaterial;

    public AbstractMaterial(final Material subMaterial) {
        this.subMaterial = subMaterial;
    }

    /**
     * @param inter The intersection.
     * @param ctx  The rendering context.
     *
     * @return The color.
     */
    public abstract Color computeColor(final IntersectionResult inter, final RenderingContext ctx);

    /**
     * @return the subMaterial
     */
    Material getSubMaterial() {
        return subMaterial;
    }
}
