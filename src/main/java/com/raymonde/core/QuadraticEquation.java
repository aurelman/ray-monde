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
         * The number of solutions that are different (can be 0, 1 or 2)
         */
        private final int rootNumber;

        /**
         * Constructs a {@code Result} with the specified elements.
         *
         * @param firstRoot contains the first root
         * @param secondRoot contains the second root
         */
        private Result(final double firstRoot, final double secondRoot) {
            this.rootNumber = 2;
            this.firstRoot = firstRoot;
            this.secondRoot = secondRoot;
        }

        /**
         * Constructs a {@code Result} with the specified elements.

         * @param onlyRoot the only root
         */
        private Result(final double onlyRoot) {
            this.rootNumber = 1;
            this.firstRoot = onlyRoot;
            this.secondRoot = onlyRoot;
        }

        /**
         * Constructs a {@code Result} with no real root.
         */
        private Result() {
            this.rootNumber = 0;
            this.firstRoot = Double.NaN;
            this.secondRoot = Double.NaN;
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
    public static Result solve(final double a, final double b, final double c) {
        return new QuadraticEquation(a, b, c).solve();
    }

    /**
     * Solves the current {@code QuadraticEquation} and
     * returns the resulting object.
     *
     * @return The solution of the equation.
     */
    public Result solve() {

        if (a == 0.0) {
            return new Result(-c/b);
        }

        double discriminant = b*b - 4*a*c;

        if (discriminant < 0.0) {
            return new Result();
        }

        if (discriminant == 0.0) {
            return new Result((-b)/(2*a));
        }

        double rootSquaredDelta = Math.sqrt(discriminant);

        /*
         *  The numerically computed solution would be  :
         *   double root1 = (-b - rootSquaredDelta)/denominator;
         *   double root2 = (-b + rootSquaredDelta)/denominator;
         *
         *  But to void cancellation issue in case b*b is a lot greater that 4*a*c
         *  We compute roots in an other way.
         *
         *  For more details about cancellation see : https://en.wikipedia.org/wiki/Loss_of_significance
         */

        double root1 = (-b - Math.signum(b) * rootSquaredDelta) / (2 * a);
        double root2 = c / (a * root1);

        if (root1 < root2) {
            return new Result(root1, root2);
        }

        return new Result(root2, root1);
    }
}