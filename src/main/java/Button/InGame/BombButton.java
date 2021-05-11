package Button.InGame;

import Button.AbstractButton;
import Control.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BombButton extends AbstractButton {

    private final String path = "file:src/main/resources/Images/bomb.png";
    private Image image;

    @Override
    public void onRightClickEvent() {
        if(!isDisable()) {
            image = new Image(path);
            ImageView imageView = new ImageView(image);
            setGraphic(imageView);
            setDisable(true);
            setOpacity(1);
            //TODO: Handle game Over
        }
    }
}
