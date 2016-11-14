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
import com.raymonde.render.light.Light;
import com.raymonde.scene.Scene;
import lombok.Builder;
import lombok.val;

/**
 */
public class PhongMaterial extends AbstractMaterial implements Material {

    /**
     * The diffuse factor.
     */
    private final double diffuseFactor;

    /**
     * The specular factor.
     */
    private final double specularFactor;

    /**
     * Constructs a {@code PhongMaterial} with the specified diffuse and
     * specular parameters.
     *
     * @param diffuse The diffuse factor.
     * @param specular The specular factor.
     */
    @Builder
    public PhongMaterial(final double diffuse, final double specular, final Material subMaterial) {
        super(subMaterial);
        this.diffuseFactor = diffuse;
        this.specularFactor = specular;
    }
    
    @Override
    public Color computeColor(final Renderer renderer,
            final Scene scene,
            final Intersection intersection,
            final RenderingContext ctx) {

        val ray = intersection.getIncomingRay();

        val color = getSubMaterial().computeColor(renderer, scene, intersection, ctx);
        val ambientColor = scene.getAmbientColor();
        val intersectionPoint = intersection.getIntersectionPosition();

        Color diffuseColor = Color.black();
        Color specularColor = Color.black();

        for (Light light : scene.getLights()) {
           
            val vectorToLight = Vector.joining(intersectionPoint, light.getPosition());
            double distanceToLight = vectorToLight.length();

            val rayToLight = new Ray(intersectionPoint, vectorToLight);
            val directionToLight = rayToLight.direction();

            val occludingIntersection = scene.nearestIntersection(rayToLight);

            /*
             * A ray is not occluded unless there is a primitive between the ray
             * origin and the light.
             */
            if (occludingIntersection == null || occludingIntersection.getDistance() > distanceToLight) {

                Vector normal = intersection.normal();
                Color lightColor = light.colorAt(intersectionPoint);

                // Diffuse
                double diff = directionToLight.dot(normal) * diffuseFactor;

                if (diff > 0.) {
                    Color currentDiffuseColor = lightColor.multiply(color).multiply(diff);
                    diffuseColor = diffuseColor.add(currentDiffuseColor);
                }

                // Specular
                final Vector lightReflect = directionToLight
                        .reflected(normal)
                        .opposite()
                        .normalized();

                double spec = lightReflect.dot(ray.direction());

                if (spec < 0.) {
                    spec = Math.pow(Math.abs(spec), specularFactor);

                    Color currentSpecularColor = lightColor.multiply(spec);
                    specularColor = specularColor.add(currentSpecularColor);
                }
            }
        }

        return Color.black().add(ambientColor, diffuseColor, specularColor);
    }
}
