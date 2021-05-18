package Option;

import Option.DataOption.Option;
import de.saxsys.javafx.test.JfxRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

@RunWith(JfxRunner.class)
public class TestImportOptions {

    private final GeneralOptions generalOptionsInstance = GeneralOptions.getInstance();
    private final String fileName = "testOptions.json";

    @Test
    public void testImportOptions()
    {
        generalOptionsInstance.setOptions(fileName);
        Map<String, Option> map = generalOptionsInstance.getOptions();

        Set<String> expectedKeys = Set.of("GameOptions","ButtonOptions","WindowOptions");

        Assert.assertEquals(3, map.size());
        Assert.assertEquals(map.keySet(),expectedKeys);

        for(Option result : map.values())
        {
            Assert.assertTrue(result instanceof Option);
        }

    }

}
