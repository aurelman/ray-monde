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

import com.raymonde.scene.Scene;
import com.raymonde.core.Color;
import com.raymonde.core.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        setScene(scene); // TODO: shouldn't need to have to set the scene
        RenderingSurface rendered = camera.createRenderingSurface();
        // For every pixel of the rendering surface
            // Ask the camera to compute a ray that goes through the pixel.
            // shoot this ray in the scene and compute the resulting color
            // update the pixel of the rendering surface with the computed color.

        rendered.eachPixel(pixel -> {
            Ray ray = camera.rayThroughPixel(pixel);

            RenderingContext ctx = new RenderingContext(0, 1.);
            ctx.setRefraction(1.0);

            Color computedColor = computeColor(ray, ctx);
            rendered.setPixelColor(pixel, computedColor);
        });

        return rendered;
    }

    /**
     *
     * @param x The x position.
     * @param y The y position.
     */
    protected Color shootThroughPixel(final int x, final int y) {
        RenderingContext ctx = new RenderingContext(0, 1.);
        ctx.setRefraction(1.0);
        Ray mainRay = rayThroughPixel(x, y);
        
        return computeColor(mainRay, ctx);
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
        Intersection intersection = sc.nearestIntersection(ray);

        Color result = Color.black();
        
        if (intersection != null) {
            result = intersection.getIntersectedPrimitive().getMaterial()
                .computeColor(this, sc, intersection, ctx);
        }

        return result;
    }

    /**
     * x and z are the components in the screen coordinate.
     *
     * @param x The x coordinate on the rendering surface.
     * @param y The y coordinate on the rendering surface.
     *
     * @return The ray passing through the x, y point of the rendering surface.
     */
    public Ray rayThroughPixel(final int x, final int y) {

        Vector surfacePosition =
                getScene().getRenderingSurface().getPosition();

        Vector cameraPosition = getScene().getDefaultCamera().getPosition();
        double endZ = surfacePosition.z();
        double endY = surfacePosition.y() - y;
        double endX = x + surfacePosition.x();

        return Ray.joining(cameraPosition, new Vector(endX, endY, endZ));
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
