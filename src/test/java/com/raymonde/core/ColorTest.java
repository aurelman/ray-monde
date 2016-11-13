
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

public class ColorTest {

    private final static double DELTA = 0.001;

    @Test
    public void shouldReturnRGBComponentAsAnInteger() {
        // Given
        Color color = new Color(1.0, 1.0, 1.0);

        // Expect
        assertThat(color.rgb()).isEqualTo(0xffffffff);
    }

    @Test
    public void shouldReturnRedComponent() {
        // Given
        Color color = new Color(1.0, 1.0, 1.0);

        // Expect
        assertThat(color.r()).isCloseTo(1.0, offset(DELTA));
    }

    @Test
    public void shouldReturnGreenComponent() {
        // Given
        Color color = new Color(1.0, 1.0, 1.0);

        // Expect
        assertThat(color.g()).isCloseTo(1.0, offset(DELTA));
    }

    @Test
    public void shouldReturnBlueComponent() {
        // Given
        Color color = new Color(1.0, 1.0, 1.0);

        // Expect
        assertThat(color.b()).isCloseTo(1.0, offset(DELTA));
    }

    @Test
    public void shouldComputeAdditionOfTwoColors() {
        // Given
        Color c1 = new Color(0.5, 0.5, 0.5);

        // When
        Color c2 = c1.add(c1);

        // Then
        assertThat(c2.r()).isCloseTo(1., offset(DELTA));
        assertThat(c2.g()).isCloseTo(1., offset(DELTA));
        assertThat(c2.b()).isCloseTo(1., offset(DELTA));
    }

    @Test
    public void shouldAddTwoColorsWithComponentOverflow() {
        // Given
        Color c1 = new Color(0.5, 0.5, 0.5);
        Color c2 = new Color(0.6, 0.6, 0.6);

        // When
        Color c3 = c1.add(c2);

        // Then
        assertThat(c3.r()).isCloseTo(1., offset(DELTA));
        assertThat(c3.g()).isCloseTo(1., offset(DELTA));
        assertThat(c3.b()).isCloseTo(1., offset(DELTA));
    }

    @Test
    public void shouldEqualAnOtherColorWithSameComponents() {
        // Given
        Color color = new Color(0.2, 0.3, 0.4);
        Color otherColor = new Color(0.2, 0.3, 0.4);

        // Expect
        assertThat(color).isEqualTo(otherColor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseExceptionWhenColorComponentOverflow() {
        new Color(2.0, 0.0, -5.0);
    }
}
