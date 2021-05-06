package Window;

import Option.DataOption.WindowOptions;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.Dimension;

public abstract class AbstractWindow extends Application {

    protected Dimension size;
    protected WindowOptions windowOptions;
    protected Stage stage;
    protected final WindowConfigure windowConfigure = WindowConfigure.getInstance();

    protected void close()
    {
        stage.close();
    }
    protected void open()
    {
        stage.show();
    }

    protected abstract void stageConfigure();
}
