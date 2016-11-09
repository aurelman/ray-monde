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
import com.raymonde.render.primitive.Primitive;
import com.raymonde.scene.Scene;
import lombok.Builder;

/**
 * {@code RefractiveMaterial} adds refractive properties to
 * primitive. Refraction factor tells how an incoming ray will go through the surface.
 */
public class RefractiveMaterial extends AbstractMaterial implements Material {

    /**
     * The reflectivity.
     */
    private final double refraction;

    /**
     * Constructs a {@code RefractiveMaterial} with the specified
     * refraction index.
     *
     * @param refraction The refraction index.
     */
    @Builder
    public RefractiveMaterial(final double refraction, final Material subMaterial) {
        super(subMaterial);
        this.refraction = refraction;
    }
    
    @Override
    public Color computeColor(final Renderer renderer,
            final Scene scene,
            final Intersection inter,
            final RenderingContext ctx) {
        Color refractColor = Color.black();
        Color surfaceColor =
                getSubMaterial().computeColor(renderer, scene, inter, ctx);
        
        Ray refracted = refractedRay(inter.getIncomingRay(), inter, ctx);


        // TODO: deleted to avoid commiting not compiling code
        /*
        if (ctx.getDepth() < renderer.getMaxDepth()) {
            RenderingContext newCtx = new RenderingContext(
                    ctx.getDepth()+1, getRefraction());
            refractColor = renderer.computeColor(refracted,newCtx);
        }
        */

        double refl = 0.5;
        return surfaceColor.multiply(1. - refl)
                .add(refractColor.multiply(refl));
    }

    /**
     * Computes the refracted ray according the incoming one.
     * 
     * @param ray The incoming ray.
     * @param inter The intersection result.
     * @param ctx The current rendering context.
     *
     * @return The refracted ray.
     */
    protected Ray refractedRay(final Ray ray, final Intersection inter, final RenderingContext ctx) {
        double refract = ctx.getRefraction();
        Primitive primitive = inter.getIntersectedPrimitive();
        double refB = refraction;
        double n = refract / refB;

        Vector normal = primitive.normalAt(inter.getIntersectionPosition());
        if (ctx.getDepth() % 2 == 0) {
            normal = normal.opposite();
        }
        double cosi = ray.direction().dot(normal);
        double cosr = Math.sqrt(1 - n*n*(1 - cosi*cosi));

        Vector term1 = ray.direction().multiply(n);
        Vector term2 = normal.multiply(cosr - n*cosi);

        Vector direction = term1.subtract(term2);
         
        return new Ray(inter.getIntersectionPosition(), direction);
    }
}
