package Score;

import de.saxsys.javafx.test.JfxRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(JfxRunner.class)
public class TestImportScore {

    private ImportScore instance = ImportScore.getInstance();

    @Test
    public void testGetScores()
    {
        List<Score> expected = new ArrayList<>();

        List<Score> result = instance.getScores();

        Assert.assertEquals(expected,result);

    }

}
