/*
 * Copyright (C) 2009 Manoury Aurélien
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

import com.raymonde.core.QuadraticEquation;
import com.raymonde.core.QuadraticEquationResult;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aurelman
 */
public class QuadraticEquationTest {

    /**
     * 
     */
    public final static double DELTA = 0.000000001;
    
    /**
     *
     */
    public QuadraticEquationTest() {
    }

    /**
     *
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     *
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
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
     * Test of solve method, of class QuadraticEquation.
     */
    @Test
    public void testSolve_Static() {
        System.out.println("solve");
        
        QuadraticEquationResult result;

        result = QuadraticEquation.solve(2.0, 5.0, 2.0);
        assertEquals(2, result.getCount());
        assertEquals(-2.0, result.getFirst(), QuadraticEquationTest.DELTA);
        assertEquals(-0.5, result.getSecond(), QuadraticEquationTest.DELTA);

        result = QuadraticEquation.solve(0.0, 5.0, 2.0);
        assertEquals(1, result.getCount());
        assertEquals(-2.0/5.0, result.getFirst(), QuadraticEquationTest.DELTA);

        result = QuadraticEquation.solve(2.0, -1.0, 2.0);
        assertEquals(0, result.getCount());

        // delta == 0.0       
        result = QuadraticEquation.solve(1.0, -2.0, 1.0);
        assertEquals(1, result.getCount());
        assertEquals(1.0, result.getFirst(), QuadraticEquationTest.DELTA);
    }

    /**
     * Test of solve method, of class QuadraticEquation.
     */
    @Test
    public void testSolve() {
        System.out.println("solve");
        QuadraticEquation instance;
        QuadraticEquationResult result;
        
        instance = new QuadraticEquation(2.0, 5.0, 2.0);
        result = instance.solve();
        assertEquals(2, result.getCount());
        assertEquals(-2.0, result.getFirst(), QuadraticEquationTest.DELTA);
        assertEquals(-0.5, result.getSecond(), QuadraticEquationTest.DELTA);

        instance = new QuadraticEquation(0.0, 5.0, 2.0);
        result = instance.solve();
        assertEquals(1, result.getCount());
        assertEquals(-2.0/5.0, result.getFirst(), QuadraticEquationTest.DELTA);

        instance = new QuadraticEquation(2.0, -1.0, 2.0);
        result = instance.solve();
        assertEquals(0, result.getCount());

        // delta == 0.0
        instance = new QuadraticEquation(1.0, -2.0, 1.0);
        result = instance.solve();
        assertEquals(1, result.getCount());
        assertEquals(1.0, result.getFirst(), QuadraticEquationTest.DELTA);
    }

    /**
     * Test of getA method, of class QuadraticEquation.
     */
    @Test
    public void testGetA() {
        System.out.println("getA");
        double a = 1.0;
        double b = -3.0;
        double c = -12.0;
        QuadraticEquation instance = new QuadraticEquation(a, b, c);
        double result = instance.getA();
        assertEquals(a, result, QuadraticEquationTest.DELTA);
    }

    /**
     * Test of setA method, of class QuadraticEquation.
     */
    @Test
    public void testSetA() {
        System.out.println("setA");
        double a = 6.0;
        QuadraticEquation instance = new QuadraticEquation();
        instance.setA(6.0);
        assertEquals(instance.getA(), a, QuadraticEquationTest.DELTA);
    }

    /**
     * Test of getB method, of class QuadraticEquation.
     */
    @Test
    public void testGetB() {
        System.out.println("getB");
        double a = 1.0;
        double b = -3.0;
        double c = -12.0;
        QuadraticEquation instance = new QuadraticEquation(a, b, c);
        double result = instance.getB();
        assertEquals(b, result, QuadraticEquationTest.DELTA);
    }

    /**
     * Test of setB method, of class QuadraticEquation.
     */
    @Test
    public void testSetB() {
        System.out.println("setB");
        double b = 6.0;
        QuadraticEquation instance = new QuadraticEquation();
        instance.setB(6.0);
        assertEquals(instance.getB(), b, QuadraticEquationTest.DELTA);
    }

    /**
     * Test of getC method, of class QuadraticEquation.
     */
    @Test
    public void testGetC() {
        System.out.println("getC");
        double a = 1.0;
        double b = -3.0;
        double c = -12.0;
        QuadraticEquation instance = new QuadraticEquation(a, b, c);
        double result = instance.getC();
        assertEquals(c, result, QuadraticEquationTest.DELTA);
    }

    /**
     * Test of setC method, of class QuadraticEquation.
     */
    @Test
    public void testSetC() {
        System.out.println("setC");
        double c = 6.0;
        QuadraticEquation instance = new QuadraticEquation();
        instance.setC(6.0);
        assertEquals(instance.getC(), c, QuadraticEquationTest.DELTA);
    }
}