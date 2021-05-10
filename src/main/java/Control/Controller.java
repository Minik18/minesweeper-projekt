package Control;

import Option.UpdateFile;
import Window.HighscoreWindow;
import Window.WindowConfigure;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import Exception.UnknownButtonException;


public class Controller {

    private Timer timer = Timer.getInstance();
    private UpdateFile updateFile = UpdateFile.getInstance();
    private HighscoreWindow highscoreWindow = HighscoreWindow.getInstance();
    private Stage stage;
    @FXML
    public AnchorPane gamePane;
    @FXML
    public Label nickName;
    @FXML
    public Label bombNumber;
    @FXML
    public AnchorPane menuPane;
    @FXML
    public AnchorPane infoPane;
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

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void start()
    {
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
