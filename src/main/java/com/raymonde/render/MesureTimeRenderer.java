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

import com.raymonde.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a technical Renderer. Based on decorator pattern it is used to 
 * log time consumming information for rendering.
 * This Renderer cannot be instantiated by users on the command line.
 * 
 * @author aurelman
 * @deprecated 
 */
@Deprecated
public class MesureTimeRenderer implements Renderer {
    
    private Renderer renderer;
    /**
     * The available logger for the <code>DefaultRenderer</code> class.
     */ 
    private static final Logger logger =
            LoggerFactory.getLogger(DefaultRenderer.class.getName());
    
    
    public MesureTimeRenderer(final Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public Surface render(Scene scene) throws RenderException {
        logger.info("Start rendering scene");
        double start = System.currentTimeMillis();
        
        final Surface surfaceResult = renderer.render(scene);
        double elapsed = System.currentTimeMillis() - start;
        logger.info("Finish rendering scene in {} ms", elapsed);
        return surfaceResult;
        
    }
}
