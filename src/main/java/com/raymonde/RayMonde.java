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

import com.raymonde.load.ParsingException;
import com.raymonde.load.SceneParser;
import com.raymonde.load.yml.YamlSceneParser;
import com.raymonde.render.RenderingException;
import com.raymonde.render.Renderer;
import com.raymonde.render.RendererFactory;
import com.raymonde.scene.Scene;
import com.raymonde.save.SaveException;
import com.raymonde.save.SceneSaver;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <code>RayMonde</code> is the class which owns the main method.
 *
 * @author aurelman
 */
public class RayMonde {

    /**
     * Available logger for the <code>RayMonde</code> class.
     */
    private static final Logger logger =
            LoggerFactory.getLogger(RayMonde.class);
    
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
     * @throws ParsingException
     * @throws RenderingException
     * 
     * @param args An array of argument.
     */
    public static void main(final String [] args)
        throws SaveException,
            IOException,
            RenderingException,
            ParsingException {
        logger.info("starting ray-monde");

        OptionParsing opt = new OptionParsing(args);
        final Renderer renderer = RendererFactory.forType(opt.getRenderer());
       
        
        
        String filename = opt.getSceneFilename();
        
        logger.info("Loading scene : {}", filename);
        Scene scene = getSceneParser().parseFile(filename);
        logger.info("Loaded scene : {}", filename);
        
        
        logger.info("Rendering scene : {}", filename);
        renderer.render(scene);
        logger.info("Rendered scene : {}", filename);
        
        SceneSaver ss = new SceneSaver();
        ss.save(scene, opt.getOutputFilename());
        
        logger.info("finishing ray-monde");
    }
    
    private static SceneParser getSceneParser() {
        return new YamlSceneParser();
    }

}
