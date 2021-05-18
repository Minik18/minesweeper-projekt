package Option;

import Option.DataOption.ButtonOptions;
import Option.DataOption.GameOptions;
import Option.DataOption.Option;
import Option.DataOption.WindowOptions;
import de.saxsys.javafx.test.JfxRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.awt.*;
import java.util.Map;

@RunWith(JfxRunner.class)
public class TestGeneralOptions {

    private final GeneralOptions generalOptionsInstance = GeneralOptions.getInstance();
    private final String fileName = "testOptions.json";

    @Test
    public void testWindowOptions()
    {
        generalOptionsInstance.setOptions(fileName);
        Map<String, Option> map = generalOptionsInstance.getOptions();

        WindowOptions result = (WindowOptions) map.get("WindowOptions");

        WindowOptions excepted = new WindowOptions();

        excepted.setTitle("Minesweeper Game");
        excepted.setGamePanelSize(new Dimension(330,510));
        excepted.setInfoPanelSize(new Dimension(250,180));
        excepted.setConsolePanelSize(new Dimension(250,90));
        excepted.setMenuPanelSize(new Dimension(250,330));
        excepted.setResizeable(false);

        Assert.assertEquals(result.getConsolePanelSize(),excepted.getConsolePanelSize());
        Assert.assertEquals(result.getInfoPanelSize(),excepted.getInfoPanelSize());
        Assert.assertEquals(result.getMenuPanelSize(),excepted.getMenuPanelSize());
        Assert.assertEquals(result.getGamePanelSize(),excepted.getGamePanelSize());
        Assert.assertEquals(result.getTitle(),excepted.getTitle());
        Assert.assertEquals(result.isResizeable(),excepted.isResizeable());

    }
    @Test
    public void testGameOptions()
    {
        generalOptionsInstance.setOptions(fileName);
        Map<String, Option> map = generalOptionsInstance.getOptions();

        GameOptions result = (GameOptions) map.get("GameOptions");

        GameOptions excepted = new GameOptions();

        excepted.setDifficulty(40);
        excepted.setNickName("default");

        Assert.assertEquals(excepted,result);

    }
    @Test
    public void testButtonOptions()
    {
        generalOptionsInstance.setOptions(fileName);
        Map<String, Option> map = generalOptionsInstance.getOptions();

        ButtonOptions result = (ButtonOptions) map.get("ButtonOptions");

        ButtonOptions excepted = new ButtonOptions();

        excepted.setSize(new Dimension(30,30));

        Assert.assertEquals(excepted,result);

    }


}
