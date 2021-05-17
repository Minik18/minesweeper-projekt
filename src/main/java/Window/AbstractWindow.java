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

    /**
     * A singleton {@link WindowConfigure} object to setup the given scene.
     */
    protected final WindowConfigure windowConfigure = WindowConfigure.getInstance();
    /**
     *  A controller of the scene.
     */
    protected Controller controller;
    /**
     * A  {@link WindowOptions} object to get access to the stored values.
     */
    protected WindowOptions windowOptions;

    /**
     * An abstract method that the sets the stage's height and width, sets the controller and starts the configuration.
     * @param stage A stage that will be set up.
     * @throws IOException When the icon cannot be imported.
     */
    protected abstract void loadStage(Stage stage) throws IOException;
}
