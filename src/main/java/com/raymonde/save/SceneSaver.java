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

package com.raymonde.save;

import com.raymonde.scene.Scene;
import com.raymonde.render.Surface;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p><code>SceneSaver</code> allows saving a rendered scene in image files.</p>
 * 
 * @author aurelman
 */
public class SceneSaver {

    /**
     * The available logger for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(SceneSaver.class.getName());
    
    /**
     * 
     * @param scene The scene to save.
     * @param outputFilename The destination file.
     * 
     * @throws SaveException
     */
    public void save(final Scene scene, final String outputFilename)
            throws SaveException {
        save(scene, new File(outputFilename));
    }

    /**
     * Saves the given scene to the given file.
     * 
     * @param scene The scene to save.
     * @param outputFilename The filename where to save the scene.
     * @throws SaveException
     */
    public void save(final Scene scene, final File outputFilename)
            throws SaveException {
        int width = scene.getSurface().getWidth();
        int height = scene.getSurface().getHeight();

        BufferedImage bi =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int [] rgbColors = SaverUtiliy.colorArrayToIntegerArray(
                scene.getSurface().getColors());

        bi.setRGB(0, 0, width, height, rgbColors, 0, width);
        
        try {
            ImageIO.write(bi, "png", outputFilename);
        } catch (IOException ex) {
            getLogger().error(null, ex);
            throw new SaveException("Image couldn't be saved", ex);
        }
    }

    /**
     * Saves the given scene to the given file.
     *
     * @param outputFilename The filename where to save the scene.
     * @throws SaveException
     */
    public void save(final Surface renderingSurface, final File outputFilename)
            throws SaveException {
        int width = renderingSurface.getWidth();
        int height = renderingSurface.getHeight();

        BufferedImage bi =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int [] rgbColors = SaverUtiliy.colorArrayToIntegerArray(
                renderingSurface.getColors());

        bi.setRGB(0, 0, width, height, rgbColors, 0, width);

        try {
            ImageIO.write(bi, "png", outputFilename);
        } catch (IOException ex) {
            getLogger().error(null, ex);
            throw new SaveException("Image couldn't be saved", ex);
        }
    }

    /**
     * Returns the available logger for the current class.
     * 
     * @return The available logger for the current class.
     */
    public static Logger getLogger() {
        return SceneSaver.logger;
    }
}
