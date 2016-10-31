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
import com.raymonde.render.light.Light;
import com.raymonde.render.Ray;
import com.raymonde.render.Renderer;
import com.raymonde.render.RenderingContext;
import com.raymonde.scene.Scene;
import com.raymonde.render.primitive.Primitive;

/**
 *
 * @author aurelman
 */
public class PhongMaterial extends AbstractMaterial implements Material {

    /**
     * The diffuse factor.
     */
    private double diffuseFactor;

    /**
     * The specular factor.
     */
    private double specularFactor;

    /**
     * Constructs an empty <code>PhongMaterial</code>.
     */
    public PhongMaterial() {
        super();
    }

    /**
     * Constructs a <code>PhongMaterial</code> with the specified diffuse and
     * specular.
     *
     * @param diffuse The diffuse factor.
     * @param specular The specular factor.
     */
    public PhongMaterial(
            final double diffuse, final double specular) {
        this.diffuseFactor = diffuse;
        this.specularFactor = specular;
    }
    
    @Override
    public Color computeColor(final Renderer renderer,
            final Scene scene,
            final Intersection intersection,
            final RenderingContext ctx) {

        Ray ray = intersection.getIncomingRay();
        Primitive object = intersection.getPrimitive();

        Color color = getMaterial().
                computeColor(renderer, scene, intersection, ctx);
        Color ambiantColor = scene.getAmbientColor();
        Color diffuseColor = Color.black();
        Color specularColor = Color.black();

        Intersection occludingIntersection;

        for (Light light : scene.getLights()) {
           
            Vector intersectionPoint = intersection.getIntersectionPosition();
            Vector vectorToLight =
                    Vector.joining(intersectionPoint, light.getPosition());
            double distanceToLight = vectorToLight.length();
            
            Ray rayToLight = new Ray(intersectionPoint, vectorToLight);
            Vector directionToLight = rayToLight.getDirection();

            occludingIntersection = scene.nearestIntersection(rayToLight);

            /*
             * A ray is not occluded unless there is a primitive between the ray
             * origin and the light.
             */
            if (occludingIntersection == null
                || occludingIntersection.getDistance() > distanceToLight) {

                Vector normal = object.normalAt(intersectionPoint);
                Color lightColor = light.colorAt(intersectionPoint);
                
                // Diffuse
                double diff = directionToLight.dot(normal)*getDiffuseFactor();

                if (diff > 0.) {
                    Color currentDiffuseColor =
                            lightColor.multiply(color).multiply(diff);
                    
                    diffuseColor = diffuseColor.add(currentDiffuseColor);
                }

                // Specular
                Vector lightReflect =
                        directionToLight.reflected(normal)
                        .opposite()
                        .normalized();

                double spec = lightReflect.dot(ray.getDirection());

                if (spec < 0.) {
                    spec = Math.pow(Math.abs(spec), getSpecularFactor());

                    Color currentSpecularColor = lightColor.multiply(spec);
                    specularColor = specularColor.add(currentSpecularColor);
                }
            }
        }

        return Color.black().add(ambiantColor, diffuseColor, specularColor);
    }

    /**
     * @return the diffuseFactor
     */
    public double getDiffuseFactor() {
        return diffuseFactor;
    }

    /**
     * @return the specularFactor
     */
    public double getSpecularFactor() {
        return specularFactor;
    }
}
