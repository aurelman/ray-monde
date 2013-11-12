/*
 * Copyright (C) 2009 Manoury Aurélien
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
     * The ambiant color of the scene.
     */
    private Color ambiantColor;

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
        return getPrimitivesMap().values();
    }

    /**
     *
     * @return The collection of lights contained in the scene.
     */
    public Collection<Light> getLights() {
        return getLightsMap().values();
    }

    /**
     *
     * @return The collection of cameras contained in the scene.
     */
    public Collection<Camera> getCameras() {
        return getCamerasMap().values();
    }

    /**
     * 
     * @return The map of the primitives contained in the scene
     * indexed by their name
     * 
     */
    protected Map<String, Primitive> getPrimitivesMap() {
        return this.primitives;
    }

    /**
     *
     * @return The map of the lights contained in the scene
     * indexed by their name.
     */
    protected Map<String, Light> getLightsMap() {
        return this.lights;
    }

    /**
     *
     * @return The map of the lights contained in the scene
     * indexed by their name.
     */
    protected Map<String, Camera> getCamerasMap() {
        return this.cameras;
    }

    /**
     *
     * @param primitives Collection of <code>Primitive</code> object.
     */
    public void setPrimitives(final Collection<Primitive> primitives) {
        getPrimitivesMap().clear();
        addPrimitives(primitives);
    }

    /**
     *
     * @param lights Collection of <code>Light</code> object.
     */
    public void setLights(final Collection<Light> lights) {
        getLightsMap().clear();
        addLights(lights);
    }

    /**
     *
     * @param cameras Collection of <code>Light</code> object.
     */
    public void setCameras(final Collection<Camera> cameras) {
        getCamerasMap().clear();
        addCameras(cameras);
    }
       
    /**
     * Adds the specified primitive to the scene.
     *
     * @param primitive The primitive to add to the scene.
     */
    public void addPrimitive(final Primitive primitive) {
        getPrimitivesMap().put(primitive.getName(), primitive);
    }

    /**
     * Adds the specified light to the scene.
     *
     * @param light The light to add.
     */
    public void addLight(final Light light) {
        getLightsMap().put(light.getName(), light);
    }

    /**
     * Adds the specified camera to the scene.
     *
     * @param camera The camera to add.
     */
    public void addCamera(final Camera camera) {
        getCamerasMap().put(camera.getName(), camera);
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
     * Returns the Primitive identified by the specified name.
     *
     * @param name The name of the Primitive.
     *
     * @return The primitive identified by the specified name.
     */
    public Primitive getPrimitive(final String name) {
        return getPrimitivesMap().get(name);
    }

    /**
     * Returns the light identified by the specified name.
     * 
     * @param name The name of the light.
     * 
     * @return The light identified by the specified name.
     */
    public Light getLight(final String name) {
        return getLightsMap().get(name);
    }
    
    /**
     * Removes the primitive identified by the specified name.
     * 
     * @param name The name of the primitive to remove.
     */
    public void removePrimitive(final String name) {
        getPrimitivesMap().remove(name);
    }

    /**
     * Removes the light identified by the specified name.
     * 
     * @param name The name of the light to remove.
     */
    public void removeLight(final String name) {
        getLightsMap().remove(name);
    }

    /**
     * Removes the camera identified by the specified name.
     *
     * @param name The name of the camera to remove.
     */
    public void removeCamera(final String name) {
        getCamerasMap().remove(name);
    }
    
    /**
     *
     * @param primitive The primitive to remove.
     */
    public void removePrimitive(final Primitive primitive) {
        removePrimitive(primitive.getName());
    }

    /**
     *
     * @param light The light to remove.
     */
    public void removeLight(final Light light) {
        removeLight(light.getName());
    }

    /**
     *
     * @param camera The camera to remove.
     */
    public void removeCamera(final Camera camera) {
        removeCamera(camera.getName());
    }

    /**
     * @return The rendering surface.
     */
    public Surface getSurface() {
        return this.renderingSurface;
    }

    /**
     * Returns the default camera.
     * 
     * @return The default camera.
     */
    public Camera getDefaultCamera() {
        return getCamerasMap().values().iterator().next();
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
     * Returns the ambiant color of the scene.
     * 
     * @return The ambiant color of the scene.
     */
    public Color getAmbiantColor() {
        return this.ambiantColor;
    }

    /**
     * Sets the specified color as the ambiant color of the scene.
     * 
     * @param ambiantColor The ambiant color to set to the scene.
     */
    public void setAmbiantColor(final Color ambiantColor) {
        this.ambiantColor = ambiantColor;
    }
}
