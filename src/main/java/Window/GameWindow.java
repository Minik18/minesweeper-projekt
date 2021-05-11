package Window;

import Option.DataOption.WindowOptions;
import Option.GeneralOptions;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameWindow extends AbstractWindow {

    private final String PATH_TO_MAIN_STAGE = "Stages/MainStage.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        loadStage(stage);
        windowConfigure.configureGameWindow(controller);
        controller.setStage(stage);
        controller.start();
    }

    @Override
    protected void loadStage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(PATH_TO_MAIN_STAGE));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));

        windowOptions = (WindowOptions) GeneralOptions.getInstance().getOptions().get("WindowOptions");

        stage.setHeight(windowOptions.getInfoPanelSize().height + windowOptions.getConsolePanelSize().height
                + windowOptions.getMenuPanelSize().height );
        stage.setWidth(windowOptions.getInfoPanelSize().width + windowOptions.getGamePanelSize().width ) ;
        stage.setResizable(windowOptions.isResizeable());
        stage.setTitle(windowOptions.getTitle());
        try
        {
            stage.getIcons().add(windowOptions.getImage());
        }catch(Exception e)
        {
            //TODO: Handle error
        }
    }

}
