package Window;

import Control.SingletonHolder;
import Option.DataOption.WindowOptions;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.Dimension;

public abstract class AbstractWindow extends Application {

    private Dimension size;
    private boolean resizable;
    private String title;
    private WindowOptions windowOptions = (WindowOptions) SingletonHolder.getInstance().getGeneralOptions().getOptions().get("WindowOptions");
    protected Stage stage;
    protected final WindowConfigure windowConfigure = SingletonHolder.getInstance().getWindowConfigure();

    protected void close()
    {
        stage.close();
    }
    protected void open()
    {
        stage.show();
    }

    protected void stageConfigure()
    {
        size = windowOptions.getSize();
        resizable = windowOptions.isResizeable();
        title = windowOptions.getTitle();

        stage.setResizable(resizable);
        stage.setHeight(size.getHeight());
        stage.setWidth(size.getWidth());

        stage.setTitle(title);
    }
}
