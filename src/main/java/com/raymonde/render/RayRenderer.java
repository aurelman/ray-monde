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
import com.raymonde.scene.Scene;

/**
 */
public class RayRenderer {

    /**
     * The scene to render
     */
    private final Scene scene;

    public RayRenderer(final Scene scene) {
        this.scene = scene;
    }

    /**
     * @param ray The ray.
     * @param ctx The current context.
     * @return The resulting color object.
     */
    public Color computeColor(final Ray ray,
                              final RenderingContext ctx) {

        //double refraction = ctx.getRefraction();
        Scene sc = scene;
        IntersectionResult intersection = sc.nearestIntersection(ray);

        Color result = Color.black();
        // No intersection
        if (intersection == null) {
            return result;
        }

        //return intersection.primitive().getSubMaterial()
        //        .computeColor(this, sc, intersection, ctx);

        return null;
    }
}
