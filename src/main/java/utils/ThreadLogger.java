package main.java.utils;

import org.apache.log4j.*;

public class ThreadLogger {

    private static ThreadLocal<Logger> loggerHolder = new ThreadLocal<>();

    public static void setLogger(Logger logger) {
        loggerHolder.set(logger);
    }

    public static Logger getThreadLogger() {
        if ((loggerHolder.get()) == null) {
            loggerHolder.set(Logger.getLogger(String.valueOf(Thread.currentThread().getId())));
        }
        return loggerHolder.get();
    }

}
