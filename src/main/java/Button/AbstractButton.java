package Button;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

import java.awt.*;

public abstract class AbstractButton extends Button {
    public abstract void onClickEvent();
    public void setSize(Dimension size)
    {
        this.setPrefSize(size.getWidth(),size.getHeight());
        this.setMaxSize(size.getWidth(),size.getHeight());
        this.setMinSize(size.getWidth(),size.getHeight());

    }
}
