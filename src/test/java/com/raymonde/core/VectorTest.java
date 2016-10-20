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

/**
 *
 * @author aurelman
 */
public class VectorTest {

    private static final double DELTA = 0.001;

    /**
     * Test of length method, of class Vector.
     */
    @Test
    public void shouldComputeLengthOfVectors() {
        // Given
        Vector vector1 = new Vector(2, 4, 6);
        Vector vector2 = new Vector(1.0, 1.0, 1.0);
        Vector vector3 = new Vector();

        // Expect
        assertThat(vector1.length()).isCloseTo(7.483, offset(DELTA));
        assertThat(vector2.length()).isCloseTo(1.732, offset(DELTA));
        assertThat(vector3.length()).isCloseTo(0.0, offset(DELTA));
    }

    @Test
    public void shouldComputeSquaredLengthOfVector() {
        // Given
        Vector vector1 = new Vector(2, 4, 6);
        Vector vector2 = new Vector(-1.0, 1.0, 1.0);
        Vector vector3 = new Vector();

        // Expect
        assertThat(vector1.squaredLength()).isCloseTo(56.0, offset(DELTA));
        assertThat(vector2.squaredLength()).isCloseTo(3.0, offset(DELTA));
        assertThat(vector3.squaredLength()).isCloseTo(0.0, offset(DELTA));
    }

    @Test
    public void shouldComputeTheDistanceToAVector() {
        // Given
        Vector vector1 = new Vector(2., 3., 4.);
        Vector vector2 = new Vector(2., 3., 4.);

        // When
        double result = vector1.distanceTo(vector2);

        // Then
        assertThat(result).isCloseTo(0.0, offset(DELTA));
    }

    @Test
    public void shouldComputeCrossProduct() {
        // Given
        Vector vector1 = new Vector(1., 0., 0.);
        Vector vector2 = new Vector(0., 1., 0.);

        // When
        Vector result = vector1.cross(vector2);

        // Then
        assertThat(result.x()).isCloseTo(0., offset(DELTA));
        assertThat(result.y()).isCloseTo(0., offset(DELTA));
        assertThat(result.z()).isCloseTo(1., offset(DELTA));
    }

    @Test
    public void shouldComputeDistanceBetweenTwoVectors() {
        // Given
        Vector vector1 = new Vector(2., 3., 4.);
        Vector vector2 = new Vector(2., 3., 4.);

        // Expect
        assertThat(Vector.distance(vector1, vector2)).isCloseTo(0.0, offset(DELTA));
    }

    @Test
    public void shouldComputeAdditionOfTwoVector() {
        // Given
        Vector vector1 = new Vector(1.0, 2.0, -3.0);
        Vector vector2 = new Vector(2.0, -1.0, -9.0);

        // When
        Vector result = vector1.add(vector2);

        // Then
        assertThat(result.x()).isCloseTo(3.0, offset(DELTA));
        assertThat(result.y()).isCloseTo(1.0, offset(DELTA));
        assertThat(result.z()).isCloseTo(-12.0, offset(DELTA));
    }

    @Test
    public void shouldComputeSubstractionOfTwoVectors() {
        // Given
        Vector vector1 = new Vector(1.0, 2.0, -3.0);
        Vector vector2 = new Vector(2.0, -1.0, -9.0);

        // When
        Vector result = vector1.substract(vector2);

        // Then
        assertThat(result.x()).isCloseTo(-1.0, offset(DELTA));
        assertThat(result.y()).isCloseTo(3.0, offset(DELTA));
        assertThat(result.z()).isCloseTo(6.0, offset(DELTA));
    }

    @Test
    public void shouldConstructAVectorJoiningTwoPoint() {
        // Given
        Vector vector1 = new Vector(2.0, -1.0, -9.0);
        Vector vector2 = new Vector(1.0, 2.0, -3.0);

        // When
        Vector result = Vector.joining(vector1, vector2);

        // Then
        assertThat(result.x()).isCloseTo(-1.0, offset(DELTA));
        assertThat(result.y()).isCloseTo(3.0, offset(DELTA));
        assertThat(result.z()).isCloseTo(6.0, offset(DELTA));
    }

