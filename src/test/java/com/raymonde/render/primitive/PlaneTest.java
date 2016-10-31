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
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author aurelman
 */
public class PlaneTest {

    /**
     * An instance of plane.
     */
    private Plane plane1;

    /**
     * Test of intersect method, of class Plane.
     */
    @Test
    public void testIntersect() {
        System.out.println("intersect");
        Ray ray = new Ray(new Vector(0., 0., 1.), new Vector(0., 0., 1.));
        Plane instance = getPlane1();
        double expResult = 99.;
        double result = instance.intersect(ray);
        assertEquals(expResult, result, 0.0000000001);
    }

    /**
     * @return the sphere1
     */
    public Plane getPlane1() {
        if (this.plane1 == null) {
            this.plane1 = new Plane(new Vector(0., 0., -1.), 100.0);
        }

        return this.plane1;
    }
}