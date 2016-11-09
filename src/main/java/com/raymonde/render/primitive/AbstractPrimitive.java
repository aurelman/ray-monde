package com.raymonde.render.primitive;

import com.raymonde.render.material.Material;

/**
 */
public abstract class AbstractPrimitive implements Primitive {
    /**
     * The root material of the primitive.
     */
    private final Material material;

    public AbstractPrimitive(final Material material) {
        this.material = material;
    }
    /**
     * Returns the root material of the primitive.
     *
     * @return The root material.
     */
    public Material getMaterial() {
        return material;
    }
}
