package Option.DataOption;

import javafx.scene.image.Image;
import lombok.Data;

import java.awt.Dimension;

/**
 * This class contains the imported options related to the shown window.
 */
@Data
public class WindowOptions implements Option{

    private Dimension gamePanelSize;
    private boolean resizeable;
    private String title;
    private Dimension infoPanelSize;
    private Dimension menuPanelSize;
    private Dimension consolePanelSize;
    private Image image;
}
