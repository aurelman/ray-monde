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
package com.raymonde.core;

import java.util.Arrays;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * {@code Vector} objects are triplet scalars.
 * It can be used to represent either direction or position in space.
 * {@code Vector} is immutable.
 * 
 * Basic and usual mathematical operations are available on each instance :
 * <li>addition,</li>
 * <li>multiplication,</li>
 * <li>length,</li>
 * <li>dot-product,</li> 
 * <li>cross product,</li>
 * <li>normalization...</li>
 *
 * @author aurelman
 */
@ThreadSafe
@Immutable
public final class Vector {

    /**
     * The cardianlity of <code>Vector</code> object.
     */
    private static final int VECTOR_DIMENSION = 3;
    
    /**
     * The index in vec of the x coordinate.
     */
    private static final int X = 0;
    
    /**
     * The index in vec of the y coordinate.
     */
    private static final int Y = 1;
    
    /**
     * The index in vec of the Z coordinate.
     */
    private static final int Z = 2;
    
    private static final Vector ZERO = new Vector(0., 0., 0.);
    
    private static final Vector UNIT = new Vector(1., 1., 1.);

    /**
     * Coordinates are stored in a 3-length array.
     */
    private final double vec[] = new double[Vector.VECTOR_DIMENSION];

    /**
     * Constructs a <code>Vector</code> object where each of the coordinate
     * is initialized with 0.0 by default.
     */
    public Vector() {
        this(0.0, 0.0, 0.0);
    }

    /**
     * Constructs a <code>Vector</code> object initialized with the
     * specified coordinates.
     * <ul>
     * <li><code>vector[0]</code> is the x coordinate.</li>
     * <li><code>vector[1]</code> is the y coordinate.</li>
     * <li><code>vector[2]</code> is the z coordinate.</li>
     * </ul>
     *
     * @param vector A 3-length array containing
     * the three coordinates to initialize the vector with.
     */
    public Vector(final double [] vector) {
        System.arraycopy(vector, 0, this.vec, 0, Vector.VECTOR_DIMENSION);
    }

    /**
     * Constructs a vector initialized with the specified coordinates.
     *
     * @param x The value to set for the x coordinate.
     * @param y The value to set for the y coordinate.
     * @param z The value to set for the z coordinate.
     */
    public Vector(final double x, final double y, final double z) {
        vec[X] = x;
        vec[Y] = y;
        vec[Z] = z;
    }
    
    /**
     * Returns a zero initialized vector.
     * 
     * @return A zero initialized vector.
     */
    public static Vector zero() {
        return ZERO;
    }

    /**
     * Returns the unit vector.
     * 
     * @return The unit vector.
     */
    public static Vector unit() {
        return UNIT;
    }
    
    /**
     * Factory method to create a new {@ode Vector} object 
     * joining the two specified points.
     * 
     * @param src The starting point.
     * @param dst The destination point.
     * 
     * @return The newly created {@code Vector}.
     */
    public static Vector joining(final Vector src, final Vector dst) {
        return new Vector(
                dst.vec[X] - src.vec[X],
                dst.vec[Y] - src.vec[Y],
                dst.vec[Z] - src.vec[Z]);
    }

