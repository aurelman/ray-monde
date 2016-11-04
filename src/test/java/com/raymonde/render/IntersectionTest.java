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
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntersectionTest {

    @Test
    public void shouldComputeReflectedRay() {
        // Given
        Vector spherePosition = new Vector(0, 0, 0);
        Ray incomingRay = new Ray(new Vector(4, 0, 0), new Vector(-1, 0, 0));
        Intersection instance = new Intersection(new Sphere(spherePosition, 1), incomingRay, 3);
        Ray expResult = new Ray(new Vector(1, 0, 0), new Vector(1, 0, 0));
        // When
        Ray result = instance.getReflectedRay();

        // Then
        assertEquals(expResult, result);
    }

//    /**
//     * Test of getIntersectionPosition method, of class Intersection.
//     */
//    @Test
//    public void testGetIntersectionPosition() {
//        System.out.println("getIntersectionPosition");
//        Intersection instance = null;
//        Vector expResult = null;
//        Vector result = instance.getIntersectionPosition();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
