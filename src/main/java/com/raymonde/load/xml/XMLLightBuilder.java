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

package com.raymonde.load.xml;

import com.raymonde.load.ParsingException;
import com.raymonde.render.light.Light;
import org.w3c.dom.Element;

/**
 *
 * @author aurelman
 */
public abstract class XMLLightBuilder {

    /**
     *
     * @throws ParsingException When an error occurs during parsing.
     * 
     * @param element The element.
     * @return The resulting ligth.
     */
    public abstract Light build(final Element element)
            throws ParsingException;
    
}
