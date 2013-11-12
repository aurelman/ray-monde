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

package com.raymonde.render;

/**
 * <code>Element</code> is the base class for all elements in a scene.
 * <code>Primitive</code>, <code>Light</code> and <code>Camera</code> objects
 * are elements.
 * Each element has a name, a position, and an optional material.
 *
 * @author aurelman
 */
public abstract class Element {

    /**
     * The name of the element.
     */
    private String name;

    /**
     * Constructs an <code>Element</code> object with the specified name.
     *
     * @param name The name to give to the element.
     */
    public Element(final String name) {
        this.name = name;
    }

    /**
     * Returns the name of the element.
     * 
     * @return The name of the element.
     */
    public String getName() {
        return name;
    }


    /**
     * Returns a string representation of the element.
     * 
     * @return a string representation of the element.
     */
    @Override
    public String toString() {
        return getName();
    }
}
