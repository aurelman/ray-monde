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

package com.raymonde.render.primitive;

import com.raymonde.core.Vector;
import com.raymonde.render.Ray;

/**
 * A <code>Plane</code> object is defined by a normal vector and a distance from
 * the origine.
 * 
 * @author aurelman
 */
public class VerticalPlane extends Primitive {

    /**
     * 
     */
    public VerticalPlane() {
        super("plane");
    }

    @Override
    public Vector normalAt(final Vector point) {
        return new Vector(0.0, 0.0, 1.0);
    }

    @Override
    public double intersect(final Ray ray) {
        return 900.0;
    }
}
