package Button;

import Button.InGame.BombButton;
import Button.InGame.EmptyButton;
import Button.InGame.NumberButton;
import Exception.UnknownButtonException;

/**
 * This class represents a button creator object.
 */
public class ButtonFactory {

    /**
     * A method to instantiate an appropriate kind of button.
     * @param state a button type.
     * @return an {@link AbstractButton} as a specified type of button.
     * @throws UnknownButtonException when given an unknown type.
     */
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
