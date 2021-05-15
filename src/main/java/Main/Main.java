package Main;

import Logging.Log;
import Window.GameWindow;

public class Main {

    public static void main(String[] args) {
        Log.log("info",Main.class.getName() + " - Application Started!");
        GameWindow.launch(GameWindow.class,args);
        Log.log("info",Main.class.getName() + " - Application Exited!");

    }
}
