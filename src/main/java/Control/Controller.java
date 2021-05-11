package Control;

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
    }
    public void endTimer()
    {
        timer.endTimer();
    }
    public void addButtonsToGamePane() throws UnknownButtonException {
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
        highscoreWindow.setScene(stage,stage.getScene());
        highscoreWindow.setup();
        stage.setScene(highscoreWindow.getScene());
    }
}
