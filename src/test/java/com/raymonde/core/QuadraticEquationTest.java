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

package com.raymonde.core;

import org.junit.Test;

import static com.raymonde.core.QuadraticEquation.Result;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

public class QuadraticEquationTest {

    private final static double DELTA = 0.000000001;

    @Test
    public void shouldReturnTheCorrectRootsStatic() {
        // When
        Result result = QuadraticEquation.solve(2.0, 5.0, 2.0);

        // Expect
        assertThat(result.rootNumber()).isEqualTo(2);
        assertThat(result.firstRoot()).isEqualTo(-2.0, offset(DELTA));
        assertThat(result.secondRoot()).isEqualTo(-0.5, offset(DELTA));
    }

    /**
     * Test of solve method, of class QuadraticEquation.
     */
    @Test
    public void shouldReturnTheCorrectRootStatic() {
        // When
        Result result = QuadraticEquation.solve(0.0, 5.0, 2.0);

        // Expect
        assertThat(result.rootNumber()).isEqualTo(1);
        assertThat(result.firstRoot()).isEqualTo(-2.0/5.0, offset(DELTA));
        assertThat(result.secondRoot()).isEqualTo(-2.0/5.0, offset(DELTA));
    }


    @Test
    public void shouldReturnZeroRootsStatic() {
        // When
        Result result = QuadraticEquation.solve(2.0, -1.0, 2.0);

        // Expect
        assertThat(result.rootNumber()).isEqualTo(0);
    }

    @Test
    public void shouldReturnTheCorrectRoots() {
        // Given
        QuadraticEquation instance = new QuadraticEquation(2.0, 5.0, 2.0);

        // When
        Result result = instance.solve();

        // Then
        assertThat(result.rootNumber()).isEqualTo(2);
        assertThat(result.firstRoot()).isEqualTo(-2.0, offset(DELTA));
        assertThat(result.secondRoot()).isEqualTo(-0.5, offset(DELTA));
    }

    @Test
    public void shouldReturnTheCorrectRoot() {
        // Given
        QuadraticEquation instance = new QuadraticEquation(0.0, 5.0, 2.0);

        // When
        Result result = instance.solve();

        // Then
        assertThat(result.rootNumber()).isEqualTo(1);
        assertThat(result.firstRoot()).isEqualTo(-2.0/5.0, offset(DELTA));
        assertThat(result.secondRoot()).isEqualTo(-2.0/5.0, offset(DELTA));
    }

    @Test
    public void shouldReturnZeroRoots() {
        // Given
        QuadraticEquation instance = new QuadraticEquation(2.0, -1.0, 2.0);

        // When
        Result result = instance.solve();

        // THen
        assertThat(result.rootNumber()).isEqualTo(0);
    }


    @Test
    public void shouldReturnTheCorrectRootIfDiscriminantIsZero() {
        // Given
        QuadraticEquation instance = new QuadraticEquation(1.0, 2.0, 1.0);

        // When
        Result result = instance.solve();

        // Then
        assertThat(result.rootNumber()).isEqualTo(1);
        assertThat(result.firstRoot()).isEqualTo(-2.0/2.0, offset(DELTA));
        assertThat(result.secondRoot()).isEqualTo(-2.0/2.0, offset(DELTA));
    }

    @Test
    public void shouldReturnTheCorrectRootIfDiscriminantIsZeroStatic() {

        // When
        Result result = QuadraticEquation.solve(1.0, 2.0, 1.0);

        // Expect
        assertThat(result.rootNumber()).isEqualTo(1);
        assertThat(result.firstRoot()).isEqualTo(-2.0/2.0, offset(DELTA));
        assertThat(result.secondRoot()).isEqualTo(-2.0/2.0, offset(DELTA));
    }
}