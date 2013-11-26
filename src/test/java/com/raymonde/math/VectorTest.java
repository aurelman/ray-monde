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

package com.raymonde.math;

import com.raymonde.core.Vector;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aurelman
 */
public class VectorTest {

    /**
     * 
     */
    private static final double DELTA = 0.00000001;

    /**
     *
     */
    public VectorTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of length method, of class Vector.
     */
    @Test
    public void testLength() {

        double expResult;
        double result;
        Vector vector1;

        vector1 = new Vector(2, 4, 6);
        expResult = 7.48331477;
        result = vector1.length();

        assertEquals(expResult, result, VectorTest.DELTA);

        vector1 = new Vector(1.0, 1.0, 1.0);
        expResult = 1.73205080;
        result = vector1.length();
        
        assertEquals(expResult, result, VectorTest.DELTA);

        vector1 = Vector.zero();
        expResult = 0.0;
        result = vector1.length();

        assertEquals(expResult, result, VectorTest.DELTA);
    }

    /**
     * Test of squaredLength method, of class Vector.
     */
    @Test
    public void testSquaredLength() {

        double expResult;
        double result;
        Vector vector1;

        vector1 = new Vector(2, 4, 6);
        expResult = 56.0;
        result = vector1.squaredLength();

        assertEquals(expResult, result, VectorTest.DELTA);

        vector1 = new Vector(-1.0, 1.0, 1.0);
        expResult = 3.0;
        result = vector1.squaredLength();

        assertEquals(expResult, result, VectorTest.DELTA);

        vector1 = Vector.zero();
        expResult = 0.0;
        result = vector1.squaredLength();

        assertEquals(expResult, result, VectorTest.DELTA);
    }

    /**
     * Test of distanceTo method, of class Vector.
     */
    @Test
    public void testDistanceTo() {

        Vector vector1;
        Vector vector2;

        vector1 = new Vector(2., 3., 4.);
        vector2 = new Vector(2., 3., 4.);

        double result;
        double expectedResult = 0.0;

        result = vector1.distanceTo(vector2);

        assertEquals(expectedResult, result, VectorTest.DELTA);
    }

    /**
     * Test of cross method, of class Vector.
     */
    @Test
    public void testCross() {

        Vector vector1;
        Vector vector2;

        vector1 = new Vector(1., 0., 0.);
        vector2 = new Vector(0., 1., 0.);

        Vector result;
        Vector expectedResult = new Vector(0., 0., 1.);

        result = vector1.cross(vector2);

        assertEquals(expectedResult, result);
    }

    /**
     * Test of distance method, of class Vector.
     */
    @Test
    public void testDistance() {

        Vector vector1;
        Vector vector2;

        vector1 = new Vector(2., 3., 4.);
        vector2 = new Vector(2., 3., 4.);

        double result;
        double expectedResult = 0.0;

        result = Vector.distance(vector1, vector2);

        assertEquals(expectedResult, result, VectorTest.DELTA);
    }

    /**
     * Test of add method, of class Vector.
     */
    @Test
    public void testAdd() {

        Vector vector1;
        Vector vector2;
        Vector expResult;

        vector1 = new Vector(1.0, 2.0, -3.0);
        vector2 = new Vector(2.0, -1.0, -9.0);
        expResult = new Vector(3.0, 1.0, -12.0);
        
        Vector result = vector1.add(vector2);

        assertEquals(expResult, result);
    }

    /**
     * Test of substract method, of class Vector.
     */
    @Test
    public void testSubstract() {

        Vector vector1;
        Vector vector2;
        Vector expResult;

        vector1 = new Vector(1.0, 2.0, -3.0);
        vector2 = new Vector(2.0, -1.0, -9.0);
        expResult = new Vector(-1.0, 3.0, 6.0);

        Vector result = vector1.substract(vector2);

        assertEquals(expResult, result);
    }

    /**
     *
     */
    @Test
    public void testMultiply() {

        Vector vector1;
        double scalar;
        Vector expResult;

        vector1 = new Vector(1.0, 2.0, -3.0);
        scalar = -6.0;
        expResult = new Vector(-6.0, -12.0, 18.0);

        Vector result = vector1.multiply(scalar);

        assertEquals(expResult, result);

        scalar = 6.0;
        expResult = new Vector(6.0, 12.0, -18.0);
        result = vector1.multiply(scalar);

        assertEquals(expResult, result);
    }

    /**
     * Test of normalized method, of class Vector.
     */
    @Test
    public void testNormalized() {
        
        Vector instance = new Vector(6.0, -5.0, 4.0);
        Vector result = instance.normalized();

        double expLength = 1.0;
        double length = result.length();
        
        assertEquals(expLength, length, VectorTest.DELTA);
    }

    /**
     * Test of normalizeInPlace method, of class Vector.
     */
    @Test
    public void testReflected() {

        Vector v1 = new Vector(6.0, -5.0, 0.0);
        Vector normal = new Vector(0.0, 1.0, 0.0);

        Vector expected = new Vector(6.0, 5.0, 0.0);

        Vector actual = v1.reflected(normal);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of dot method, of class Vector.
     */
    @Test
    public void testDot() {
        
        Vector vector1 = new Vector(6.0, -5.0, -4.0);
        Vector vector2 = new Vector(3.0, 2.0, -1.0);

        double expResult = 12.0;
        double result = vector1.dot(vector2);
        
        assertEquals(expResult, result, VectorTest.DELTA);
    }


    /**
     *
     */
    @Test
    public void testEquals() {
        System.out.println("equals");

        Vector vector1 = new Vector(6.0, -5.0, -4.0);
        Vector vector2 = new Vector(6.0, -5.0, -4.0);

        assertTrue(vector1.equals(vector2));

        vector2 = null;
        assertFalse(vector1.equals(vector2));
    }

    /**
     * 
     */
    @Test
    public void testOpposite() {

        Vector vector;
        Vector result;
        Vector expected;

        vector = new Vector(6.0, 0.0, -12.0);
        result = vector.opposite();
        expected = new Vector(-6.0, -0.0, 12.0);

        assertEquals(expected, result);
    }
    
    /**
     * 
     */
    @Test
    public void testToString() {

        Vector vector = new Vector(1.0, 1.0, 1.0);
        String expected =  "[Vector : x = 1.0, y = 1.0, z = 1.0]";
        assertEquals(expected, vector.toString());
    }
}
