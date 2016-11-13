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

import com.raymonde.render.RenderingSurface;
import com.raymonde.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * {@code SceneSaver} allows saving a rendered scene in image files.
 */
public class SceneSaver {

    /**
     * The available logger for this class.
     */
    private final static Logger logger = LoggerFactory.getLogger(SceneSaver.class);
    
    /**
     * 
     * @param renderingSurface the surface on which to draw the scene.
     * @param outputFilename The destination file.
     * 
     * @throws SaveException
     */
    public void save(final RenderingSurface renderingSurface, final String outputFilename)
            throws SaveException {
        save(renderingSurface, new File(outputFilename));
    }

    /**
     * Saves the given scene to the given file.
     *
     * @param outputFilename The filename where to save the scene.
     * @throws SaveException
     */
    public void save(final RenderingSurface renderingSurface, final File outputFilename)
            throws SaveException {
        int width = renderingSurface.getPixelWidth();
        int height = renderingSurface.getPixelHeight();

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int [] rgbColors = SaverUtiliy.colorArrayToIntegerArray(renderingSurface.getColors());

        bi.setRGB(0, 0, width, height, rgbColors, 0, width);

        try {
            ImageIO.write(bi, "png", outputFilename);
        } catch (IOException ex) {
            logger.error("an error occurred while trying to save rendered scene to {}", outputFilename.getAbsolutePath(), ex);
            throw new SaveException("image couldn't be saved", ex);
        }
    }
}
