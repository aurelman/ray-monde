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
import com.raymonde.core.Vector;
import com.raymonde.render.Intersection;
import com.raymonde.render.Ray;
import com.raymonde.render.Renderer;
import com.raymonde.render.RenderingContext;
import com.raymonde.scene.Scene;

/**
 * {@code ReflectiveMaterial} adds reflectivity properties to
 * primitive. Reflectivity factor tells which quantity of the incoming light
 * is reflected.
 */
public class ReflectiveMaterial extends AbstractMaterial implements Material {

    /**
     * The reflectivity.
     */
    private double reflectivity;

    /**
     * Constructs an empty {@code ReflectiveMaterial}.
     */
    public ReflectiveMaterial() {
        super();
    }

    /**
     * Constructs a {@code ReflectiveMaterial} with the specified
     * reflectivity.
     *
     * @param reflectivity The reflection factor.
     */
    public ReflectiveMaterial(final double reflectivity) {
        this.reflectivity = reflectivity;
    }
    
    @Override
    public Color computeColor(final Renderer renderer,
            final Scene scene,
            final Intersection inter,
            final RenderingContext ctx) {
        Color reflectColor = Color.black();
        Color surfaceColor =
                getMaterial().computeColor(renderer, scene, inter, ctx);
        
        // Ray reflected = reflectedRay(inter.getIncomingRay(), inter);
        Ray reflected = inter.getReflectedRay();

        // TODO: deleted to avoid commiting not compiling code
        /**
        if (ctx.getDepth() < renderer.getMaxDepth()) {
            reflectColor = renderer.computeColor(reflected,
                    RenderingContext.incremented(ctx));
        }
         */

        double refl = getReflectivity();
        return surfaceColor.multiply(1. - refl)
                .add(reflectColor.multiply(refl));
    }

    /**
     * Returns the reflectivity factor of the material.
     *
     * @return The reflectivity factor.
     */
    public double getReflectivity() {
        return reflectivity;
    }
    
    /**
     * Computes the reflected ray according the incoming one.
     * 
     * @param ray The incoming ray.
     * @param inter The intersection result.
     *
     * @return The reflection ray.
     */
    protected Ray reflectedRay(final Ray ray, final Intersection inter) {
        Vector intersectionPoint = inter.getIntersectionPosition();
        Vector normal = inter.getPrimitive().normalAt(intersectionPoint);
        Vector reflected = ray.getDirection().reflected(normal);
        return new Ray(intersectionPoint, reflected);
    }
}
