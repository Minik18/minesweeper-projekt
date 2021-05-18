package Score;

import de.saxsys.javafx.test.JfxRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(JfxRunner.class)
public class TestImportScore {

    private final ImportScore instance = ImportScore.getInstance();
    private final String emptyFile = "testHighscoreEmpty.json";
    private final String oneElementFile = "testHighscore1.json";

    @Test
    public void testGetScoresEmpty()
    {

        List<Score> expectedEmpty = new ArrayList<>();

        instance.importScores(emptyFile);
        List<Score> resultEmpty = instance.getScores();

        Assert.assertEquals(expectedEmpty,resultEmpty);
    }
    @Test
    public void testGetScoresOneElement()
    {
        List<Score> exceptedOne = new ArrayList<>();
        Score score = new Score();
        score.setScore(0.0);
        score.setName("Minik");
        score.setTime(13L);
        score.setBombNumber(10);
        score.setPlace(1);
        exceptedOne.add(score);

        instance.importScores(oneElementFile);

        List<Score> resultOne = instance.getScores();

        Assert.assertEquals(exceptedOne.get(0),resultOne.get(0));
    }

}
