package Button.InGame;

import Button.AbstractButton;
import Control.RevealButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;


public class NumberButton extends AbstractButton {
    private Integer score = 0;
    private String pathToNumbers = "file:src/main/resources/Images/";

    @Override
    public void onRightClickEvent() {
        if(!isDisable()) {
            Image image;
            pathToNumbers = pathToNumbers + score.toString() + ".png";
            image = new Image(pathToNumbers);
            ImageView imageView = new ImageView(image);
            setGraphic(imageView);
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
