package Button;

import Control.RevealButton;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.Dimension;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * This abstract class defines an in-game playable button.
 */
public abstract class AbstractButton extends Button {
    private final String pathToFlag = "Images/flag.png";
    private Boolean hasFlag = false;

    /**
     * An abstract method to be called when right click is pressed on mouse.
     */
    public abstract void onRightClickEvent();

    /**
     * A method to set the max,min and pref size of the button.
     * @param size the size that contains the width and height option of a button.
     */
    public void setSize(Dimension size)
    {
        this.setPrefSize(size.getWidth(),size.getHeight());
        this.setMaxSize(size.getWidth(),size.getHeight());
        this.setMinSize(size.getWidth(),size.getHeight());

    }

    /**
     * A method to be called when left click is pressed on mouse. If the current button's {@link AbstractButton#hasFlag} field
     * is false, then sets a  flag graphic element to this object, else removes the flag graphic element.
     */
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
