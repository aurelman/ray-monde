/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymonde.render;

import com.raymonde.scene.Scene;
import java.util.List;

/**
 *
 * @author aurelman
 */
public class RenderingTask implements Runnable {

    private final Scene scene;
    
    private final List points;
    
    public RenderingTask(final Scene scene) {
        this.scene = scene;
        points = null;
    }

    RenderingTask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
