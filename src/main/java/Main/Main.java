package Main;

import Logging.Log;
import Window.GameWindow;

/**
 * A class to run the application.
 */
public class Main {

    /**
     * This method calls the {@link GameWindow#launch(String...)} method which starts the application.
     * @param args Arguments given from console. Does not play part in the project.
     */
    public static void main(String[] args) {
        Log.log("info",Main.class.getName() + " - Application Started!");
        GameWindow.launch(GameWindow.class,args);
        Log.log("info",Main.class.getName() + " - Application Exited!");

    }
}
