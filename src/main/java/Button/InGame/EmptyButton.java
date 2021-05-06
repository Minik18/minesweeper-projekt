package Button.InGame;

import Button.AbstractButton;
import Control.ButtonGenerator;

import java.util.ArrayList;

public class EmptyButton extends AbstractButton {
    @Override
    public void onClickEvent() {
        if(!isDisable())
        {
            AbstractButton[][] buttonMatrix = ButtonGenerator.getInstance().getButtonMatrix();
            setDisable(true);

            Integer[] objIndex = new Integer[2];

            for(int i = 0; i < buttonMatrix.length ;i++)
            {
                for(int j = 0; j < buttonMatrix[i].length; j++)
                {
                    if(buttonMatrix[i][j].equals(this))
                    {
                        objIndex[0] = i;
                        objIndex[1] = j;
                        break;
                    }
                }
            }

            ArrayList<AbstractButton> unlockList = new ArrayList<>();

            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    try {
                        if (buttonMatrix[objIndex[0] + i][objIndex[1] + j].getClass().equals(this.getClass())
                                || buttonMatrix[objIndex[0] + i][objIndex[1] + j].getClass().equals(NumberButton.class)) {
                            unlockList.add(buttonMatrix[objIndex[0] + i][objIndex[1] + j]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        //TODO: Print error message
                    }r
                }
            }
            unlockList.forEach(AbstractButton::onClickEvent);
        }
    }
}
