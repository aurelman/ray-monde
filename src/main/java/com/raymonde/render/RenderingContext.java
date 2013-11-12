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

package com.raymonde.render;

/**
 * <code>RenderingContext</code> objects describes some values
 * (such as refraction factor, or depth) which are
 * specific to the current rendering step.
 * 
 * @author aurelman
 */
public class RenderingContext {

    /**
     * The current refraction index.
     */
    private double refraction;

    /**
     * The current depth in the algorithm.
     */
    private int depth;

    private boolean out;

    public static RenderingContext incremented(final RenderingContext ctx) {
        return new RenderingContext(ctx.getDepth()+1, ctx.getRefraction());
    }
    
    public RenderingContext() {
        
    }

    public RenderingContext(final int depth, final double refraction) {
        this.depth = depth;
        this.refraction = refraction;
    }

    /**
     * @return The refraction
     */
    public double getRefraction() {
        return this.refraction;
    }

    /**
     * @param refraction The refraction to set
     */
    public void setRefraction(final double refraction) {
        this.refraction = refraction;
    }

    /**
     * @return the depth
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(final int depth) {
        this.depth = depth;
    }

    /**
     * @return the out
     */
    public boolean isOut() {
        return this.out;
    }

    /**
     * @param out the out to set
     */
    public void setOut(final boolean out) {
        this.out = out;
    }
}
