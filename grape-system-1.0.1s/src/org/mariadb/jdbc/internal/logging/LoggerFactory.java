package org.mariadb.jdbc.internal.logging;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoggerFactory {
    public static final Logger NO_LOGGER = new NoLogger();
    public static Boolean hasToLog = null;
    public static Class loggerClass = null;
    public static Method method = null;

    /**
     * Initialize factory.
     *
     * @param mustLog indicate if must initiate Slf4j log
     */
    @SuppressWarnings("unchecked")
    public static void init(boolean mustLog) {
        if (hasToLog == null || hasToLog != mustLog) {
            if (mustLog) {
                synchronized (LoggerFactory.class) {
                    if (hasToLog == null || hasToLog != mustLog) {
                        try {
                            loggerClass = Class.forName("org.slf4j.LoggerFactory");
                            method = loggerClass.getMethod("getLogger", Class.class);
                            hasToLog = Boolean.TRUE;
                        } catch (ClassNotFoundException classNotFound) {
                            System.out.println("Logging cannot be activated, missing slf4j dependency");
                            hasToLog = Boolean.FALSE;
                        } catch (NoSuchMethodException classNotFound) {
                            System.out.println("Logging cannot be activated, missing slf4j dependency");
                            hasToLog = Boolean.FALSE;
                        }
                    }
                }
            }
        }

    }

    /**
     * Initialize logger.
     *
     * @param clazz initiator class
     * @return logger
     */
    public static Logger getLogger(Class<?> clazz) {
        if (hasToLog != null && hasToLog) {
            try {
                return new Slf4JLogger((org.slf4j.Logger) method.invoke(loggerClass, clazz));
            } catch (IllegalAccessException illegalAccess) {
                return null;
            } catch (InvocationTargetException invocationException) {
                return null;
            }
        } else {
            return NO_LOGGER;
        }
    }

}
