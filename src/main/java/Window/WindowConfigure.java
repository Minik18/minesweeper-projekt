package Window;

import Exception.UnknownButtonException;
import Button.AbstractButton;

import Control.ButtonGenerator;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;
import java.awt.Dimension;

public class WindowConfigure {

    private Scene scene;
    private static WindowConfigure  instance= new WindowConfigure();
    private final ButtonGenerator buttonGenerator = ButtonGenerator.getInstance();

    private WindowConfigure(){};

    public static  WindowConfigure getInstance()
    {
        return instance;
    }

    public Stage configureGameWindow(Stage stage,int width, int height) throws UnknownButtonException {
        GridPane pane = new GridPane();

        pane = buttonGenerator.generateButtons(new Dimension(width,height),pane);


        scene = new Scene(pane);
        stage.setScene(scene);
        return stage;
    }
}
