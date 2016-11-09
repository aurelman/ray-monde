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

import com.raymonde.exception.RayMondeException;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Returns a Rendered
 */
public class RendererFactory {

    private final static Logger logger = LoggerFactory.getLogger(RendererFactory.class);


    public static final Renderer createRenderer(final String type) throws RayMondeException {

        final String name = "com.raymonde.render." + capitalize(type) + "Renderer";

        try {
            @SuppressWarnings("unchecked") Class<? extends Renderer> clazz =  (Class< ? extends Renderer>)Class.forName(name);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
            logger.error("cannot create renderer because class {} is not found", name, e);
            throw new RayMondeException("cannot create renderer", e);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("an error occurred while instantiating renderer {}", name, e);
            throw new RayMondeException("cannot create renderer", e);
        }
    }

    private static String capitalize(final String string) {
        return WordUtils.capitalizeFully(string, '-').replace("-", "");
    }
}
