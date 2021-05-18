package Logging;

import org.apache.logging.log4j.*;

/**
 * An object to be able to log onto console and into a file.
 */
public class Log {

    private static final Logger LOGGER = LogManager.getLogger(Log.class.getClassLoader().getResource("log4j2.xml"));
    private static final Marker INFO_MARKER = MarkerManager.getMarker("INFO");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");
    private static final Marker DEBUG_MARKER = MarkerManager.getMarker("DEBUG");
    private static final Marker WARNING_MARKER = MarkerManager.getMarker("WARNING");

    /**
     * A method to log the given string onto console and into the configured file. It logs different kind of levels
     * [ERROR,INFO,DEBUG,WARNING].
     * @param level The string contains the level of the current log. By default it logs on INFO level.
     * @param text The string to be put into file and onto console.
     */
    public static void log(String level,String text)
    {
        switch (level) {
            case "info" -> LOGGER.log(Level.INFO, INFO_MARKER, text);
            case "error" -> LOGGER.log(Level.ERROR, ERROR_MARKER, text);
            case "debug" -> LOGGER.log(Level.DEBUG, DEBUG_MARKER, text);
            case "warning" -> LOGGER.log(Level.WARN, WARNING_MARKER, text);
            default -> LOGGER.log(Level.INFO, INFO_MARKER, "--Default Case Log-- " + text);
        }
    }

}
