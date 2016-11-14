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
import com.raymonde.render.material.Material;
import lombok.Builder;

/**
 *
 */
public class Triangle extends AbstractPrimitive {

    private final Vector [] _points = new Vector[3];

    private static final int FIRST = 0;

    private static final int SECOND = 1;

    private static final int THIRD = 2;

    @Builder
    public Triangle(final Vector first, final Vector second, final Vector third, final Material material) {
        super(material);
        _points[FIRST] = first;
        _points[SECOND] = second;
        _points[THIRD] = third;
    }

    @Override
    public Vector normalAt(Vector point) {
        return null;
    }

    @Override
    public double intersect(Ray ray) {
        return 0;
    }
}
