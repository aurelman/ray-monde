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

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * <p> {@code QuadraticEquation} objects are responsible of handling
 * 2nd degree polynomial (following the pattern : {@code ax² + bx + c}).
 * It also allows find the roots of such an equation.</p>
 *
 * @author aurelman
 */
@ThreadSafe
@Immutable
public final class QuadraticEquation {

    /**
     * Used to represent the solution(s) of a quadratic equation.
     * @author aurelman
     */
    @ThreadSafe
    @Immutable
    static final class Result {

        /**
         *
         */
        private final double firstRoot;

        /**
         *
         */
        private final double secondRoot;

        /**
         *
         */
        private final int rootNumber;

        /**
         * Constructs a <code>result</code> object.
         *
         * @param rootNumber The number of root
         * @param firstRoot contains the first root, if <code>rootNumber == 2</code>, or the only root if <code>rootNumber == 1</code>
         * @param secondRoot contains the second root (only available when <code>rootNumber == 2</code>)
         */
        private Result(int rootNumber, double firstRoot, double secondRoot) {
            this.rootNumber = rootNumber;
            this.firstRoot = firstRoot;
            this.secondRoot = secondRoot;
        }

        /**
         * @return the firstRoot
         */
        public double firstRoot() {
            return firstRoot;
        }

        /**
         * @return the secondRoot
         */
        public double secondRoot() {
            return secondRoot;
        }

        /**
         * @return the rootNumber
         */
        public int rootNumber() {
            return rootNumber;
        }
    }

    /**
     * First component.
     */
    private final double a;

    /**
     * Second component.
     */
    private final double b;

    /**
     * Third component.
     */
    private final double c;

    /**
     * Constructs a <code>QuadraticEquation</code> with the specified values for
     * a, b and c.
     *
     * @param a The firstRoot component.
     * @param b The secondRoot component.
     * @param c The third component.
     */
    public QuadraticEquation(final double a, final double b, final double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Solves the {@link QuadraticEquation} corresponding to the specified
     * components.
     *
     * @param a The firstRoot component.
     * @param b The secondRoot component.
     * @param c The third component.
     *
     * @return The solution of the equation.
     */
    public static Result solve(
            final double a,
            final double b,
            final double c) {
        QuadraticEquation eq = new QuadraticEquation(a, b, c);
        return eq.solve();
    }

    /**
     * Solves the current <code>QuadraticEquation</code> and
     * returns the resulting object.
     *
     * @return The solution of the equation.
     */
    public Result solve() {

        double firstRoot = 0;
        double secondRoot = 0;
        int count;

        if (a == 0.0) {
            count = 1;
            firstRoot = (-c/b);
        } else {

            double delta = b*b - 4*a*c;

            if (delta < 0.0) {
                count = 0;
            } else if (delta == 0.0) {
                count = 1;
                firstRoot = ((-b)/(2*a));
            } else {
                double rootSquaredDelta = Math.sqrt(delta);
                double denominator = 2*a;
                count = 2;
                double root1 = (-b - rootSquaredDelta)/denominator;
                double root2 = (-b + rootSquaredDelta)/denominator;

                if (root1 < root2) {
                    firstRoot = root1;
                    secondRoot = root2;
                } else {
                    firstRoot = root2;
                    secondRoot = root1;
                }
            }
        }

        return new Result(count, firstRoot, secondRoot);
    }
}