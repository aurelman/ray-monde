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
import com.raymonde.render.primitive.Sphere;
import lombok.val;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntersectionResultTest {

    @Test
    public void shouldComputeReflectedRay() {
        // Given
        val incomingRay = new Ray(Vector.builder()
                .x(4.)
                .y(0.)
                .z(0.)
                .build(), Vector.builder()
                .x(-1.)
                .y(0.)
                .z(0.)
                .build());


        val sphere = Sphere.builder()
                .origin(Vector.builder()
                    .x(0.)
                    .y(0.)
                    .z(0.)
                    .build())
                .radius(1.)
                .build();

        val instance = IntersectionResult.builder()
                .primitive(sphere)
                .ray(incomingRay)
                .distance(3.)
                .build();

        // When
        val result = instance.reflectedRay();

        // Then
        val expResult = new Ray(Vector.builder()
                .x(1.)
                .y(0.)
                .z(0.)
                .build(), Vector.builder()
                .x(1.)
                .y(0.)
                .z(0.)
                .build());

        assertThat(result).isEqualTo(expResult);
    }
}
