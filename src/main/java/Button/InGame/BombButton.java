package Button.InGame;

import Button.AbstractButton;
import Control.RevealButton;
import Logging.Log;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * In minesweeper, a bomb tile has a bomb and when its pressed, the player loses the game.
 */
public class BombButton extends AbstractButton {

    private final String path = "Images/bomb.png";

    /**
     * A method to be called when mouse right click is pressed. It disables this button to not be able to press it again,
     * and calls the {@link RevealButton#lose()} method to lose the game.
     */
    @Override
    public void onRightClickEvent() {
        Image image;
        if(!isDisable()) {
            String filePath = "";
            try {
                filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(path)),"UTF-8");
            } catch (UnsupportedEncodingException ignored) {
                //It cannot happen because the encoding will always be UTF-8 which is a valid encoding format.
                Log.log("error",getClass().getName() + " " + ignored.getCause().getMessage());
            }
            image = new Image(filePath);
            ImageView imageView = new ImageView(image);
            setGraphic(imageView);
            setDisable(true);
            setOpacity(1);
            Log.log("info",getClass().getName() + " - Clicked Bomb button!");
            RevealButton.lose();
        }
    }
}
