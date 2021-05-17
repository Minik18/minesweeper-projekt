package Window;

import Logging.Log;
import Option.GeneralOptions;
import Score.ImportScore;
import Control.HighscoreController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A class represent a scoreboard scene in the game.
 */
public class HighscoreWindow  {

    private static HighscoreWindow instance = new HighscoreWindow();
    private Scene scene;
    private Scene mainScene;
    private final String PATH_TO_STAGE = "Stages/HighscoreStage.fxml";
    private HighscoreController controller;
    private Stage stage;

    /**
     * Gets a {@link HighscoreWindow} object.
     * @return An instantiated {@link HighscoreWindow} object.
     */
    public static HighscoreWindow getInstance()
    {
        return instance;
    }

    /**
     * Sets the given stage's scene to an imported new scene.
     * @param stage A stage to set the new scene.
     * @param oldScene The old scene se be able to set the original back.
     */
    public void setScene(Stage stage, Scene oldScene)
    {
        this.stage = stage;
        mainScene = oldScene;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLLoader.getDefaultClassLoader().getResource(PATH_TO_STAGE));
        try {
            Parent root = loader.load();
            controller = loader.getController();
            scene = new Scene(root);
        } catch (IOException e) {
            Log.log("error", getClass().getName() + " " + e.getMessage());
        }
    }

    /**
     * Gets the attached scoreboard scene.
     * @return A scoreboard scene.
     */
    public Scene getScene()
    {
        return scene;
    }

    /**
     * Configures the elements inside the scoreboard scene.
     */
    public void setup()
    {
        controller.back.setOnMouseClicked(action ->
        {
            stage.setScene(mainScene);
        });

        TableColumn place = (TableColumn) controller.tableView.getColumns().get(0);
        TableColumn name = (TableColumn) controller.tableView.getColumns().get(1);
        TableColumn bombNumber = (TableColumn) controller.tableView.getColumns().get(2);
        TableColumn time = (TableColumn) controller.tableView.getColumns().get(3);

        place.setCellValueFactory(new PropertyValueFactory<>("place"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        bombNumber.setCellValueFactory(new PropertyValueFactory<>("bombNumber"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));

        ImportScore.getInstance().getScores().forEach(controller.tableView.getItems()::add);
        Log.log("info", getClass().getName() + " - Score stage initial setup was successfully made!");

    }
}
