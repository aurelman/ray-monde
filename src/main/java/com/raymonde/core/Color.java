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

import com.google.common.collect.Range;
import lombok.Builder;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Arrays;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@code Color} objects are a RGB (Red, Green, Blue) representation of real color.
 * Each of the component is a double value which represents the intensity of the
 * component in the range from 0.0 to 1.0.
 * The 0.0 value is for absence of intensity of this component,
 * whereas 1.0 is for maximum intensity of the component.
 *
 * This class is Thread safe : every method that return a Color object actually returns a newly created, or a shared immutable object.
 * object.
 */
@ThreadSafe
@Immutable
public final class Color {
    /**
     * An instance of the black color.
     */
    private static final Color BLACK = new Color(0., 0., 0.);

    /**
     * Message to give to exceptions when any of a component value
     * is out of the rage.
     */
    private static final String COMPONENTS_OUT_OF_RANGE_MESSAGE = "Color components %s should be between 0.0 and 1.0, but is %s";

    /**
     * Internal storage of the color is an int value where
     * the 24 last bits are used to store red, green and blue component.
     * Each component is stored on 8 bits.
     */
    private int rgb;

    /**
     * Constructs the color with 0 for each component.
     */
    public Color() {
        this(0., 0., 0.);
    }

     /**
     * Constructs a {@link Color} object by copying the given {@link Color} object.
     *
     * @param other The {@link Color} object to copy.
     */
    private Color(final Color other) {
        rgb = other.rgb;
    }

    /**
     * Constructs the color object with the given components.
     *
     * @param r The red component intensity.
     * @param g The green component intensity.
     * @param b The blue component intensity.
     */
    @Builder
    public Color(final double r, final double g, final double b) {
        checkArgument(isValidComponent(r), COMPONENTS_OUT_OF_RANGE_MESSAGE, "red", r);
        checkArgument(isValidComponent(g), COMPONENTS_OUT_OF_RANGE_MESSAGE, "green", g);
        checkArgument(isValidComponent(b), COMPONENTS_OUT_OF_RANGE_MESSAGE, "blue", b);

        setBasic(f2i(r), f2i(g), f2i(b));
    }

    /**
     *
     * @param other The color to add to the current.
     *
     * @return The resulting <code>Color</code> object.
     */
    public Color add(final Color other) {
        int[] otherComponents = other.getBasic();
        int[] components = getBasic();

        components[0] += otherComponents[0];
        components[1] += otherComponents[1];
        components[2] += otherComponents[2];

        if (components[0] > 255) {
            components[0] = 255;
        }
        if (components[1] > 255) {
            components[1] = 255;
        }
        if (components[2] > 255) {
            components[2] = 255;
        }

        Color res = new Color();
        res.setBasic(components[0], components[1], components[2]);
        return res;
    }
    
    /**
     * Adds the specified color color to the current one and returns the result
     * as a new instance of {@code Color}.
     *
     * @param colors The colors to add.
     *
     * @return The new resulting color.
     */
    public Color add(final Color...colors) {
        int [] components = getBasic();

        if (colors.length != 0) {
            int [] currentComponents;

            for (Color color : colors) {
                currentComponents = color.getBasic();
                components[0] += currentComponents[0];
                components[1] += currentComponents[1];
                components[2] += currentComponents[2];
            }

            if (components[0] > 255) {
                components[0] = 255;
            }
            if (components[1] > 255) {
                components[1] = 255;
            }
            if (components[2] > 255) {
                components[2] = 255;
            }
        }
        Color res = new Color();
        res.setBasic(components[0], components[1], components[2]);
        return res;
    }

    /**
     * Multiply the current color with the specified one. The returns the
     * new resulting color.
     *
     * @param other The color to multiply the current with.
     *
     * @return The resulting color.
     */
    public Color multiply(final Color other) {

        double[] otherComponents = other.get();
        double[] components = get();

        components[0] *= otherComponents[0];
        components[1] *= otherComponents[1];
        components[2] *= otherComponents[2];

        if (components[0] > 1.0) {
            components[0] = 1.0;
        }
        if (components[1] > 1.0) {
            components[1] = 1.0;
        }
        if (components[2] > 1.0) {
            components[2] = 1.0;
        }

        return new Color(components[0], components[1], components[2]);
    }

