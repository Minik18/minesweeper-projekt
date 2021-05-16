package Control;

import Logging.Log;
import Option.UpdateFile;
import Window.HighscoreWindow;
import Window.WindowConfigure;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * A class taht represents the elements on the main scene and controls the different game states.
 */
public class Controller {

    private Timer timer = Timer.getInstance();
    private UpdateFile updateFile = UpdateFile.getInstance();
    private HighscoreWindow highscoreWindow = HighscoreWindow.getInstance();

    private Stage stage;
    @FXML
    public Pane gamePane;
    @FXML
    public Label nickName;
    @FXML
    public Label bombNumber;
    @FXML
    public Pane menuPane;
    @FXML
    public Pane infoPane;
    @FXML
    public Button start;
    @FXML
    public Button stop;
    @FXML
    public Button highscore;
    @FXML
    public Button changeNickname;
    @FXML
    public Button exit;
    @FXML
    public Button changeDifficulty;
    @FXML
    public TextField nickInput;
    @FXML
    public TextField numberInput;
    @FXML
    public Pane consolePane;
    @FXML
    public Label console;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the default setup of the the main stage. Sets the appropriate buttons to enabled or disabled and ends the timer.
     */
    public void restartState()
    {
        start.setDisable(false);
        highscore.setDisable(false);
        changeNickname.setDisable(false);
        changeDifficulty.setDisable(false);
        gamePane.setDisable(true);
        nickInput.setDisable(false);
        numberInput.setDisable(false);
        gamePane.getChildren().clear();
        endTimer();
        stop.setDisable(true);
        Log.log("info",getClass().getName() + " - Restarted state!");
    }

    /**
     * This starts to show the window which has been configured.
     */
    public void start()
    {
        RevealButton.setController(this);
        stage.show();
    }

    /**
     * Closes the current stage.
     */
    public void close()
    {
        stage.close();
    }

    /**
     * Starts the timer.
     */
    public void startTimer()
    {
        timer.startTimer();
        Log.log("info",getClass().getName() + " - Started timer!");
    }

    /**
     * Ends the timer.
     */
    public void endTimer()
    {
        timer.endTimer();
        Log.log("info",getClass().getName() + " - Stopped timer!");
    }

    /**
     * Add buttons to the game panel.
     */
    public void addButtonsToGamePane(){
        WindowConfigure.getInstance().addButtonsToGamePane(this);
    }

    /**
     * Updates the nickname in the options file.
     * @param name The string that contains the player's name.
     */
    public void updateNickname(String name)
    {
        updateFile.updateNickname(name);
    }
    /**
     * Updates the difficulty in the options file.
     * @param number The number of the bombs in the game panel.
     */
    public void updateDifficulty(Integer number)
    {
        updateFile.updateDifficulty(number);
    }

    /**
     * Sets a score scene to the current stage.
     */
    public void setHighscoreScene()
    {
        Log.log("info",getClass().getName() + " - Load scoreboard!");
        highscoreWindow.setScene(stage,stage.getScene());
        highscoreWindow.setup();
        stage.setScene(highscoreWindow.getScene());
    }

    /**
     * Handles the win state of the game. It updates the highscore file with the new score.
     * @param bombNumber the number of bombs that were in the game.
     */
    public void winState(Integer bombNumber)
    {
        endTimer();
        Long time = timer.getDeltaTime() / 1000;
        updateFile.updateHighScore(nickName.getText(),time,bombNumber);
        restartState();
    }
}
