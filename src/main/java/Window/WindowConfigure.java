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

        List<AbstractButton> buttons = buttonGenerator.generateButtons(new Dimension(width,height));
        int index = 0;

        for(int i =0;i< width/30;i++)
        {
            for(int j = 0;j<height/30;j++)
            {
                pane.add(buttons.get(index),i,j);
                index++;
            }
        }

        scene = new Scene(pane);
        stage.setScene(scene);
        return stage;
    }
}
