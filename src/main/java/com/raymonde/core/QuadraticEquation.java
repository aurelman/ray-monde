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
 * <p><code>QuadraticEquation</code> object are responsible of handling
 * 2nd degree polynomial (following the pattern : <code>ax² + bx + c</code>).
 * It also allows find the roots of such an equation.</p>
 * 
 * @author aurelman
 */
public class QuadraticEquation {

    /**
     * First component.
     */
    private double a;

    /**
     * Second component.
     */
    private double b;

    /**
     * Third component.
     */
    private double c;

    /**
     * Default constructor.
     * a, b and c are initialized with 0.0.
     */
    public QuadraticEquation() {
        this(0.0, 0.0, 0.0);
    }

    /**
     * Constructs a <code>QuadraticEquation</code> with the specified values for
     * a, b and c.
     * 
     * @param a The first component.
     * @param b The second component.
     * @param c The third component.
     */
    public QuadraticEquation(final double a, final double b, final double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Solves the <code>QuadraticEquation</code> corresponding on the specified
     * components.
     * 
     * @param a The first componenet.
     * @param b The second component.
     * @param c The third component.
     * 
     * @return The solution of the equation.
     */
    public static QuadraticEquationResult solve(
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
    public QuadraticEquationResult solve() {
        double pA = getA();
        double pB = getB();
        double pC = getC();
        QuadraticEquationResult res = new QuadraticEquationResult();

        if (pA == 0.0) {
            res.setCount(1);
            res.setFirst(-pC/pB);
        } else {

            double delta = pB*pB - 4*pA*pC;

            if (delta < 0.0) {
                res.setCount(0);
            } else if (delta == 0.0) {
                res.setCount(1);
                res.setFirst((-pB)/(2*pA));
            } else {
                double rootSquaredDelta = Math.sqrt(delta);
                double denominator = 2*pA;
                res.setCount(2);
                double root1 = (-pB - rootSquaredDelta)/denominator;
                double root2 = (-pB + rootSquaredDelta)/denominator;

                if (root1 < root2) {
                    res.setFirst(root1);
                    res.setSecond(root2);
                } else {
                    res.setFirst(root2);
                    res.setSecond(root1);
                }
            }
        }
        
        return res;
    }
    
    /**
     * Returns the first component of the polynomial.
     * 
     * @return The first component.
     */
    public double getA() {
        return a;
    }

    /**
     * @param a The value to set to the first component.
     */
    public void setA(final double a) {
        this.a = a;
    }

    /**
     * Returns the second component of the polynomial.
     *
     * @return The second component
     */
    public double getB() {
        return this.b;
    }

    /**
     * @param b The value to set for the second component.
     */
    public void setB(final double b) {
        this.b = b;
    }

    /**
     * Returns the third component of the polynomial.
     * 
     * @return The third component
     */
    public double getC() {
        return this.c;
    }

    /**
     * @param c The value to set for the third component.
     */
    public void setC(final double c) {
        this.c = c;
    }
}
