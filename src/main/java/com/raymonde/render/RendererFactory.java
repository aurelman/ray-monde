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

import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a method to instantiate {@link Renderer}s objects.
 */
public class RendererFactory {

    private static final Logger logger = LoggerFactory.getLogger(RendererFactory.class);

    private static final String RENDERER_BASE_PACKAGE = "com.raymonde.render.";

    private static final String RENDERER_CLASSNAME_SUFFIX = "Renderer";

    /**
     * Instantiates the {@link Renderer} implementation regarding the specified {@code type}.<br />
     * The {@code type} must be cased as hyphenated (e.g. "multi-threaded"). <br />
     * To find the appropriate {@code Renderer} this method will capitalize the specified {@code type}
     * and search for a class named like "com.raymonde.render.<i>CapitalizedType</i>Renderer".<br />
     * e.g:
     * <pre>
     * +----------------+--------------------------------------------+
     * | type           | class name                                 |
     * +----------------+--------------------------------------------+
     * | multi-threaded | com.raymonde.render.MultiThreadedRenderer  |
     * | default        | com.raymonde.render.DefaultRenderer        |
     * +----------------+--------------------------------------------+
     * </pre>
     *
     * If the class cannot be found or if the class cannot be instantiated an {@link UnableToCreateRendererException}
     * exception is thrown.
     *
     * @param type the type of the {@link Renderer} to instantiate
     *
     * @return the requested {@link Renderer} implementation.
     *
     * @throws UnableToCreateRendererException if no corresponding class exist or the class cannot be instantiated.
     */
    public static final Renderer createRenderer(final String type) {

        final String name = RENDERER_BASE_PACKAGE + hyphenatedToCapitalized(type) + RENDERER_CLASSNAME_SUFFIX;

        try {
            @SuppressWarnings("unchecked") Class<? extends Renderer> clazz =  (Class< ? extends Renderer>)Class.forName(name);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
            logger.error("cannot create renderer because class {} could not be found", name, e);
            throw new UnableToCreateRendererException("class not found", e);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("an error occurred while instantiating renderer {}", name, e);
            throw new UnableToCreateRendererException("instantiation error", e);
        }
    }

    /**
     * Transforms {@code String}s written such as <i>"multi-threader"</i> into <i>"MultiThreaded"</i>
     *
     * @param string
     * @return
     */
    private static String hyphenatedToCapitalized(final String string) {
        return WordUtils.capitalizeFully(string, '-')
                .replace("-", "");
    }
}
