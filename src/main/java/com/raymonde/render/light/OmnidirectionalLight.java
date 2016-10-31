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
package com.raymonde.render.light;

import com.raymonde.core.Color;
import com.raymonde.core.Vector;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 *
 * @author aurelman
 */
@ThreadSafe
@Immutable
public class OmnidirectionalLight extends Light {

    /**
     * 
     */
    private final Vector attenuation;

    /**
     * The color of the light.
     */
    private final Color color;

    /**
     *
     * @param position The position of the light.
     * @param color The color of the light.
     * @param attenuation The attenuation of the light.
     */
    public OmnidirectionalLight(final Vector position,
            final Color color, final Vector attenuation) {
        super(position);
        this.color = color;
        this.attenuation = attenuation;
    }


    @Override
    public Color colorAt(final Vector point) {
        double dist = point.distanceTo(getPosition());
        double attCoeff = 1./(getAttenuation().x()*dist*dist);
        return getColor().multiply(attCoeff);
    }

    /**
     * @return the attenuation
     */
    public Vector getAttenuation() {
        return attenuation;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }
}
