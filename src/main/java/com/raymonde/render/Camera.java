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
     * The rendering surface's information
     */
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
        this.renderingSurfaceSpec = new RenderingSurfaceSpec(width, height, pixelWidth, pixelHeight, distance);
    }

    /**
     *
     * @param pos
     * @param dir
     * @param up
     * @param surfaceSpec
     */
    public Camera(final Vector pos, final Vector dir, final Vector up, final RenderingSurfaceSpec surfaceSpec) {
        this.position = pos;
        this.direction = dir.normalized();
        this.up = up.normalized();
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


    public RenderingSurface createRenderingSurface() {
        return new RenderingSurface(renderingSurfaceSpec.getPixelWidth(), renderingSurfaceSpec.getPixelHeight());
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
        return Ray.joining(position, absolutePositionOfPixel(pixel));
    }

    /**
     * Computes the absolute position of the specified pixel on the screen.
     *
     * @param pixel
     * @return a {@code Vector} representing the absolute position of the specified pixel on the rendering screen.
     */
    private Vector absolutePositionOfPixel(final Pixel pixel) {

        Vector dirVector = direction.normalized();
        Vector yVector = up.normalized();

        // Is unique for camera, should be computed at construction time.
        Vector xVector = direction.cross(up).normalized();

        // Need to be computed accordingly
        double somethingDependingOnX = pixel.x() * 1.0;
        double somethingDependingOnY = pixel.y() * 1.0;

        return position
                .add(dirVector.multiply(renderingSurfaceSpec.getDistance()))
                .add(xVector.multiply(somethingDependingOnX))
                .add(yVector.multiply(somethingDependingOnY));
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

        /**
         * The distance from the camera's origin position
         */
        private final double distance;

        private RenderingSurfaceSpec(double width, double height, int pixelWidth, int pixelHeight, double distance) {
            this.width = width;
            this.height = height;
            this.pixelWidth = pixelWidth;
            this.pixelHeight = pixelHeight;
            this.distance = distance;
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

        public double getDistance() {
            return distance;
        }
    }
}
