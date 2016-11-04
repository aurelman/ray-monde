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
 * <p> {@code QuadraticEquation} represents 2nd degree polynomial equations
 * (i.e. following the pattern : {@code ax² + bx + c}) and provides way to solve them
 * and find their solutions if any.
 */
@ThreadSafe
@Immutable
public final class QuadraticEquation {

    /**
     * Used to represent the solution(s) of a quadratic equation.
     */
    @ThreadSafe
    @Immutable
    static final class Result {

        /**
         * The first solution
         */
        private final double firstRoot;

        /**
         * The second solution
         */
        private final double secondRoot;

        /**
         * The number of solutions that are differents (can be 0, 1 or 2)
         */
        private final int rootNumber;

        /**
         * Constructs a {@code Result} with the specified elements.
         *
         * @param rootNumber The number of root
         * @param firstRoot contains the first root, if {@code rootNumber == 2}, or the only root if {@code rootNumber == 1}
         * @param secondRoot contains the second root, if {@code rootNumber == 2}, or the only root if {@code rootNumber == 1}
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
     * Constructs a {@code QuadraticEquation} with the specified values for
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
     * Solves the {@code QuadraticEquation} corresponding to the specified
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
     * Solves the current {@code QuadraticEquation} and
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
            secondRoot = firstRoot;
        } else {
            double delta = b*b - 4*a*c;

            if (delta < 0.0) {
                count = 0;
            } else if (delta == 0.0) {
                count = 1;
                firstRoot = ((-b)/(2*a));
                secondRoot = firstRoot;
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