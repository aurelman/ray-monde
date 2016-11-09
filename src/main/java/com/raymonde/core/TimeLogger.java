package com.raymonde.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeLogger {

    private static final Logger logger = LoggerFactory.getLogger(TimeLogger.class);

    /**
     * Logs the execution time of the process specified by {@code lambda}. Activate trace level for
     * the logger {@code com.raymonde.core.TimeLogger} if you want to view those logs.
     *
     * @param description a tag that will be added to the log message
     * @param lambda the code to execute and whome execution time should be logged
     * @param <T> The type of exception that can be thrown by the {@code lambda}
     *
     * @throws T
     */
    public static <T extends Throwable> void logExecutionTime(final String description, final Procedure<T> lambda)  throws T {
        final long startTime =  System.nanoTime();
        logger.trace("[{}] starting", description);
        lambda.process();
        final long elapsedTime = System.nanoTime() - startTime;
        logger.trace("[{}] finished in {}ms", description, elapsedTime / 1000_000.);
    }

    @FunctionalInterface
    public interface Procedure<E extends Throwable> {
        void process() throws E;
    }
}
