package Button.InGame;

import Button.AbstractButton;
import Control.RevealButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class NumberButton extends AbstractButton {
    private Integer score = 0;
    private String pathToNumbers = "Images/";

    @Override
    public void onRightClickEvent() {
        if(!isDisable()) {

            Image image;
            pathToNumbers = pathToNumbers + score.toString() + ".png";
            try {
                String filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(pathToNumbers)),"UTF-8");
                image = new Image(filePath);
                ImageView imageView = new ImageView(image);
                setGraphic(imageView);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                //TODO: Handle Error
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
