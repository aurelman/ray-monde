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
     * The complementary vector to define the 3 orthogonal vector of the system. (directionCrossUp, direction, up).
     * It is the result of the cross product between {@code direction} and {@code up} {@link Vector}s
     */
    private final Vector directionCrossUp;
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
        directionCrossUp = direction.cross(up).normalized();
        this.renderingSurfaceSpec = new RenderingSurfaceSpec(width, height, pixelWidth, pixelHeight, distance);
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

        // Is unique for camera, should be computed at construction time.
        Vector xVector = direction.cross(up).normalized();

        final double yCoeff = (renderingSurfaceSpec.getPixelHeight() - 1) / 2. - pixel.y();
        final double xCoeff = pixel.x() - ((renderingSurfaceSpec.getPixelWidth() - 1) / 2.);

        double somethingDependingOnX = xCoeff * renderingSurfaceSpec.widthOfPixel();
        double somethingDependingOnY = yCoeff * renderingSurfaceSpec.heightOfPixel();


        return position
                .add(direction.multiply(renderingSurfaceSpec.getDistance()))
                .add(directionCrossUp.multiply(somethingDependingOnX))
                .add(up.multiply(somethingDependingOnY));
    }

    @ThreadSafe
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

        /**
         * The cached height of a pixel of the surface
         */
        private transient Double _heightOfPixel;

        /**
         * The cached width of a pixel of the surface
         */
        private transient Double _widthOfPixel;

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

        public double widthOfPixel() {
            if (_widthOfPixel == null) {
                _widthOfPixel = width / pixelWidth;
            }
            return _widthOfPixel;
        }

        public double heightOfPixel() {
            if (_heightOfPixel == null) {
                _heightOfPixel = height / pixelHeight;
            }

            return _heightOfPixel;
        }
    }
}
