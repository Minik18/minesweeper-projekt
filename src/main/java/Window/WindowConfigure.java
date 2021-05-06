package Window;

import Exception.UnknownButtonException;
import Button.AbstractButton;

import Control.ButtonGenerator;
import Option.DataOption.WindowOptions;
import Option.GeneralOptions;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.Dimension;

public class WindowConfigure {

    private Scene scene;
    private static WindowConfigure  instance= new WindowConfigure();
    private final ButtonGenerator buttonGenerator = ButtonGenerator.getInstance();
    private WindowOptions windowOptions;
    private WindowConfigure(){};

    public static  WindowConfigure getInstance()
    {
        return instance;
    }

    public Stage configureGamePanel(Stage stage) throws UnknownButtonException {
        windowOptions = (WindowOptions) GeneralOptions.getInstance().getOptions().get("WindowOptions");
        Dimension size;
        size = windowOptions.getGamePanelSize();
        GridPane pane = new GridPane();
        pane.setPrefSize(size.width,size.height);

        pane = buttonGenerator.generateButtons(size,pane);


        scene = new Scene(pane);
        stage.setScene(scene);
        return stage;
    }
}
