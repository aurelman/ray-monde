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

import com.google.common.base.MoreObjects;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Arrays;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

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
 */
@ThreadSafe
@Immutable
public final class Vector {

    /**
     * The cardinality of <code>Vector</code> object.
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
     * This field is an indicator telling whether the current instance of {@link Vector} has been constructed by the
     * {@link #normalized()} method or not.
     * {@code Vector} objects instantiated outside of this method are considered as not-normalized
     * (even if their length is equal to 1).
     * <p>
     * It is used for optimization matters: actually, any subsequent call to {@link Vector#normalized()}
     * on an already normalized instance will return the same instance.
     * (Immutability and thread-safety are preserved).
     *
     * @see #normalized()
     */
    private final boolean normalized;

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
        normalized = false;
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
        this(x, y, z, false);
    }

    private Vector(final double x, final double y, final double z, boolean normalized) {
        vec[X] = x;
        vec[Y] = y;
        vec[Z] = z;
        this.normalized = normalized;
    }

    /**
     * Returns true if the current vector is the 0 vector, false otherwise.
     *
     * @return true or false whether the current vector is the 0 vector.
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
        // Avoid making useless multiplication
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

        // Avoid making useless multiplication
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
     * Subtracts the specified vector to the current
     * and returns the resulting vector.
     *
     * @param vector The vector to subtract.
     * @return the new resulting vector.
     */
    public Vector subtract(final Vector vector) {
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
     * Return a {@code Vector} instance equivalent to the current one but whom length is equal to 1.
     * <p>
     * To preserve immutability (and thus thread-safety) of the {@code Vector} class, this method does not modify
     * the current object and (almost) always return a new instance. However in the case where the current instance is already
     * the result of a previous call to {@code normalized()}, this method will return the current instance itself.
     * This has been made for performance matters because vector normalization is a computation intensive process
     * (squared root, division...).
     *
     */
    public Vector normalized() {
        if (normalized) {
            return this;
        }

        double length = length();
        double x = vec[X];
        double y = vec[Y];
        double z = vec[Z];

        if (length != 0.0 && length != 1.0) {
            double invLength = 1.0/length;
            x *= invLength;
            y *= invLength;
            z *= invLength;
        }

        return new Vector(x, y, z, true);
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
     * @param normalVector The reference.
     * @return The reflected vector.
     */
    public Vector reflected(final Vector normalVector) {
        // VR = V - ( 2 * ( V . N )) * N
        return subtract(normalVector
                .multiply(2. * dot(normalVector)));
    }

    /**
     * Getter for the x value of the vector.
     *
     * @return The x value of the vector.
     */
    public double x() {
        return vec[X];
    }

    /**
     * Getter for the y value of the vector.
     *
     * @return The y value of the vector.
     */
    public double y() {
        return vec[Y];
    }

    /**
     * Getter for the z value of the vector.
     *
     * @return The z value of the vector.
     */
    public double z() {
        return vec[Z];
    }

    /**
     * Returns the hash code of the current object.
     *
     * @return The hash code of the current object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(vec);
    }

    /**
     * Returns a {@link String} representation of the current object.
     *
     * @return A {@link String} representation of the current object.
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("x", vec[X])
                .add("y", vec[Y])
                .add("z", vec[Z])
                .add("normalized", normalized)
                .toString();
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
     * Factory method to create a new {@code Vector} object
     * joining the two specified points.
     *
     * @param source The starting point.
     * @param destination The destination point.
     *
     * @return The newly created {@code Vector}.
     */
    public static Vector joining(final Vector source, final Vector destination) {
        checkNotNull(source, "source vector cannot be null");
        checkNotNull(destination, "destination vector cannot be null");
        return destination.subtract(source);
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
}
