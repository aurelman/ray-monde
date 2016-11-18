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

package com.raymonde;

import com.raymonde.load.SceneBuildingException;
import com.raymonde.load.yaml.YamlSceneBuilder;
import com.raymonde.render.Renderer;
import com.raymonde.render.RendererFactory;
import com.raymonde.render.RenderingException;
import com.raymonde.render.RenderingSurface;
import com.raymonde.save.SaveException;
import com.raymonde.save.SceneSaver;
import com.raymonde.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.raymonde.core.TimeLogger.logElapsedTime;


/**
 * {@code RayMonde} is the launching class of ray-monde.
 *
 */
public class RayMonde {

    /**
     * logger for the {@code RayMonde} class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RayMonde.class);
    
    /**
     * Default constructor protected.
     */
    protected RayMonde() {
    }
    
    /**
     * Program entry point.
     *
     * @throws SaveException
     * @throws IOException
     * @throws SceneBuildingException
     * @throws RenderingException
     * 
     * @param args An array of argument.
     */
    public static void main(final String [] args)
        throws SaveException,
            IOException,
            RenderingException,
            SceneBuildingException {
        logger.info("starting ray-monde");

        OptionParsing opt = new OptionParsing(args);

        Renderer renderer  = RendererFactory.createRenderer(opt.getRenderer());

        String filename = opt.getSceneFilename();
        
        logger.info("start loading scene from {}", filename);
        final Scene scene = logElapsedTime(() ->
                new YamlSceneBuilder()
                    .fromFile(filename)
                    .build())
                .andReturn();
        logger.info("scene loaded", filename);

        logger.info("start rendering scene", filename);
        final RenderingSurface rendered = logElapsedTime("rendering scene", () ->
                renderer.renderSceneThroughCameraAntialiased(scene, scene.getDefaultCamera()))
                .andReturn();
        logger.info("rendering scene finished", filename);

        logger.info("saving scene to {}", opt.getOutputFilename());
        SceneSaver ss = new SceneSaver();
        ss.save(rendered, opt.getOutputFilename());
        logger.info("file {} saved", opt.getOutputFilename());

        logger.info("finishing ray-monde");
    }
}