    @Test
    public void shouldComputeMultiplicationOfVectorByAScalar() {
        // Given
        Vector vector1 = new Vector(1.0, 2.0, -3.0);

        // When
        Vector result = vector1.multiply(-6.0);

        // Then
        assertThat(result.x()).isCloseTo(-6.0, offset(DELTA));
        assertThat(result.y()).isCloseTo(-12.0, offset(DELTA));
        assertThat(result.z()).isCloseTo(18.0, offset(DELTA));
    }

    @Test
    public void shouldNormalizeAVector() {
        // Given
        Vector instance = new Vector(6.0, -5.0, 4.0);

        // When
        Vector result = instance.normalized();

        // Then
        assertThat(result.length()).isCloseTo(1.0, offset(DELTA));
        assertThat(result.x()).isCloseTo(0.684, offset(DELTA));
        assertThat(result.y()).isCloseTo(-0.569, offset(DELTA));
        assertThat(result.z()).isCloseTo(0.455, offset(DELTA));
    }

    /**
     * This unit test has been written to ensure that no regression has been
     * introduced when has been developed the normalized optimization.
     */
    @Test
    public void shouldNormalizeAVectorWithTwoSubsequentCalls() {
        // Given
        Vector instance = new Vector(6.0, -5.0, 4.0);

        // When
        Vector result = instance.normalized().normalized();

        // Then
        assertThat(result.length()).isCloseTo(1.0, offset(DELTA));
        assertThat(result.x()).isCloseTo(0.684, offset(DELTA));
        assertThat(result.y()).isCloseTo(-0.569, offset(DELTA));
        assertThat(result.z()).isCloseTo(0.455, offset(DELTA));
    }

    @Test
    public void shouldComputeReflectedVector() {
        // Given
        Vector vector = new Vector(6.0, -5.0, 0.0);
        Vector normal = new Vector(0.0, 1.0, 0.0);

        // When
        Vector reflected = vector.reflected(normal);

        // Then
        assertThat(reflected.x()).isCloseTo(6.0, offset(DELTA));
        assertThat(reflected.y()).isCloseTo(5.0, offset(DELTA));
        assertThat(reflected.z()).isCloseTo(0.0, offset(DELTA));
    }

    @Test
    public void shouldComputeDotProduct() {
        // Given
        Vector vector1 = new Vector(6.0, -5.0, -4.0);
        Vector vector2 = new Vector(3.0, 2.0, -1.0);

        // When
        double result = vector1.dot(vector2);

        // Then
        assertThat(result).isCloseTo(12.0, offset(DELTA));
    }

    @Test
    public void shouldCorrectlyCompareTwoVectors() {
        // Given
        Vector vector1 = new Vector(6.0, -5.0, -4.0);
        Vector vector2 = new Vector(6.0, -5.0, -4.0);

        // Expect
        assertThat(vector1.equals(vector2)).isTrue();
    }

    @Test
    public void nullVectorShouldNotEqualAnyOtherVector() {
        // Given
        Vector vector = new Vector(6.0, -5.0, -4.0);

        // Expect
        assertThat(vector.equals(null)).isFalse();
    }

    @Test
    public void shouldComputeTheOppositeVector() {
        // Given
        Vector vector = new Vector(6.0, 0.0, -12.0);

        // When
        Vector result = vector.opposite();

        // Then
        assertThat(result.x()).isEqualTo(-6.0);
        assertThat(result.y()).isEqualTo(-0.0);
        assertThat(result.z()).isEqualTo(12.0);
    }

    @Test
    public void toStringShouldBeCorrect() {
        // Given
        Vector vector = new Vector(1.0, 1.0, 1.0);

        // Expect
        assertThat(vector.toString()).isEqualTo("Vector{x=1.0, y=1.0, z=1.0, normalized=false}");
    }
}
