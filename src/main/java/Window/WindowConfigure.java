package Window;

import Exception.UnknownButtonException;

import Button.ButtonGenerator;
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

    public Scene configureGameWindow(Stage stage) throws UnknownButtonException {
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
        Color green = Color.color(0.3,1,0.2);
        Color red = Color.color(1,0.2,0.2);
        Color yellow = Color.color(0.5,0,1);

        GameOptions gameOptions = (GameOptions) GeneralOptions.getInstance().getOptions().get("GameOptions");
        //Info label
        vBox.getChildren().add(setLabel(Font.font("Arial",FontWeight.BOLD,20),"Info Panel",yellow));
        //Empty label
        vBox.getChildren().add(emptyLabel);
        //Player label
        vBox.getChildren().add(setLabel(Font.font("Arial",FontWeight.BOLD,20),"Player:",green));
        //Nickname label
        vBox.getChildren().add(setLabel(Font.font("Arial",20),gameOptions.getNickName(),red));
        //Empty label
        emptyLabel = new Label();
        vBox.getChildren().add(emptyLabel);
        //Number of Bombs label
        vBox.getChildren().add(setLabel(Font.font("Arial",FontWeight.BOLD,15),"Number of Bombs:",green));
        //Actual number of bombs label
        vBox.getChildren().add(setLabel(Font.font("Arial",20),gameOptions.getDifficulty().toString(),red));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));
        box.getChildren().add(vBox);
        return box;
    }
    private Label setLabel(Font font, String text, Color color)
    {
        Label label = new Label();
        label.setFont(font);
        label.setText(text);
        label.setTextFill(color);
        return label;
    }
}