    /**
     * Computes the distance between the two specified {@code Vector}s.
     * 
     * @param v1 The first vector.
     * @param v2 The second vector.
     *
     * @return The distance between the two {@code Vector}s.
     */
    public static double distance(final Vector v1, final Vector v2) {
        double x = v2.vec[X] - v1.vec[X];
        double y = v2.vec[Y] - v1.vec[Y];
        double z = v2.vec[Z] - v1.vec[Z];

        return Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * Returns true if the current vector is the 0 vector, false otherwise.
     *
     * @return true or false wether the current vector is the 0 vector.
     */
    public boolean isZero() {
        // return vec[X] == 0.0 && vec[Y] == 0.0 && vec[Z] == 0.0;
        
        return equals(ZERO);
    }
    
    @Override
    public boolean equals(final Object other) {
       if (this == other) {
           return true;
       }
       
       if (!(other instanceof Vector)) {
           return false;
       }
       
       final Vector otherVector = (Vector)other;
       return Arrays.equals(vec, otherVector.vec);
       
    }
    
    /**
     * Returns the length (magnitude) of the vector.
     * 
     * @return The length of the vector.
     */
    public double length() {

        // TODO: If optimization is needed we could cache the length value.
        
        // Avoid making inutile multiplication
        if (isZero()) {
            return 0.0;
        }

        return Math.sqrt(
                vec[X] * vec[X] +
                vec[Y] * vec[Y] +
                vec[Z] * vec[Z]);
    }

    /**
     * Returns the squared length of the vector.
     *
     * @return The squared length of the vector.
     */
    public double squaredLength() {
        
        // Avoid making inutile multiplication
        if (isZero()) {
            return 0.0;
        }
        
        return vec[X] * vec[X]
             + vec[Y] * vec[Y] 
             + vec[Z] * vec[Z];
    }

    /**
     * Computes the distance to the specified vector.
     *
     * @param to The vector to compute the distance with.
     *
     * @return The resulting distance.
     */
    public double distanceTo(final Vector to) {
        double x = to.vec[X] - vec[X];
        double y = to.vec[Y] - vec[Y];
        double z = to.vec[Z] - vec[Z];

        return Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * Compute the dot product between the current object
     * and the specified vector.
     * 
     * @param other The vector to compute with.
     *
     * @return The dot product computed.
     */
    public double dot(final Vector other) {
        return vec[X] * other.vec[X] 
             + vec[Y] * other.vec[Y]
             + vec[Z] * other.vec[Z];
    }

    /**
     * Returns the cross product between the current {@code Vector} and the one
     * specified in parameter.
     * 
     * @param other The vector to compute the cross-product with.
     * 
     * @return The resulting vector.
     */
    public Vector cross(final Vector other) {
        double x = vec[Y] * other.vec[Z] - vec[Z] * other.vec[Y];
        double y = vec[Z] * other.vec[X] - vec[X] * other.vec[Z];
        double z = vec[X] * other.vec[Y] - vec[Y] * other.vec[X];
        
        return new Vector(x, y, z);
    }

    /**
     * Adds the specified vector to the current
     * and returns the resulting vector.
     * 
     * @param vector The vector to add.
     * @return the new resulting vector.
     */
    public Vector add(final Vector vector) {
        return new Vector(
                vec[X] + vector.vec[X],
                vec[Y] + vector.vec[Y],
                vec[Z] + vector.vec[Z]);
    }

    /**
     * Substracts the specified vector to the current
     * and returns the resulting vector.
     *
     * @param vector The vector to substract.
     * @return the new resulting vector.
     */
    public Vector substract(final Vector vector) {
        return new Vector(
                vec[X] - vector.vec[X],
                vec[Y] - vector.vec[Y],
                vec[Z] - vector.vec[Z]);
    }

    /**
     * Multiply the vector multiply by the given value.
     * 
     * @param scalar The value to multiply the vector by.
     *
     * @return A new vector corresponding to the current
     * multiplied by the given scalar.
     */
    public Vector multiply(final double scalar) {
        return new Vector(vec[X] * scalar, vec[Y] * scalar, vec[Z] * scalar);
    }

    /**
     * Translates the current vector.
     * 
     * @param other The vector to translate the current with.
     *
     * @return The new resulting vector.
     */
    public Vector translate(final Vector other) {
        return new Vector(
                vec[X] * other.vec[X],
                vec[Y] * other.vec[Y],
                vec[Z] * other.vec[Z]);
    }

    /**
     * Normalize the current vector.
     *
     * @return The object on which the method is called.
     */
    public Vector normalized() {

        double length = length();
        double x = vec[X];
        double y = vec[Y];
        double z = vec[Z];

        if (length != 0.0 && length != 1.0) {
            double invLength = 1.0/length;
//            x = vec[X] * invLength;
//            y = vec[Y] * invLength;
//            z = vec[Z] * invLength;
            x *= invLength;
            y *= invLength;
            z *= invLength;
        }

        return new Vector(x, y, z);
    }

    /**
     * Returns the opposite vector of the current vector.
     * The opposite vector of an (x, y, z) vector is : (-x, -y, -z).
     * 
     * @return The opposite vector of the current vector.
     */
    public Vector opposite() {
        return new Vector(-vec[X], -vec[Y], -vec[Z]);
    }

    /**
     * Computes the reflected vector.
     * 
     * @param other The reference.
     * @return The reflected vector.
     */
    public Vector reflected(final Vector other) {
        // VR = V - ( 2 * ( V . N )) * N
        Vector result = substract(other.multiply(other.dot(this) * 2.0));
        return result;
    }

    /**
     * Getter for the x value of the vector.
     * 
     * @return The x value of the vector.
     */
    public double getX() {
        return vec[X];
    }

    /**
     * Getter for the y value of the vector.
     *
     * @return The y value of the vector.
     */
    public double getY() {
        return vec[Y];
    }


    /**
     * Getter for the z value of the vector.
     *
     * @return The z value of the vector.
     */
    public double getZ() {
        return vec[Z];
    }


//    @Override
//    public boolean equals(final Object other) {
//         if (this == other) {
//            return true;
//        }
//         
//        if (other == null || other.getClass() != this.getClass()) {
//            return false;
//        }
//
//        Vector vecOther = (Vector)other;
//        return Arrays.equals(this.vec, vecOther.vec);
//    }

    /**
     * Returns the hash code of the current object.
     *
     * @return The hash code of the current object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (vec != null ? Arrays.hashCode(vec) : 0);
        return hash;
    }

    /**
     * Returns a <code>String</code> representation of the current object.
     * 
     * @return A <code>String</code> representation of the current object.
     */
    @Override
    public String toString() {
        StringBuilder sb = (new StringBuilder("[Vector : x = "))
          .append(vec[X])
          .append(", y = ").append(vec[Y])
          .append(", z = ").append(vec[Z]).append("]");
        return sb.toString();
    }
}
