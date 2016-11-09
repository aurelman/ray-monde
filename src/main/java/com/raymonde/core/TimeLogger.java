package com.raymonde.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeLogger {
    private static final Logger logger = LoggerFactory.getLogger(TimeLogger.class);

    public static void logExecutionTime(final String description, final Procedure lambda) {
        long startTime =  System.nanoTime();
        logger.info("[{}] starting", description);
        lambda.process();
        long elapsedTime = System.nanoTime() - startTime;
        logger.info("[{}] finished in {}ms", description, elapsedTime / 1000_000);
    }

    public interface Procedure {
        void process();
    }
}
