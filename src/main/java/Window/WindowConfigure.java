package Window;

import Control.Controller;

import Button.ButtonGenerator;
import Logging.Log;
import Option.DataOption.ButtonOptions;
import Option.DataOption.GameOptions;
import Option.DataOption.WindowOptions;
import Option.GeneralOptions;
import javafx.scene.layout.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.awt.*;

/**
 * This class is responsible for the configuration of the main scene
 */
public class WindowConfigure {

    private static final WindowConfigure instance = new WindowConfigure();
    private final WindowOptions windowOptions = (WindowOptions) GeneralOptions.getInstance().getOptions().get("WindowOptions");
    private final GameOptions gameOptions = (GameOptions) GeneralOptions.getInstance().getOptions().get("GameOptions");
    private final ButtonOptions buttonOptions = (ButtonOptions) GeneralOptions.getInstance().getOptions().get("ButtonOptions");

    private WindowConfigure() {
    }

    /**
     * Gets a {@link WindowConfigure} object.
     * @return An instantiated {@link WindowConfigure} object.
     */
    public static WindowConfigure getInstance() {
        return instance;
    }

    /**
     * Configures the game panel and calls the {@link WindowConfigure#configureMenuPanel(Controller)}
     * method to set up the remaining parts of the scene.
     * @param controller A class containing fxml elements of the main scene.
     */
    public void configureGameWindow(Controller controller) {
        Dimension size = windowOptions.getGamePanelSize();
        controller.gamePane.setPrefSize(size.width ,size.height );
        controller.gamePane.setDisable(true);
        controller.stop.setDisable(true);

        size = windowOptions.getInfoPanelSize();
        controller.infoPane.setPrefSize(size.width,size.height);

        size = windowOptions.getMenuPanelSize();
        controller.menuPane.setPrefSize(size.width,size.height);

        size = windowOptions.getConsolePanelSize();
        controller.consolePane.setPrefSize(size.width,size.height);

        controller.bombNumber.setText(gameOptions.getDifficulty().toString());
        controller.nickName.setText(gameOptions.getNickName());

        Log.log("info", getClass().getName() + " - Successfully configured the game panel!");
        configureMenuPanel(controller);

    }

    /**
     * Adds button to the game panel on the main scene.
     * @param controller  A class containing fxml elements of the main scene.
     */
    public void addButtonsToGamePane(Controller controller) {
        ButtonGenerator buttonGenerator;
        controller.gamePane.getChildren().clear();
        Dimension size;
        size = windowOptions.getGamePanelSize();
        GridPane pane = new GridPane();
        pane.setPrefSize(size.width, size.height);
        buttonGenerator = new ButtonGenerator();
        pane = buttonGenerator.generateButtons(size, pane);
        controller.gamePane.getChildren().add(pane);
        Log.log("info", getClass().getName() + " - Successfully added buttons to game panel!");
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
            controller.addButtonsToGamePane();
        });
        controller.exit.setOnMouseClicked(action -> controller.close());
        controller.stop.setOnMouseClicked(action ->
        {
            controller.restartState();
            controller.console.setText("Lets have a little break now!");
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
                int inputNumber = Integer.parseInt(controller.numberInput.getText());
                int maxBombs = (windowOptions.getGamePanelSize().height * windowOptions.getGamePanelSize().width) /
                        (buttonOptions.getSize().height * buttonOptions.getSize().width);
                if(inputNumber <= 0 || inputNumber > maxBombs)
                {
                    controller.numberInput.setText("Write a number between 0 and "+ maxBombs +" !");
                }else
                {
                    controller.bombNumber.setText(Integer.toString(inputNumber));
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
        controller.highscore.setOnMouseClicked(action -> controller.setHighscoreScene());
        Log.log("info", getClass().getName() + " - Successfully configured the menu panel!");

    }


}
