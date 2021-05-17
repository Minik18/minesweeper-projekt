package Button.InGame;

import Button.AbstractButton;
import Control.RevealButton;
import Logging.Log;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * In minesweeper, every tile which is not a bomb but has a neighboring bomb
 * tile is a number tile. This class represents that in-game object.
 */
public class NumberButton extends AbstractButton {
    private Integer score = 0;
    private String pathToNumbers = "Images/";

    /**
     * A method to be called when mouse right click is pressed. It disables this button to not be able to press it again,
     * sets the current number {@link Image} according to {@link NumberButton#score}. It also increments the number of
     * revealed tiles.
     */
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
                Log.log("error",getClass().getName() + " " + ignored.getMessage());
            }
            setDisable(true);
            setOpacity(1);
            RevealButton.incrementRevealedNumber();
        }
    }

    /**
     * Sets the {@link NumberButton#score} field to the given number.
     * @param number A number of bomb tiles around this tile.
     */
    public void changeScore(Integer number)
    {
        score = number;
    }
}