    /**
     * Multiply each component of the current object by the specified factor.
     *
     * @param factor The factor to multiply each component by.
     *
     * @return The resulting <code>Color<code> object.
     */
    public Color multiply(final double factor) {
        int[] components = getBasic();

        components[0] = round((double)components[0] * factor);
        components[1] = round((double)components[1] * factor);
        components[2] = round((double)components[2] * factor);

        if (components[0] > 255) {
            components[0] = 255;
        }
        if (components[1] > 255) {
            components[1] = 255;
        }
        if (components[2] > 255) {
            components[2] = 255;
        }

        Color res = new Color();
        res.setBasic(components[0], components[1], components[2]);
        return res;
    }

    /**
     * Returns the red component of the color.
     *
     * @return The red component of the color.
     */
    public double r() {
        return getRedBasic() / 255.;
    }

    /**
     * Returns the green component of the color.
     *
     * @return The green component of the color.
     */
    public double g() {
        return getGreenBasic() / 255.;
    }

    /**
     * Returns the blue component of the color.
     *
     * @return The blue component of the color.
     */
    public double b() {
        return getBlueBasic() / 255.;
    }

    /**
     * Returns an array containing the 3 color components.
     * If <code>res</code> is the resulting array, then
     * <code>res[0]</code> is the red component,
     * <code>res[1]</code> is the green component and
     * <code>res[2]</code> is the blue component.

     * @return The array containing the 3 color components.
     */
    public double[] get() {
        double[] res = new double[3];
        res[0] = r();
        res[1] = g();
        res[2] = b();
        return res;
    }

    /**
     * Returns an <code>Integer</code> representation of the current color.
     * The RGB value is decomposed like this :
     * <pre>R8G8B8</pre>.
     *
     * @return The RGB value as an integer value.
     */
    public int rgb() {
        return rgb;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || other.getClass() != getClass()) {
            return false;
        }

        final Color colOther = (Color)other;
        return Objects.equals(colOther.rgb, rgb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rgb);
    }

    /**
     * Returns a <code>String</code> representation of the current color.
     *
     * @return A <code>String</code> representation of the current color.
     */
    @Override
    public String toString() {
        return "Color : " + Arrays.toString(get());
    }
    
    /**
     * Sets the color components with the specified values.
     * This method doesn't do any verification on the incoming values. This the
     * caller's responsibility.
     *
     * @param red The red component.
     * @param green The green component.
     * @param blue The blue component.
     */
    private void setBasic(final int red, final int green, final int blue) {
        int alpha = 255;
        rgb = ((alpha & 0xFF) << 24)
            | ((red & 0xFF) << 16)
            | ((green & 0xFF) << 8)
            | ((blue & 0xFF));
    }

    /**
     * Returns as an array the 3 components as integer values.
     *
     * @return An array containing the three components.
     */
    private int[] getBasic() {
        int[] res = new int[3];
        res[0] = getRedBasic();
        res[1] = getGreenBasic();
        res[2] = getBlueBasic();
        return res;
    }

    /**
     * Returns the basic integer value of the red component.
     *
     * @return The basic integer value of the red component.
     */
    private int getRedBasic() {
        return (rgb >> 16) & 0xFF;
    }

    /**
     * Returns the basic integer value of the green component.
     *
     * @return The basic integer value of the green component.
     */
    private int getGreenBasic() {
        return (rgb >> 8) & 0xFF;
    }

    /**
     * Returns the basic integer value of the blue component.
     *
     * @return The basic integer value of the blue component.
     */
    private int getBlueBasic() {
        return rgb & 0xFF;
    }

    /**
     * The black color.
     *
     * @return The black color.
     */
    public static Color black() {
        return Color.BLACK;
    }

    /**
     * Checks whether a color component is valid (within 0. and 1.)
     *
     * @param value the value to check.
     *
     * @return {@code true} or {@code false} whether the value is valid.
     */
    private static boolean isValidComponent(double value) {
        return Range.closed(0., 1.).contains(value);
    }

    /**
     * Utility method
     *
     * @param value The value to round.
     *
     * @return A rounded integer of value.
     */
    private static int round(final double value) {
        return (int)(value + 0.5);
    }

    /**
     *
     * @param value the value to normalize
     *
     * @return the normalized value
     */
    private static int f2i(double value) {
        return (int) ((value * 255.) + 0.5);
    }

}
