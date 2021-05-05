package Button.InGame;

import Button.AbstractButton;

public class NumberButton extends AbstractButton {

    private Integer score = 0;

    @Override
    public void onClickEvent() {

    }

    public void changeScore(Integer number)
    {
        score = number;
    }
}
