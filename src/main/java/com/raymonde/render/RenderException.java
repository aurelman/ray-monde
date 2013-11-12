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

import com.raymonde.exception.RayMondeException;

/**
 * 
 * @author aurelman
 */
public class RenderException extends RayMondeException{

    /**
     * Constructs a default <code>RenderException</code>.
     */
    public RenderException() {
        super();
    }

    /**
     * Constructs a <code>RenderException</code> with the specified message.
     * 
     * @param message The message.
     */
    public RenderException(final String message) {
        super(message);
    }

    /**
     * Constructs a <code>RenderException</code> with the specified message and
     * cause.
     * 
     * @param message The message.
     * @param cause The cause.
     */
    public RenderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a <code>RenderedException</code> with the specified cause.
     * 
     * @param cause The cause.
     */
    public RenderException(final Throwable cause) {
        super(cause);
    }
 }
