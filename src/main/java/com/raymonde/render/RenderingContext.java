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
import lombok.Builder;

/**
 * {@code RenderingContext} objects stores key elements (such as refraction factor, or depth) that are
 * specific to the current rendering step.
 */
public class RenderingContext {

    /**
     * The current refraction index.
     */
    private final double refraction;

    /**
     * The current depth in the algorithm.
     */
    private final int depth;

    private final Scene scene;

    @Builder
    public RenderingContext(final Scene scene, final int depth, final double refraction) {
        this.scene = scene;

        this.depth = depth;
        this.refraction = refraction;
    }

    /**
     * @return The refraction
     */
    public double getRefraction() {
        return this.refraction;
    }

    /**
     * @return the depth
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     *
     * @return
     */
    public Scene getScene() {
        return scene;
    }

    /**
     *
     * @param ray The ray.
     *
     * @return The resulting color object.
     */
    public Color computeColor(final Ray ray) {

        //double refraction = ctx.getRefraction();
        Scene sc = scene;
        IntersectionResult intersection = sc.nearestIntersection(ray);

        Color result = Color.black();

        if (intersection != null) {
            result = intersection.primitive().getMaterial()
                    .computeColor(intersection, this);
        }

        return result;
    }

    public static RenderingContext incremented(final RenderingContext ctx) {
        return RenderingContext.builder()
                .scene(ctx.scene)
                .depth(ctx.depth + 1)
                .refraction(ctx.refraction)
                .build();
    }

    public static RenderingContext incremented(final RenderingContext ctx, final double refraction) {
        return RenderingContext.builder()
                .scene(ctx.scene)
                .depth(ctx.depth + 1)
                .refraction(refraction)
                .build();
    }
}
