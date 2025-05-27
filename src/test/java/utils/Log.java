package utils;

import org.apache.logging.log4j.LogManager;

public class Log {

    // Create one static Logger instance (you can change the class here)
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("Log.class");

    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void fatal(String message) {
        logger.fatal(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void fatal(String message, Throwable throwable) {
        logger.fatal(message, throwable);
    }
}
