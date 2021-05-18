package Window;

import Logging.Log;
import Option.DataOption.WindowOptions;
import Option.GeneralOptions;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main scene class. This class represents the essential part of the UI elements.
 */
public class GameWindow extends AbstractWindow {

    /**
     * This method starts the configuration of the current scene.
     * @param stage A stage from the {@link javafx.application.Application} class.
     * @throws IOException When the configuration file does not exist.
     */
    @Override
    public void start(Stage stage) throws IOException {
        loadStage(stage);
        windowConfigure.configureGameWindow(controller);
        controller.setStage(stage);
        controller.start();
    }

    /**
     * This method configures the size of the stage and set the icon to it.
     * @param stage A stage to set the abvove values.
     * @throws IOException When the configuration fiel does not exist.
     */
    @Override
    protected void loadStage(Stage stage) throws IOException {
        String pathToMainStage = "Stages/MainStage.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(pathToMainStage));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setImportOptionsValue();
        stage.setScene(new Scene(root));

        windowOptions = (WindowOptions) GeneralOptions.getInstance().getOptions().get("WindowOptions");

        stage.setHeight(windowOptions.getInfoPanelSize().height + windowOptions.getConsolePanelSize().height
                + windowOptions.getMenuPanelSize().height );
        stage.setWidth(windowOptions.getInfoPanelSize().width + windowOptions.getGamePanelSize().width ) ;
        stage.setResizable(windowOptions.isResizeable());
        stage.setTitle(windowOptions.getTitle());
        Log.log("info", getClass().getName() + " - Main stage initial setup was successfully made!");
        try
        {
            stage.getIcons().add(windowOptions.getImage());
        }catch(Exception e)
        {
            Log.log("error", getClass().getName() + " - Error when adding icon to stage! " + e.getMessage());
        }
    }

}
