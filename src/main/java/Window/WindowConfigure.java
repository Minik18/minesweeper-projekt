package Window;

import Exception.UnknownButtonException;

import Control.ButtonGenerator;
import Option.DataOption.GameOptions;
import Option.DataOption.WindowOptions;
import Option.GeneralOptions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.awt.*;

public class WindowConfigure {

    private static WindowConfigure  instance= new WindowConfigure();
    private final ButtonGenerator buttonGenerator = ButtonGenerator.getInstance();
    private WindowOptions windowOptions = (WindowOptions) GeneralOptions.getInstance().getOptions().get("WindowOptions");
    private WindowConfigure(){};

    public static  WindowConfigure getInstance()
    {
        return instance;
    }

    public Scene configureGamePanel(Stage stage) throws UnknownButtonException {
        Dimension size;
        size = windowOptions.getGamePanelSize();
        GridPane pane = new GridPane();
        pane.setPrefSize(size.width,size.height);

        pane = buttonGenerator.generateButtons(size,pane);

        return new Scene(configureInfoPanel(new HBox(pane)));
    }

    private HBox configureInfoPanel(HBox box)
    {
        VBox vBox = new VBox();
        Label emptyLabel = new Label();
        GameOptions gameOptions = (GameOptions) GeneralOptions.getInstance().getOptions().get("GameOptions");
        //Info label
        vBox.getChildren().add(setLabel(Font.font("Arial",FontWeight.BOLD,20),"Info Panel"));
        //Empty label
        vBox.getChildren().add(emptyLabel);
        //Player label
        vBox.getChildren().add(setLabel(Font.font("Arial",FontWeight.BOLD,20),"Player:"));
        //Nickname label
        vBox.getChildren().add(setLabel(Font.font("Arial",20),gameOptions.getNickName()));
        //Empty label
        emptyLabel = new Label();
        vBox.getChildren().add(emptyLabel);
        //Number of Bombs label
        vBox.getChildren().add(setLabel(Font.font("Arial",FontWeight.BOLD,15),"Number of Bombs:"));
        //Actual number of bombs label
        vBox.getChildren().add(setLabel(Font.font("Arial",20),gameOptions.getDifficulty().toString()));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));
        box.getChildren().add(vBox);
        return box;
    }
    private Label setLabel(Font font, String text)
    {
        Label label = new Label();
        label.setFont(font);
        label.setText(text);
        return label;
    }
}
