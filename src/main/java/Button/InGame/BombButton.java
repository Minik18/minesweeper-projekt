package Button.InGame;

import Button.AbstractButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BombButton extends AbstractButton {

    private final String path = "file:src/main/resources/Images/bomb.png";
    private Image image;

    @Override
    public void onClickEvent() {
        if(!isDisable()) {
            image = new Image(path, 30, 30, true, true);
            ImageView imageView = new ImageView(image);
            setGraphic(imageView);
            setDisable(true);
            setOpacity(1);
        }
    }
}
