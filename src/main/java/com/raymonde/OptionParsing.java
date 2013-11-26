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
package com.raymonde;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ExampleMode;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uses the args4j API.
 * 
 * @author aurelman
 */
public class OptionParsing {
    
    private static final Logger logger = 
            LoggerFactory.getLogger(OptionParsing.class);
    
    private String renderer = "default";
    
    @Option(name="-o", required=true, aliases="--output")
    private String outputFilename;
    
    @Argument(index=0)
    private String sceneFilename;
    
    public OptionParsing(final String [] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException ex) {
            logger.debug("an error occurs while parsing the command line", ex);
            System.err.println(parser.printExample(ExampleMode.ALL));
        }
    }

    /**
     * Returns the parsed scene filename.
     * 
     * @return The parsed scene filename
     */
    public String getSceneFilename() {
        return sceneFilename;
    }

    /**
     * Returns the parsed renderer type.
     * 
     * @return The parsed renderer type
     */
    public String getRenderer() {
        return renderer;
    }

    public String getOutputFilename() {
        return outputFilename;
    }
}
