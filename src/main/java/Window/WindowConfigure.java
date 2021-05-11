package Window;

import Control.Controller;
import Exception.UnknownButtonException;

import Button.ButtonGenerator;
import Option.DataOption.ButtonOptions;
import Option.DataOption.GameOptions;
import Option.DataOption.WindowOptions;
import Option.GeneralOptions;
import javafx.scene.layout.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.awt.*;

public class WindowConfigure {

    private static WindowConfigure instance = new WindowConfigure();
    private  ButtonGenerator buttonGenerator;
    private WindowOptions windowOptions = (WindowOptions) GeneralOptions.getInstance().getOptions().get("WindowOptions");
    private GameOptions gameOptions = (GameOptions) GeneralOptions.getInstance().getOptions().get("GameOptions");
    private ButtonOptions buttonOptions = (ButtonOptions) GeneralOptions.getInstance().getOptions().get("ButtonOptions");

    private WindowConfigure() {
    }

    public static WindowConfigure getInstance() {
        return instance;
    }

    public void configureGameWindow(Controller controller) {
        Dimension size = windowOptions.getGamePanelSize();
        controller.gamePane.setPrefSize(size.width - buttonOptions.getSize().width,size.height - buttonOptions.getSize().height);
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
            controller.nickInput.setDisable(true);
            controller.numberInput.setDisable(true);
            controller.changeDifficulty.setDisable(true);
            controller.console.setText("Lets get started!...");
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
            controller.nickInput.setDisable(false);
            controller.numberInput.setDisable(false);
            controller.console.setText("Lets have a little break now!");
            controller.gamePane.getChildren().clear();
            controller.endTimer();
            controller.stop.setDisable(true);
        });
        controller.changeNickname.setOnMouseClicked(action ->
        {
            if(controller.nickInput.getText().isEmpty())
            {
                controller.nickInput.setText("Write something here first!");
            }else if(controller.nickInput.getText().equals("Write something here first!"))
            {
                controller.nickInput.setText("Write something here first!");
            }else
            {
                controller.nickName.setText(controller.nickInput.getText());
                controller.updateNickname(controller.nickInput.getText());
                controller.console.setText("Hello there " + controller.nickInput.getText() + " !");
                controller.nickInput.clear();
            }
        });
        controller.changeDifficulty.setOnMouseClicked(action ->
        {
            if(controller.numberInput.getText().isEmpty())
            {
                controller.numberInput.setText("Write something here first!");
            }else if(NumberUtils.isParsable(controller.numberInput.getText())){
                Integer inputNumber = Integer.parseInt(controller.numberInput.getText());
                Integer maxBombs = (windowOptions.getGamePanelSize().height * windowOptions.getGamePanelSize().width) /
                        (buttonOptions.getSize().height * buttonOptions.getSize().width);
                if(inputNumber <= 0 || inputNumber > maxBombs)
                {
                    controller.numberInput.setText("Write a number between 0 and "+ maxBombs +" !");
                }else
                {
                    controller.bombNumber.setText(inputNumber.toString());
                    controller.updateDifficulty(inputNumber);
                    gameOptions.setDifficulty(inputNumber);
                    if(inputNumber < 20)
                    {
                        controller.console.setText("Too easy..");
                    }else if(inputNumber < 50)
                    {
                        controller.console.setText("This is not even challenging!");
                    }else if(inputNumber < 80)
                    {
                        controller.console.setText("Okay, that looks normal!");
                    }else if(inputNumber < 120)
                    {
                        controller.console.setText("Finally a worthy opponent!");
                    }else{
                        controller.console.setText("Good luck with that!");
                    }
                    controller.numberInput.clear();
                }
            }else
            {
                controller.numberInput.setText("Write a number between 0 and 100!");
            }
        });
        controller.highscore.setOnMouseClicked(action ->
        {
            controller.setHighscoreScene();
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
