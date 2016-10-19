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

/**
 *
 * @author aurelman
 */
public class QuadraticEquationResult {

    /**
     *
     */
    private double delta;

    /**
     *
     */
    private double first;

    /**
     *
     */
    private double second;

    /**
     *
     */
    private int count;

    /**
     * @return the delta
     */
    public double getDelta() {
        return delta;
    }

    /**
     * @param delta the delta to set
     */
    public void setDelta(final double delta) {
        this.delta = delta;
    }

    /**
     * @return the first
     */
    public double getFirst() {
        return first;
    }

    /**
     * @param first the first to set
     */
    public void setFirst(final double first) {
        this.first = first;
    }

    /**
     * @return the second
     */
    public double getSecond() {
        return second;
    }

    /**
     * @param second the second to set
     */
    public void setSecond(final double second) {
        this.second = second;
    }

    /**
     * @return the rootNumber
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the rootNumber to set
     */
    public void setCount(final int count) {
        this.count = count;
    }
}
