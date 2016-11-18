appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}

root(INFO, [ "CONSOLE" ])

// enable TimeLogger logs
logger("com.raymonde.core.TimeLogger", TRACE)
// logger("com.raymonde.scene.Scene", DEBUG)