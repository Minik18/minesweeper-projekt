package Button;

import Control.RevealButton;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.Dimension;

public abstract class AbstractButton extends Button {
    private final String pathToFlag = "file:src/main/resources/Images/flag.png";
    private Boolean hasFlag = false;

    public abstract void onRightClickEvent();
    public void setSize(Dimension size)
    {
        this.setPrefSize(size.getWidth(),size.getHeight());
        this.setMaxSize(size.getWidth(),size.getHeight());
        this.setMinSize(size.getWidth(),size.getHeight());

    }
    public void onLeftClickEvent()
    {
        if(!hasFlag)
        {
            Image image = new Image(pathToFlag);
            ImageView imageView = new ImageView(image);
            this.setGraphic(imageView);
            hasFlag = true;
            RevealButton.incrementFlagNumber();
        }else
        {
            this.setGraphic(null);
            hasFlag = false;
            RevealButton.decrementFlagNumber();
        }
    }
}
