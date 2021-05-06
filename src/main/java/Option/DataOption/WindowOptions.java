package Option.DataOption;

import lombok.Data;

import java.awt.*;

@Data
public class WindowOptions implements Option{

    private Dimension gamePanelSize;
    private boolean resizeable;
    private String title;
    private Dimension infoPanelSize;
}
