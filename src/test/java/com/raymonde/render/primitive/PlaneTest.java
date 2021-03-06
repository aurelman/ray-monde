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
import lombok.val;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

public class PlaneTest {

    @Test
    public void shouldDetectIntersectionWithPlan() {
        // When
        val plane = Plane.builder()
                .normal(Vector.builder()
                        .x(0.)
                        .y(0.)
                        .z(-1.)
                        .build())
                .distance(100.)
                .build();


        val ray = new Ray(Vector.builder()
                .x(0.)
                .y(0.)
                .z(0.)
                .build(), Vector.builder()
                .x(0.)
                .y(0.)
                .z(1.)
                .build());

        // When
        val result = plane.intersect(ray);

        // Then
        assertThat(result.intersect()).isTrue();
        assertThat(result.distance()).isCloseTo(100., offset(0.00000001));
    }
}