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

/**
 *
 * @author aurelman
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
     *
     * @param x The x position.
     * @param y The y position.
     */
    protected void shootThroughPixel(final int x, final int y) {
        RenderingContext ctx = new RenderingContext(0, 1.);
        ctx.setRefraction(1.0);
        Ray mainRay = rayThroughPixel(x, y);
        
        Color color = computeColor(mainRay, ctx);
        scene.getSurface().setColor(x, y, color);
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
        Scene sc = scene;
        Intersection intersection = sc.nearestIntersection(ray);

        Color result = Color.black();
        // No intersection
        if (intersection == null) {
            return result;
        }

        //return intersection.getPrimitive().getMaterial()
        //        .computeColor(this, sc, intersection, ctx);
    
        return null;
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
                scene.getSurface().getPosition();

        Vector cameraPosition = scene.getDefaultCamera().getPosition();
        double endZ = surfacePosition.getZ();
        double endY = surfacePosition.getY() - y;
        double endX = x + surfacePosition.getX();

        return Ray.joining(cameraPosition, new Vector(endX, endY, endZ));
    }
    
}
