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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class MultiThreadedRenderer implements Renderer {
    
    /**
     * Each rendering tasks is dispatched to on thread of the pool.
     */
    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    @Override
    public RenderingSurface renderSceneThroughCamera(final Scene scene, final Camera camera) throws RenderingException {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public RenderingSurface renderSceneThroughCameraAntialiased(final Scene scene, final Camera camera) throws RenderingException {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
