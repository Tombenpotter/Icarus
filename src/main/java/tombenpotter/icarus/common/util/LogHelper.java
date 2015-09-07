package tombenpotter.icarus.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tombenpotter.icarus.Icarus;

public class LogHelper {
    private static Logger logger = LogManager.getLogger(Icarus.name);

    /**
     * @param info - String to log to the info level
     */

    public static void info(Object info) {
        logger.info(info);
    }

    /**
     * @param error - String to log to the error level
     */

    public static void error(Object error) {
        logger.error(error);
    }

    /**
     * @param debug - String to log to the debug level
     */

    public static void debug(Object debug) {
        logger.debug(debug);
    }

    public static Logger getLogger() {
        return logger;
    }
}
