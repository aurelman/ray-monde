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

package com.raymonde.render.primitive;

import com.raymonde.core.Vector;
import com.raymonde.render.Ray;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;


public class SphereTest {

    @Test
    public void shouldNotIntersectSphere() {
        // Given
        Sphere sphere = Sphere.builder()
                .origin(Vector.builder()
                    .x(0.)
                    .y(0.)
                    .z(-100.)
                    .build())
                .radius(10.)
                .build();

        Ray ray = Ray.joining(Vector.builder()
                .x(0)
                .y(0)
                .z(0)
                .build(), Vector.builder()
                .x(0)
                .y(11)
                .z(-100)
                .build());

        // Expect
        assertThat(sphere.intersect(ray)).isCloseTo(Double.POSITIVE_INFINITY, offset(0.00000001));
    }

    @Test
    public void rayRayGoingThroughSphereShouldIntersect() {
        // Given
        Sphere sphere = Sphere.builder()
                .origin(Vector.builder()
                        .x(0.)
                        .y(0.)
                        .z(-100.)
                        .build())
                .radius(10.)
                .build();

        Ray ray = Ray.joining(Vector.builder()
                .x(0)
                .y(0)
                .z(0)
                .build(), Vector.builder()
                .x(0)
                .y(10)
                .z(-100)
                .build());

        // Expect
        assertThat(sphere.intersect(ray)).isCloseTo(Math.sqrt(100 * 100 + 10 * 10), withinPercentage(2));
    }


    @Test
    public void rayRayJustTouchingSphereShouldIntersect() {
        // Given
        Sphere sphere = Sphere.builder()
                .origin(Vector.builder()
                        .x(0.)
                        .y(0.)
                        .z(-100.)
                        .build())
                .radius(10.)
                .build();

        Ray ray = Ray.joining(Vector.builder()
                .x(0)
                .y(0)
                .z(0)
                .build(), Vector.builder()
                .x(0)
                .y(0.0)
                .z(-100)
                .build());

        // Expect
        assertThat(sphere.intersect(ray)).isCloseTo(90.0, offset(0.00000001));
    }
}