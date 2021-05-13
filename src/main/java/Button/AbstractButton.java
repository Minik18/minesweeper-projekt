package Button;

import Control.RevealButton;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.Dimension;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public abstract class AbstractButton extends Button {
    private final String pathToFlag = "Images/flag.png";
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
            String filePath = null;
            try {
                filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(pathToFlag)),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                //TODO: Handle error
            }
            Image image = new Image(filePath);
            ImageView imageView = new ImageView(image);
            setGraphic(imageView);
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
