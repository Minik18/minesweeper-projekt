package Window;

import javafx.stage.Stage;

public class GameWindow extends AbstractWindow {

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stageConfigure();
        this.stage = windowConfigure.configureGameWindow(stage,(int)stage.getWidth(),(int)stage.getHeight());
        open();

    }
}
