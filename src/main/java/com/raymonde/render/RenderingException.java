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

/**
 * Exceptions that can be thrown during the rendering process by a {@link Renderer}.
 */
public class RenderingException extends Exception{

    /**
     * Constructs a default {@code RenderingException}.
     */
    public RenderingException() {
        super();
    }

    /**
     * Constructs a {@code RenderingException} with the specified message.
     * 
     * @param message The message.
     */
    public RenderingException(final String message) {
        super(message);
    }

    /**
     * Constructs a {@code RenderingException} with the specified message and
     * cause.
     * 
     * @param message The message.
     * @param cause The cause.
     */
    public RenderingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@code RenderingException} with the specified cause.
     * 
     * @param cause The cause.
     */
    public RenderingException(final Throwable cause) {
        super(cause);
    }
 }
