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

package com.raymonde.exception;

/**
 * <code>RayMondeException</code> is the base class for any exceptions
 * which should be thrown in the Ray-Monde context.
 *
 * @author aurelman
 */
public class RayMondeException extends Exception {

    /**
     * Constructs a default <code>RayMondeException</code> without any message
     * or cause.
     */
    public RayMondeException() {
        super();
    }

    /**
     * Constructs a <code>RayMondeException</code> with the specified message.
     *
     * @param message The message.
     */
    public RayMondeException(final String message) {
        super(message);
    }

    /**
     * Construcs a <code>RayMondeException</code> with the specified message
     * and cause.
     *
     * @param message The message.
     * @param cause The cause.
     */
    public RayMondeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Construcs a <code>RayMondeException</code> with the specified cause.
     *
     * @param cause The cause.
     */
    public RayMondeException(final Throwable cause) {
        super(cause);
    }
}
