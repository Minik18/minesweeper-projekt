package Button;

import javafx.scene.control.Button;

import java.awt.*;

public abstract class AbstractButton extends Button {
    public abstract void onClickEvent();
    public void setSize(Dimension size)
    {
        this.setPrefSize(size.getWidth(),size.getHeight());
    }
}
