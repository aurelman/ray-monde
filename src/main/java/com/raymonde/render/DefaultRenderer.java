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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The default renderer. The initial renderer developed for ray-monde.
 */
public class DefaultRenderer implements Renderer {

    /**
     *
     */
    protected static final int DEFAULT_MAX_DEPTH = 8;

    /**
     * The available logger for the <code>DefaultRenderer</code> class.
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultRenderer.class);

    /**
     * Renders the specified {@link Scene} through the specified {@link Camera}
     *
     * @param scene
     * @param camera
     *
     * @return
     */
    public RenderingSurface renderSceneThroughCamera(final Scene scene, final Camera camera) {
        RenderingSurface rendered = camera.createRenderingSurface();

        rendered.eachPixel(pixel -> {
            Ray ray = camera.rayThroughPixel(pixel);

            RenderingContext ctx = RenderingContext.builder()
                    .scene(scene)
                    .depth(0)
                    .refraction(1.)
                    .build();

            final Color computedColor = ctx.computeColor(ray);


            rendered.setPixelColor(pixel, computedColor);
        });

        return rendered;
    }
}
