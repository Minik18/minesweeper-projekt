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

import Exception.UnknownButtonException;


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

    public void start()
    {
        RevealButton.setController(this);
        stage.show();
    }
    public void close()
    {
        stage.close();
    }
    public void startTimer()
    {
        timer.startTimer();
        Log.log("info",getClass().getName() + " - Started timer!");
    }
    public void endTimer()
    {
        timer.endTimer();
        Log.log("info",getClass().getName() + " - Stopped timer!");
    }
    public void addButtonsToGamePane(){
        WindowConfigure.getInstance().addButtonsToGamePane(this);
    }
    public void updateNickname(String name)
    {
        updateFile.updateNickname(name);
    }
    public void updateDifficulty(Integer number)
    {
        updateFile.updateDifficulty(number);
    }
    public void setHighscoreScene()
    {
        Log.log("info",getClass().getName() + " - Load scoreboard!");
        highscoreWindow.setScene(stage,stage.getScene());
        highscoreWindow.setup();
        stage.setScene(highscoreWindow.getScene());
    }
    public void winState(Integer bombNumber)
    {
        endTimer();
        Long time = timer.getDeltaTime() / 1000;
        updateFile.updateHighScore(nickName.getText(),time,bombNumber, (double) (bombNumber * bombNumber / time));
        restartState();
    }
}
