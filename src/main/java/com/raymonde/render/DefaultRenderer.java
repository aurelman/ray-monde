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
     * The scene to shootThroughPixel.
     */
    private Scene scene;

    /**
     * Renders the specified {@link Scene} through the specified {@link Camera}
     *
     * @param scene
     * @param camera
     *
     * @return
     */
    public RenderingSurface renderSceneThroughCamera(final Scene scene, final Camera camera) {
        // TODO: shouldn't need to have to set the scene
        setScene(scene);
        RenderingSurface rendered = camera.createRenderingSurface();

        rendered.eachPixel(pixel -> {
            Ray ray = camera.rayThroughPixel(pixel);

            RenderingContext ctx = new RenderingContext(0, 1.);
            ctx.setRefraction(1.0);
            Color computedColor = computeColor(ray, ctx);


            rendered.setPixelColor(pixel, computedColor);
        });

        return rendered;
    }

    public RenderingSurface renderSceneThroughCameraAntialiased(final Scene scene, final Camera camera) {
        // TODO: shouldn't need to have to set the scene
        setScene(scene);
        RenderingSurface rendered = camera.createRenderingSurface();

        rendered.eachPixel(pixel -> {
            List<Ray> rays = camera.raysThroughPixel(pixel);

            RenderingContext ctx = new RenderingContext(0, 1.);
            ctx.setRefraction(1.0);
            Color computedColor = computeColor(rays, ctx);

            rendered.setPixelColor(pixel, computedColor);
        });

        return rendered;
    }

    /**
     *
     * @param ray The ray.
     * @param ctx The current context.
     *
     * @return The resulting color object.
     */
    public Color computeColor(final Ray ray,
            final RenderingContext ctx) {

        //double refraction = ctx.getRefraction();
        Scene sc = getScene();
        IntersectionResult intersection = sc.nearestIntersection(ray);

        Color result = Color.black();
        
        if (intersection != null) {
            result = intersection.primitive().getMaterial()
                .computeColor(this, sc, intersection, ctx);
        }

        return result;
    }


    /**
     *
     * @param ray The ray.
     * @param ctx The current context.
     *
     * @return The resulting color object.
     */
    public Color computeColor(final List<Ray> ray, final RenderingContext ctx) {

        //double refraction = ctx.getRefraction();
        Scene sc = getScene();


        IntersectionResult intersection = sc.nearestIntersection(ray.get(0));

        Color result1 = null;

        if (intersection != null) {
            result1 = intersection.primitive().getMaterial()
                    .computeColor(this, sc, intersection, ctx);
        }


        intersection = sc.nearestIntersection(ray.get(1));

        Color result2 = null;

        if (intersection != null) {
            result2 = intersection.primitive().getMaterial()
                    .computeColor(this, sc, intersection, ctx);
        }

        intersection = sc.nearestIntersection(ray.get(2));

        Color result3 = null;

        if (intersection != null) {
            result3 = intersection.primitive().getMaterial()
                    .computeColor(this, sc, intersection, ctx);
        }

        intersection = sc.nearestIntersection(ray.get(3));

        Color result4 = null;

        if (intersection != null) {
            result4 = intersection.primitive().getMaterial()
                    .computeColor(this, sc, intersection, ctx);
        }

        return Color.average(result1, result2, result3, result4);
    }

    
    /**
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * @param scene the scene to set
     */
    public void setScene(final Scene scene) {
        this.scene = scene;
    }
}
