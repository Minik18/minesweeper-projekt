package Logging;

import org.apache.logging.log4j.*;

public class Log {

    private static final Logger LOGGER = LogManager.getLogger(Log.class.getClassLoader().getResource("log4j2.xml"));
    private static final Marker INFO_MARKER = MarkerManager.getMarker("INFO");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");
    private static final Marker DEBUG_MARKER = MarkerManager.getMarker("DEBUG");
    private static final Marker WARNING_MARKER = MarkerManager.getMarker("WARNING");

    public static void log(String level,String text)
    {
       switch (level)
       {
           case "info" : LOGGER.log(Level.INFO,INFO_MARKER,text); break;
           case "error" : LOGGER.log(Level.ERROR,ERROR_MARKER,text); break;
           case "debug" : LOGGER.log(Level.DEBUG,DEBUG_MARKER,text); break;
           case "warning" : LOGGER.log(Level.WARN,WARNING_MARKER,text); break;
           default: LOGGER.log(Level.INFO,INFO_MARKER,"--Default Case Log-- " + text);
       }
    }

}
