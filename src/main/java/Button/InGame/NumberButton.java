package Button.InGame;

import Button.AbstractButton;
import Control.RevealButton;
import Logging.Log;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class NumberButton extends AbstractButton {
    private Integer score = 0;
    private String pathToNumbers = "Images/";

    @Override
    public void onRightClickEvent() {
        if(!isDisable()) {
            Log.log("info",getClass().getName() + " - Clicked Number button!");

            Image image;
            pathToNumbers = pathToNumbers + score.toString() + ".png";
            try {
                String filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(pathToNumbers)),"UTF-8");
                image = new Image(filePath);
                ImageView imageView = new ImageView(image);
                setGraphic(imageView);
            } catch (UnsupportedEncodingException ignored) {
                //It cannot happen because the encoding will always be UTF-8 which is a valid encoding format.
                Log.log("error",getClass().getName() + " " + ignored.getCause().getMessage());
            }
            setDisable(true);
            setOpacity(1);
            RevealButton.incrementRevealedNumber();
        }
    }

    public void changeScore(Integer number)
    {
        score = number;
    }
}
