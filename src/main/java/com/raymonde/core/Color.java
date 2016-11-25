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
    private static final Color BLACK = new Color(0, 0, 0);

    /**
     * An instance of the white color.
     */
    private static final Color WHITE = new Color(255, 255, 255);

    /**
     * Message to give to exceptions when any of a component value
     * is out of the rage.
     */
    private static final String COMPONENTS_OUT_OF_RANGE_MESSAGE = "%s component should be between 0.0 and 1.0, but is %s";

    /**
     * Message to give to exceptions when any of a component value
     * is out of the rage.
     */
    private static final String COMPONENTS_OUT_OF_RANGE_MESSAGE_BYTE = "%s component should be between 0 and 255, but is %s";

    /**
     * Internal storage of the color is an int value where
     * the 24 last bits are used to store red, green and blue component.
     * Each component is stored on 8 bits.
     */
    private final int rgb;

    /**
     * Constructs the color with 0 for each component.
     */
    public Color() {
        this(0., 0., 0.);
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
    private  Color(final int red, final int green, final int blue) {
        checkArgument(isComponentValid(red), COMPONENTS_OUT_OF_RANGE_MESSAGE_BYTE, "red", red);
        checkArgument(isComponentValid(green), COMPONENTS_OUT_OF_RANGE_MESSAGE_BYTE, "green", green);
        checkArgument(isComponentValid(blue), COMPONENTS_OUT_OF_RANGE_MESSAGE_BYTE, "blue", blue);
        rgb = componentsAsInteger(red, green, blue);
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
        checkArgument(isComponentValid(r), COMPONENTS_OUT_OF_RANGE_MESSAGE, "red", r);
        checkArgument(isComponentValid(g), COMPONENTS_OUT_OF_RANGE_MESSAGE, "green", g);
        checkArgument(isComponentValid(b), COMPONENTS_OUT_OF_RANGE_MESSAGE, "blue", b);
        rgb = componentsAsInteger(f2i(r), f2i(g), f2i(b));
    }

    public static Color average(Color ... colors) {
        int nb = 0;

        double red = 0.;
        double green = 0.;
        double blue = 0.;

        for (Color c : colors) {
            if (c != null) {
                nb++;
                red += c.r();
                green += c.g();
                blue += c.b();
            }
        }

        return Color.builder()
                .r(red/nb)
                .g(green/nb)
                .b(blue/nb)
                .build();
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

        Color res = new Color(components[0], components[1], components[2]);
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
        return new Color(components[0], components[1], components[2]);
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
        return new Color(components[0], components[1], components[2]);
    }

    /**
     * Returns the red component of the color.
     *
     * @return The red component of the color.
     */
    public double r() {
        return redAsByte() / 255.;
    }

    /**
     * Returns the green component of the color.
     *
     * @return The green component of the color.
     */
    public double g() {
        return greenAsByte() / 255.;
    }

    /**
     * Returns the blue component of the color.
     *
     * @return The blue component of the color.
     */
    public double b() {
        return blueAsByte() / 255.;
    }

    /**
     * Returns an array containing the 3 color components.
     * If {@code res} is the resulting array, then
     * {@code res[0]} is the red component,
     * {@code res[1]} is the green component and
     * {@code res[2]} is the blue component.
     *
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
    private int componentsAsInteger(final int red, final int green, final int blue) {
        int alpha = 255;
        return ((alpha & 0xFF) << 24)
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
        res[0] = redAsByte();
        res[1] = greenAsByte();
        res[2] = blueAsByte();
        return res;
    }

    /**
     * Returns the basic integer value of the red component.
     *
     * @return The basic integer value of the red component.
     */
    private int redAsByte() {
        return (rgb >> 16) & 0xFF;
    }

    /**
     * Returns the basic integer value of the green component.
     *
     * @return The basic integer value of the green component.
     */
    private int greenAsByte() {
        return (rgb >> 8) & 0xFF;
    }

    /**
     * Returns the basic integer value of the blue component.
     *
     * @return The basic integer value of the blue component.
     */
    private int blueAsByte() {
        return rgb & 0xFF;
    }

    /**
     * The white color.
     *
     * @return The white color.
     */
    public static Color white() {
        return Color.WHITE;
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
    private static boolean isComponentValid(double value) {
        return Range.closed(0., 1.).contains(value);
    }

    /**
     * Checks whether a color component is valid (within 0. and 1.)
     *
     * @param value the value to check.
     *
     * @return {@code true} or {@code false} whether the value is valid.
     */
    private static boolean isComponentValid(int value) {
        return Range.closed(0, 255).contains(value);
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
