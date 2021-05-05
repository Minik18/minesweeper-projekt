package Option.DataOption;

import lombok.Data;

import java.awt.*;

@Data
public class WindowOptions implements Option{

    private Dimension size;
    private boolean resizeable;
    private String title;

}
