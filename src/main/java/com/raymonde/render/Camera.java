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

import com.raymonde.core.Vector;
import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Builder;

import javax.annotation.concurrent.ThreadSafe;

/**
 * {@code Camera} objects represents a point of view in a Scene
 */
@Immutable
@ThreadSafe
public class Camera {

    /**
     * The position of the camera.
     */
    private final Vector position;
    /**
     * The look at position.
     */
    private final Vector direction;
    /**
     * The up {@link Vector}
     */
    private final Vector up;
    /**
     * The distance from the camera origin to the surface.
     */
    private final double distance;
    private final RenderingSurfaceSpec renderingSurfaceSpec;

    /**
     *
     * @param position
     * @param direction
     * @param up
     * @param distance
     * @param width
     */
    @Builder
    protected Camera(final Vector position, final Vector direction, final Vector up, final double distance, final double width, final double height, final int pixelWidth, final int pixelHeight) {
        this.position = position;
        this.direction = direction.normalized();
        this.up = up.normalized();
        this.distance = distance;
        this.renderingSurfaceSpec = new RenderingSurfaceSpec(width, height, pixelWidth, pixelHeight);
    }

    /**
     *
     * @param pos
     * @param dir
     * @param up
     * @param distance
     * @param surfaceSpec
     */
    public Camera(final Vector pos, final Vector dir, final Vector up, final double distance, final RenderingSurfaceSpec surfaceSpec) {
        this.position = pos;
        this.direction = dir.normalized();
        this.up = up.normalized();
        this.distance = distance;
        this.renderingSurfaceSpec = surfaceSpec;
    }

    /**
     * @return the position
     */
    public Vector getPosition() {
        return this.position;
    }

    /**
     * @return the direction
     */
    public Vector getDirection() {
        return this.direction;
    }

    /**
     * @return the pixelWidth
     */
    public int getPixelWidth() {
        return renderingSurfaceSpec.getPixelWidth();
    }

    /**
     * @return the pixelHeight
     */
    public int getPixelHeight() {
        return renderingSurfaceSpec.getPixelHeight();
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return this.distance;
    }

    public RenderingSurface createRenderingSurface() {
        return new RenderingSurface(renderingSurfaceSpec.getPixelWidth(), renderingSurfaceSpec.getPixelWidth());
    }

    /**
     * Returns a {@link Ray} that goes from the camera origin to the specified pixel.
     * Basically it computes the coordinates of the specified pixel in the absolute coordinate system and constructs
     * a Ray that starts at the {@link Camera} origin and pass through this computed {@link Vector point}.
     *
     * @param pixel the {@link Pixel} the resulting ray should pass through
     *
     * @return the resulting {@link Ray}
     */
    public Ray rayThroughPixel(final Pixel pixel) {

        Vector surfaceCenter = position.add(direction.normalized().multiply(distance));
        Vector surfUnitY = up.normalized();
        Vector surfUnitX = direction.normalized().cross(up);

        double endZ = surfaceCenter.z();
        double endY = 0.0;
        double endX = 0.0;

        return Ray.joining(position, new Vector(endX, endY, endZ));
    }

    private static class RenderingSurfaceSpec {
        /**
         * The width in pixels of the surface.
         */
        private final int pixelWidth;

        /**
         * The height in pixels of the surface.
         */
        private final int pixelHeight;

        /**
         * The width of the surface (in world coordinate system)
         */
        private final double width;

        /**
         * The height of the surface (in world coordinate system)
         */
        private final double height;

        private RenderingSurfaceSpec(double width, double height, int pixelWidth, int pixelHeight) {
            this.width = width;
            this.height = height;
            this.pixelWidth = pixelWidth;
            this.pixelHeight = pixelHeight;
        }

        public int getPixelWidth() {
            return pixelWidth;
        }

        public int getPixelHeight() {
            return pixelHeight;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }
    }
}
