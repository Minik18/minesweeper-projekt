package Timer;

import Control.Timer;
import de.saxsys.javafx.test.JfxRunner;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
public class TestTimer {

    private final Timer instance = Timer.getInstance();

    @Test
    public void testTimerStartEndDeltaTimeMethods()
    {
        instance.startTimer();
        instance.endTimer();
        Long deltaTime = instance.getDeltaTime();

        Assert.isNonEmpty(deltaTime);

    }
}
