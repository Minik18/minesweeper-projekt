package Window;

import Control.Controller;
import Option.DataOption.WindowOptions;
import Option.GeneralOptions;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.io.IOException;

public abstract class AbstractWindow extends Application {

    protected final WindowConfigure windowConfigure = WindowConfigure.getInstance();
    protected Controller controller;
    protected WindowOptions windowOptions;

    protected abstract void loadStage(Stage stage) throws IOException;
}
