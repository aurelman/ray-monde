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

/**
 *
 * @author aurelman
 */
public class QuadraticEquationTest {

    private final static double DELTA = 0.000000001;

    /**
     * Test of solve method, of class QuadraticEquation.
     */
    @Test
    public void shouldReturnTheCorrectRootsStatic() {
        Result result = QuadraticEquation.solve(2.0, 5.0, 2.0);

        assertThat(result.rootNumber()).isEqualTo(2);
        assertThat(result.firstRoot()).isEqualTo(-2.0, offset(DELTA));
        assertThat(result.secondRoot()).isEqualTo(-0.5, offset(DELTA));
    }

    /**
     * Test of solve method, of class QuadraticEquation.
     */
    @Test
    public void shouldReturnTheCorrectRootStatic() {
        Result result = QuadraticEquation.solve(0.0, 5.0, 2.0);

        assertThat(result.rootNumber()).isEqualTo(1);
        assertThat(result.firstRoot()).isEqualTo(-2.0/5.0, offset(DELTA));
    }


    @Test
    public void shouldReturnZeroRootsStatic() {
        Result result = QuadraticEquation.solve(2.0, -1.0, 2.0);

        assertThat(result.rootNumber()).isEqualTo(0);
    }

    /**
     * Test of solve method, of class QuadraticEquation.
     */
    @Test
    public void shouldReturnTheCorrectRoots() {
        QuadraticEquation instance = new QuadraticEquation(2.0, 5.0, 2.0);
        Result result = instance.solve();

        assertThat(result.rootNumber()).isEqualTo(2);
        assertThat(result.firstRoot()).isEqualTo(-2.0, offset(DELTA));
        assertThat(result.secondRoot()).isEqualTo(-0.5, offset(DELTA));
    }

    /**
     * Test of solve method, of class QuadraticEquation.
     */
    @Test
    public void shouldReturnTheCorrectRoot() {

        QuadraticEquation instance = new QuadraticEquation(0.0, 5.0, 2.0);
        Result result = instance.solve();

        assertThat(result.rootNumber()).isEqualTo(1);
        assertThat(result.firstRoot()).isEqualTo(-2.0/5.0, offset(DELTA));
    }

    @Test
    public void shouldReturnZeroRoots() {

        QuadraticEquation instance = new QuadraticEquation(2.0, -1.0, 2.0);
        Result result = instance.solve();

        assertThat(result.rootNumber()).isEqualTo(0);
    }
}