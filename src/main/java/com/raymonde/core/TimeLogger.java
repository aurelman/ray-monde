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
     * @param lambda the code to execute and whom execution time should be logged
     * @param <T> The type of exception that can be thrown by the {@code lambda}
     *
     * @throws T
     */
    public static <T extends Throwable> void logElapsedTime(final String description, final Procedure<T> lambda)  throws T {
        final long startTime =  System.nanoTime();
        lambda.process();
        final long elapsedTime = System.nanoTime() - startTime;
        logger.trace("[{}] elapsed time: {}ms", description, elapsedTime / 1000_000.);
    }

    /**
     * Logs the execution time of the process specified by {@code lambda}. Activate trace level for
     * the logger {@code com.raymonde.core.TimeLogger} if you want to view those logs.
     *
     * @param lambda the code to execute and whom execution time should be logged
     * @param <T> The type of exception that can be thrown by the {@code lambda}
     *
     * @throws T
     */
    public static <T extends Throwable> void logElapsedTime(final Procedure<T> lambda)  throws T {
        final long startTime =  System.nanoTime();
        lambda.process();
        final long elapsedTime = System.nanoTime() - startTime;
        logger.trace("elapsed time: {}ms", elapsedTime / 1000_000.);
    }

    /**
     * Logs the execution time of the process specified by {@code lambda}. Activate trace level for
     * the logger {@code com.raymonde.core.TimeLogger} if you want to view those logs.
     *
     * @param description a tag that will be added to the log message
     * @param lambda the code to execute and whom execution time should be logged
     * @param <T> The type of exception that can be thrown by the {@code lambda}
     *
     * @return the andReturn returned by the execution of the lambda.
     *
     * @throws T
     */
    public static <R, T extends Throwable> FunctionResult<R> logElapsedTime(final String description, final Function<R, T> lambda)  throws T {
        final long startTime =  System.nanoTime();
        R result = lambda.apply();
        final long elapsedTime = System.nanoTime() - startTime;
        logger.trace("[{}] elapsed time: {}ms", description, elapsedTime / 1000_000.);
        return new FunctionResult<>(result);
    }

    /**
     * Logs the execution time of the process specified by {@code lambda}. Activate trace level for
     * the logger {@code com.raymonde.core.TimeLogger} if you want to view those logs.
     *
     * @param lambda the code to execute and whom execution time should be logged
     * @param <T> The type of exception that can be thrown by the {@code lambda}
     *
     * @return the andReturn returned by the execution of the lambda.
     *
     * @throws T
     */
    public static <R, T extends Throwable> FunctionResult<R> logElapsedTime(final Function<R, T> lambda)  throws T {
        final long startTime =  System.nanoTime();
        final R result = lambda.apply();
        final long elapsedTime = System.nanoTime() - startTime;
        logger.trace("elapsed time: {}ms", elapsedTime / 1000_000.);
        return new FunctionResult<>(result);
    }

    @FunctionalInterface
    public interface Procedure<E extends Throwable> {
        void process() throws E;
    }

    @FunctionalInterface
    public interface Function<R, E extends Throwable> {
        R apply() throws E;
    }

    /**
     * A class that just holds the result of a {@link Function} execution.
     * Allows some fancy writing :
     * <pre>
     *
     *     Foo doSomething() {
     *         return new Foo();
     *     }
     *
     *     Foo any = logElapsedTime("description", () -> doSomething())
     *         .andReturn();
     *
     * </pre>
     * @param <T>
     */
    public static class FunctionResult<T> {
        private final T result;

        private FunctionResult(T result) {
            this.result = result;
        }

        public T andReturn() {
            return result;
        }
    }
}
