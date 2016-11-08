package com.raymonde.render;


import static com.google.common.base.Preconditions.checkArgument;

/**
 */
public class Pixel {
    private final int x;

    private final int y;

    public Pixel(final int x, final int y) {
        checkArgument(x >= 0, "x must be strictly positive");
        checkArgument(y >= 0, "y must be strictly positive");

        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}
