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
import com.raymonde.render.primitive.Primitive;

/**
 * <code>ReflectiveMaterial</code> adds refflectivity properties to
 * primitive. Reflectivity factor tells which quantity of the incoming light
 * is reflected.
 * 
 * @author aurelman
 */
public class RefractiveMaterial extends AbstractMaterial implements Material {

    /**
     * The reflectivity.
     */
    private double refraction;

    /**
     * Constructs an empty <code>RefractiveMaterial</code>.
     */
    public RefractiveMaterial() {

    }

    /**
     *
     * @param name The name of the material.
     */
    public RefractiveMaterial(final String name) {
        super(name);
    }

    /**
     * Constructs a <code>RefractiveMaterial</code> with the specified
     * refraction index.
     *
     * @param name The name of the material.
     * @param refraction The refraction index.
     */
    public RefractiveMaterial(final String name, final double refraction) {
        super(name);
        this.refraction = refraction;
    }
    
    @Override
    public Color computeColor(final Renderer renderer,
            final Scene scene,
            final Intersection inter,
            final RenderingContext ctx) {
        Color refractColor = Color.black();
        Color surfaceColor =
                getMaterial().computeColor(renderer, scene, inter, ctx);
        
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
     * Returns the reflectivity factor of the material.
     *
     * @return The reflectivity factor.
     */
    public double getRefraction() {
        return this.refraction;
    }
    
    /**
     * Computes the refracted ray according the incoming one.
     * 
     * @param ray The incoming ray.
     * @param inter The intersection result.
     * @param ctx The current rendering context.
     *
     * @return The reflection ray.
     */
    protected Ray refractedRay(final Ray ray, final Intersection inter,
            final RenderingContext ctx) {
        double refract = ctx.getRefraction();
        Primitive primitive = inter.getPrimitive();
        double refB = getRefraction();
        double n = refract / refB;

        Vector normal = primitive.normalAt(inter.getIntersectionPosition());
        if (ctx.getDepth() % 2 == 0) {
            normal = normal.opposite();
        }
        double cosi = ray.getDirection().dot(normal);
        double cosr = Math.sqrt(1 - n*n*(1 - cosi*cosi));

        Vector term1 = ray.getDirection().multiply(n);
        Vector term2 = normal.multiply(cosr - n*cosi);

        Vector direction = term1.substract(term2);
         
        return new Ray(inter.getIntersectionPosition(), direction);
    }
}
