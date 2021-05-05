package Control;

import Button.AbstractButton;
import Button.InGame.BombButton;
import Button.InGame.EmptyButton;
import Button.InGame.NumberButton;
import Button.State;
import Exception.UnknownButtonException;

public class ButtonFactory {

    public AbstractButton getButton(State state) throws UnknownButtonException {
        switch(state) {
            case BOMB -> {
                return new BombButton();
            }
            case EMPTY -> {
                return new EmptyButton();
            }
            case NUMBER -> {
                return new NumberButton();
            }
            default -> throw new UnknownButtonException("Unknown button state: " + state.toString());
        }
    }

}
