package Button;

import Button.InGame.BombButton;
import Button.InGame.EmptyButton;
import Button.InGame.NumberButton;
import Exception.UnknownButtonException;

import de.saxsys.javafx.test.JfxRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
public class TestButtonFactory {

    private final ButtonFactory instance = new ButtonFactory();

    @Test
    public void testGetButton() {
        try {
            AbstractButton bombButton =  instance.getButton(State.BOMB);
            AbstractButton numberButton =  instance.getButton(State.NUMBER);
            AbstractButton emptyButton =  instance.getButton(State.EMPTY);
            Assert.assertTrue(bombButton instanceof BombButton);
            Assert.assertTrue(numberButton instanceof NumberButton);
            Assert.assertTrue(emptyButton instanceof EmptyButton);
        } catch (UnknownButtonException e) {
            Assert.assertTrue(false);
        }
    }

}
