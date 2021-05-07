package Window;

import Option.DataOption.WindowOptions;
import Option.GeneralOptions;
import javafx.stage.Stage;

import java.awt.*;

public class GameWindow extends AbstractWindow {

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stageConfigure();
        this.stage.setScene(windowConfigure.configureGamePanel(stage));
        open();
    }

    @Override
    protected void stageConfigure() {
        windowOptions = (WindowOptions) GeneralOptions.getInstance().getOptions().get("WindowOptions");
        size = new Dimension(windowOptions.getGamePanelSize().width + windowOptions.getInfoPanelSize(). width,
                windowOptions.getInfoPanelSize().height);

        stage.setResizable(windowOptions.isResizeable());
        stage.setWidth(size.width);
        stage.setHeight(size.height);
        stage.setTitle(windowOptions.getTitle());
    }
}
