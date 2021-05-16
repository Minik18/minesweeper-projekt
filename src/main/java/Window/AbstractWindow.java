package Window;

import Control.Controller;
import Option.DataOption.WindowOptions;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * An abstract class that represents a viewable window in the application.
 */
public abstract class AbstractWindow extends Application {

    protected final WindowConfigure windowConfigure = WindowConfigure.getInstance();
    protected Controller controller;
    protected WindowOptions windowOptions;

    protected abstract void loadStage(Stage stage) throws IOException;
}
