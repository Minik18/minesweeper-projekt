package Window;

import Control.Controller;
import Exception.UnknownButtonException;

import Button.ButtonGenerator;
import Option.DataOption.GameOptions;
import Option.DataOption.WindowOptions;
import Option.GeneralOptions;
import javafx.scene.layout.*;

import java.awt.*;

public class WindowConfigure {

    private static WindowConfigure instance = new WindowConfigure();
    private  ButtonGenerator buttonGenerator;
    private WindowOptions windowOptions = (WindowOptions) GeneralOptions.getInstance().getOptions().get("WindowOptions");
    private GameOptions gameOptions = (GameOptions) GeneralOptions.getInstance().getOptions().get("GameOptions");

    private WindowConfigure() {
    }

    public static WindowConfigure getInstance() {
        return instance;
    }

    public void configureGameWindow(Controller controller) {
        Dimension size;
        controller.gamePane.setDisable(true);
        controller.stop.setDisable(true);

        size = windowOptions.getInfoPanelSize();
        controller.menuPane.setPrefSize(size.width,size.height/2);
        controller.infoPane.setPrefSize(size.width,size.height/2);

        controller.bombNumber.setText(gameOptions.getDifficulty().toString());
        controller.nickName.setText(gameOptions.getNickName());

        configureMenuPanel(controller);

    }

    private void configureMenuPanel(Controller controller)
    {
        controller.start.setOnMouseClicked((action) ->
        {
            controller.gamePane.setDisable(false);
            controller.startTimer();
            controller.start.setDisable(true);
            controller.stop.setDisable(false);
            controller.highscore.setDisable(true);
            controller.changeNickname.setDisable(true);
            controller.changeDifficulty.setDisable(true);
            try {
                controller.addButtonsToGamePane();
            } catch (UnknownButtonException e) {
               //TODO: Print error message
            }
        });
        controller.exit.setOnMouseClicked(action ->
        {
            controller.close();
        });
        controller.stop.setOnMouseClicked(action ->
        {
            controller.start.setDisable(false);
            controller.highscore.setDisable(false);
            controller.changeNickname.setDisable(false);
            controller.changeDifficulty.setDisable(false);
            controller.gamePane.setDisable(true);
            controller.gamePane.getChildren().clear();
            controller.endTimer();
            controller.stop.setDisable(true);
        });
        controller.changeNickname.setOnMouseClicked(action ->
        {
            //TODO: Make new window where user can insert a new nickname and store it!
        });
        controller.changeDifficulty.setOnMouseClicked(action ->
        {
            //TODO: Make new window where user can insert a number of bombs and store it!
        });
        controller.highscore.setOnMouseClicked(action ->
        {
            //TODO: Make new window where user can see all of the high scores and possibly order them!
        });

    }
    public void addButtonsToGamePane(Controller controller) throws UnknownButtonException {
        controller.gamePane.getChildren().clear();
        Dimension size;
        size = windowOptions.getGamePanelSize();
        GridPane pane = new GridPane();
        pane.setPrefSize(size.width, size.height);
        buttonGenerator = new ButtonGenerator();
        pane = buttonGenerator.generateButtons(size, pane);
        controller.gamePane.getChildren().add(pane);
    }
}
