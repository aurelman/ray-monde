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

import com.raymonde.core.Color;
import com.raymonde.render.Camera;
import com.raymonde.render.Intersection;
import com.raymonde.render.Ray;
import com.raymonde.render.Surface;
import com.raymonde.render.light.Light;
import com.raymonde.render.primitive.Primitive;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>Scene</code> object are responsible of representing the description 
 * of what would be rendered.
 * 
 * @author aurelman
 */
public class Scene {
    
    private final SpatialPartitionFactory spatialPartitionFactory = new DefaultSpatialPartitionFactory();
    
    /**
     * The minimum distance a ray must have run to valid collision.
     */
    private static double DELTA_COLLISION_DETECTION = 0.000000001;

    /**
     * The surface where the scene will be rendered.
     */
    private Surface renderingSurface;
    
    /**
     * List of primitives
     */
    private Map<String, Primitive> primitives;

    /**
     * List of lights
     */
    private Map<String, Light> lights;

    /**
     * List of cameras
     */
    private Map<String, Camera> cameras;

    /**
     * The ambient color of the scene.
     */
    private Color ambientColor;

    /**
     *
     */
    public Scene() {
        this(null, null, null);
    }
    
    /**
     *
     * @param primitives The list of the primitives in the scene.
     * @param lights The list of the lights in the scene.
     * @param cameras The list of the cameras in the scene.
     */
    public Scene(final Collection<Primitive> primitives,
            final Collection<Light> lights,
            final Collection<Camera> cameras) {

        // Initialize entities
        if (primitives == null || primitives.isEmpty()) {
            this.primitives = new HashMap<>();
        } else {
            this.primitives = new HashMap<>(primitives.size());
            addPrimitives(primitives);
        }

        // Initialize lights
        if (lights == null || lights.isEmpty()) {
            this.lights = new HashMap<>();
        } else {
            this.lights = new HashMap<>(lights.size());
            addLights(lights);
        }

        // Initialize cameras
        if (cameras == null || cameras.isEmpty()) {
            this.cameras = new HashMap<>();
        } else {
            this.cameras = new HashMap<>(cameras.size());
            addCameras(cameras);
        }
    }

    /**
     * Computes the nearest intersected primitive with the specified ray and
     * and returns intersection data,
     * <code>null</code> if no intersection occurs.
     * 
     * @param ray The ray which might intersect one or more primitive.
     * 
     * @return The <code>Intersection</code> object, which contains data about
     * intersection. Returns <code>null</code> if no intersection occurs.
     */
    public Intersection nearestIntersection(final Ray ray) {
        Intersection res = null;

        // Nearest collided primitive, distances
        Primitive minPrimitive = null;
        double minDistance = Double.POSITIVE_INFINITY;
        
        double distance;

        double delta = Scene.DELTA_COLLISION_DETECTION;
        for (Primitive primitive : getPrimitives()) {
            distance = primitive.intersect(ray);

            /*
             * - Avoid intersection detection with previous one
             * - Avoid intersection with an other very close other primitive.
             */
            if (distance < minDistance
                && distance > delta 
                && minDistance - distance > delta) {
                minDistance = distance;
                minPrimitive = primitive;
            }
        }

        if (minPrimitive != null) {
            res = new Intersection(minPrimitive, ray, minDistance);
        }
        return res;
    }

    /**
     *
     * @return The collection of the primitives contained in the scene.
     */
    public Collection<Primitive> getPrimitives() {
        return primitives.values();
    }

    /**
     *
     * @return The collection of lights contained in the scene.
     */
    public Collection<Light> getLights() {
        return lights.values();
    }

       
    /**
     * Adds the specified primitive to the scene.
     *
     * @param primitive The primitive to add to the scene.
     */
    public void addPrimitive(final Primitive primitive) {
        primitives.put(primitive.getName(), primitive);
    }

    /**
     * Adds the specified light to the scene.
     *
     * @param light The light to add.
     */
    public void addLight(final Light light) {
        lights.put(light.getName(), light);
    }

    /**
     * Adds the specified camera to the scene.
     *
     * @param camera The camera to add.
     */
    public void addCamera(final Camera camera) {
        cameras.put(camera.getName(), camera);
    }

    /**
     * Adds each of the primitive the specified collection contains
     * to the scene.
     * 
     * @param primitives The primitives to add.
     */
    public void addPrimitives(final Collection<Primitive> primitives) {
        for(Primitive primitive : primitives) {
            addPrimitive(primitive);
        }
    }

    /**
     * Adds each of the light the specified collection contains
     * to the scene.
     *
     * @param lights The lights to add.
     */
    public void addLights(final Collection<Light> lights) {
        for (Light light : lights) {
           addLight(light);
        }
    }

    /**
     * Adds each of the camera the specified collection contains
     * to the scene.
     *
     * @param cameras The cameras to add.
     */
    public void addCameras(final Collection<Camera> cameras) {
        for(Camera camera : cameras) {
            addCamera(camera);
        }
    }

    /**
     * @return The rendering surface.
     */
    public Surface getSurface() {
        return renderingSurface;
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
    public void setSurface(final Surface renderingSurface) {
        this.renderingSurface = renderingSurface;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Scene : primitives = (");
        for (Primitive p : getPrimitives()) {
            sb.append(p).append(", ");
        }
        sb.append(")]");
        
        return sb.toString();
    }

    /**
     * Returns the ambient color of the scene.
     * 
     * @return The ambient color of the scene.
     */
    public Color getAmbientColor() {
        return ambientColor;
    }

    /**
     * Sets the specified color as the ambient color of the scene.
     * 
     * @param ambientColor The ambient color to set to the scene.
     */
    public void setAmbientColor(final Color ambientColor) {
        this.ambientColor = ambientColor;
    }
}
