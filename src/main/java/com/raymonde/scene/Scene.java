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
package com.raymonde.scene;

import com.google.common.base.MoreObjects;
import com.raymonde.core.Color;
import com.raymonde.render.Camera;
import com.raymonde.render.IntersectionResult;
import com.raymonde.render.Ray;
import com.raymonde.render.RenderingSurface;
import com.raymonde.render.light.Light;
import com.raymonde.render.primitive.Primitive;
import lombok.Builder;
import lombok.Singular;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@code Scene} object are responsible of representing the description
 * of what would be rendered.
 */
@Builder
public class Scene {

    private final static Logger logger = LoggerFactory.getLogger(Scene.class);

    /**
     * The minimum distance a ray must have run to valid collision.
     */
    private final static double DELTA_COLLISION_DETECTION = 0.000000001;

    private final SpatialPartitionFactory spatialPartitionFactory = new DefaultSpatialPartitionFactory();
    /**
     * The surface where the scene will be rendered.
     */
    private RenderingSurface renderingSurface;
    
    /**
     * List of primitives
     */
    @Singular
    private Map<String, Primitive> primitives = new LinkedHashMap<>();

    /**
     * List of lights
     */
    @Singular
    private Map<String, Light> lights = new LinkedHashMap<>();

    /**
     * List of cameras
     */
    @Singular
    private Map<String, Camera> cameras = new LinkedHashMap<>();

    /**
     * The ambient color of the scene.
     */
    private Color ambientColor;

    /**
     * Computes the nearest intersected primitive with the specified ray and
     * and returns intersection data,
     * {@code null} if no intersection occurs.
     * 
     * @param ray The ray which might intersect one or more primitive.
     * 
     * @return The {@link IntersectionResult} object, which contains data about
     * intersection. Returns {@code null} if no intersection occurs.
     */
    public IntersectionResult nearestIntersection(final Ray ray) {

        IntersectionResult minInter = null;
        // Nearest collided primitive, distances
        String minPrimitiveName = null;
        double minDistance = Double.POSITIVE_INFINITY;


        double delta = Scene.DELTA_COLLISION_DETECTION;
        for (Map.Entry<String, Primitive> primitive : primitives.entrySet()) {
            val p = primitive.getValue();

            val res = p.intersect(ray);

            if (res.intersect()) {
                double distance = res.distance();

                /*
                 * - Avoid intersection detection with previous one
                 * - Avoid intersection with an other very close other primitive.
                 */
                if (distance < minDistance
                        && distance > delta
                        && minDistance - distance > delta) {
                    minDistance = distance;
                    minPrimitiveName = primitive.getKey();
                    minInter = res;
                }
            }
        }

        if (minInter != null) {
            logger.debug("intersection detected with primitive {} for ray {}", minPrimitiveName, ray);
        }

        return minInter;
    }

    /**
     * @return The collection of lights contained in the scene.
     */
    public Collection<Light> getLights() {
        return lights.values();
    }

    /**
     * Returns the default camera.
     * 
     * @return The default camera.
     */
    public Camera getDefaultCamera() {
        return cameras.values().iterator().next();
    }

    /**
     * @param renderingSurface The rendering surface to set.
     */
    public void setSurface(final RenderingSurface renderingSurface) {
        this.renderingSurface = renderingSurface;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ambientColor", ambientColor)
                .add("primitives", primitives)
                .add("lights", lights)
                .add("cameras", cameras)
                .toString();
    }

    /**
     * Returns the ambient color of the scene.
     * 
     * @return The ambient color of the scene.
     */
    public Color getAmbientColor() {
        return ambientColor;
    }
}
