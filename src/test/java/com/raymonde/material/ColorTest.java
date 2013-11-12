
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

package com.raymonde.material;

import com.raymonde.core.Color;
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
public class ColorTest {

    /**
     *
     */
    public ColorTest() {
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
     * Test of getRGB method, of class Color.
     */
    @Test
    public void testGetRGB() {
        System.out.println("getRGB");
        Color instance = new Color(1.0, 1.0, 1.0);
        int expResult = 0xFFFFFFFF;
        int result = instance.getRGB();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRed method, of class Color.
     */
    @Test
    public void testGetRed() {
        System.out.println("getRed");
        Color instance = new Color(1.0, 1.0, 1.0);
        double expResult = 1.;
        double result = instance.getRed();
        assertEquals(expResult, result, 0.000000000);

        instance = new Color(0., 1., 1.);
        expResult = 0.;
        result = instance.getRed();
        assertEquals(expResult, result, 0.000000000);
    }

    /**
     * Test of getGreen method, of class Color.
     */
    @Test
    public void testGetGreen() {
        System.out.println("getGreen");
        Color instance = new Color(1.0, 1.0, 1.0);
        double expResult = 1.;
        double result = instance.getGreen();
        assertEquals(expResult, result, 0.000000000);


        instance = new Color(1.0, 0., 1.);
        expResult = 0.;
        result = instance.getGreen();
        assertEquals(expResult, result, 0.000000000);
    }

    /**
     * Test of getBlue method, of class Color.
     */
    @Test
    public void testGetBlue() {
        System.out.println("getBlue");
        Color instance = new Color(1.0, 1.0, 1.0);
        double expResult = 1.;
        double result = instance.getBlue();
        assertEquals(expResult, result, 0.000000000);

        instance = new Color(1., 1., 0.);
        expResult = 0.;
        result = instance.getBlue();
        assertEquals(expResult, result, 0.000000000);
    }

    /**
     * Test pf add method, of class Color.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Color c1 = new Color(0.5, 0.5, 0.5);
        double expectedBlue = 1.;
        double expectedRed = 1.;
        double expectedGreen = 1.;

        Color c2 = c1.add(c1);
        assertEquals(expectedRed, c2.getRed(), 0.);
        assertEquals(expectedGreen, c2.getGreen(), 0.);
        assertEquals(expectedBlue, c2.getBlue(), 0.);

        Color c3 = new Color(0.6, 0.6, 0.6);
        c2 = c1.add(c3);
        expectedBlue = 1.;
        expectedGreen = 1.;
        expectedBlue = 1.;

        assertEquals(expectedRed, c2.getRed(), 0.);
        assertEquals(expectedGreen, c2.getGreen(), 0.);
        assertEquals(expectedBlue, c2.getBlue(), 0.);
    }
}
