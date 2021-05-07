package Window;

import Control.Controller;
import Option.DataOption.WindowOptions;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.Dimension;

public abstract class AbstractWindow extends Application {

    protected Dimension size;
    protected WindowOptions windowOptions;
    protected Stage stage;
    protected final WindowConfigure windowConfigure = WindowConfigure.getInstance();
    protected final Controller controller = Controller.getInstance();

    protected abstract void stageConfigure();
}
