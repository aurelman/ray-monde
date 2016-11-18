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
import com.raymonde.render.IntersectionResult;
import com.raymonde.render.Ray;
import com.raymonde.render.Renderer;
import com.raymonde.render.RenderingContext;
import com.raymonde.scene.Scene;
import lombok.Builder;

/**
 * {@code ReflectiveMaterial} adds reflectivity properties to
 * primitive. Reflectivity factor tells which quantity of the incoming light
 * is reflected.
 */
public class ReflectiveMaterial extends AbstractMaterial implements Material {

    /**
     * The reflectivity.
     */
    private final double reflectivity;

    /**
     * Constructs a {@code ReflectiveMaterial} with the specified
     * reflectivity.
     *
     * @param reflectivity the reflection factor.
     * @param subMaterial the subMaterial
     */
    @Builder
    public ReflectiveMaterial(final double reflectivity, final Material subMaterial) {
        super(subMaterial);
        this.reflectivity = reflectivity;
    }
    
    @Override
    public Color computeColor(final Renderer renderer,
            final Scene scene,
            final IntersectionResult inter,
            final RenderingContext ctx) {
        Color reflectColor = Color.black();

        Color surfaceColor = getSubMaterial().computeColor(renderer, scene, inter, ctx);
        
        Ray reflected = inter.reflectedRay();

        // TODO: deleted to avoid commiting not compiling code
        /**
        if (ctx.getDepth() < renderer.getMaxDepth()) {
            reflectColor = renderer.computeColor(reflected,
                    RenderingContext.incremented(ctx));
        }
         */

        double refl = reflectivity;
        return surfaceColor.multiply(1. - refl)
                .add(reflectColor.multiply(refl));
    }

    /**
     * Computes the reflected ray according the incoming one.
     * 
     * @param ray The incoming ray.
     * @param inter The intersection.
     *
     * @return The reflection ray.
     */
    protected Ray reflectedRay(final Ray ray, final IntersectionResult inter) {
        Vector intersectionPoint = inter.getIntersectionPosition();
        Vector normal = inter.primitive().normalAt(intersectionPoint);
        Vector reflected = ray.direction().reflected(normal);
        return new Ray(intersectionPoint, reflected);
    }
}
