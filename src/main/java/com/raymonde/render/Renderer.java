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

package com.raymonde.render;

import com.raymonde.scene.Scene;

/**
 * Base interface that any renderer should implements.
 * @author aurelman
 */
public interface Renderer {

    /**
     * The max depth in the rendering algorithm.
     */
    //private int maxDepth;

    
    /**
     * Renders the specified scene.
     * 
     * @param scene The scene to render.
     *
     * @throws RenderException When an unexpectable error occurs during 
     * rendering. Message exception, or cause exception should give more detail
     */
    public Surface render(final Scene scene)
            throws RenderException;

    /**
     * 
     * @param ray
     * @param level
     * @param ctx
     * @return
     */
//    public abstract Color computeColor(final Ray ray,
//            final RenderingContext ctx);

    /**
     * @return the maxDepth
     */
//    public int getMaxDepth() {
//        return this.maxDepth;
//    }

    /**
     * @param maxDepth the depth to set
     */
//    public void setMaxDepth(final int maxDepth) {
//        this.maxDepth = maxDepth;
//    }
}
