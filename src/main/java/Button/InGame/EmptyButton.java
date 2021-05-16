package Button.InGame;

import Button.AbstractButton;
import Button.ButtonGenerator;
import Control.RevealButton;
import Logging.Log;
import java.util.ArrayList;

/**
 * In minesweeper, every tile which is not a bomb nor has a neighboring bomb tile is an empty tile. This class represent
 * that.
 */
public class EmptyButton extends AbstractButton {
    /**
     * A method to be called when mouse right click is pressed. It disables this button to not be able to press it again,
     * collects all of the surrounding empty and number tiles and call the {@link AbstractButton#onRightClickEvent()}
     * method on them. Increments the revealed number of tiles for each revealed tile.
     */
    @Override
    public void onRightClickEvent() {
        if(!isDisable())
        {
            Log.log("info",getClass().getName() + " - Clicked Empty button!");
            RevealButton.incrementRevealedNumber();
            this.setGraphic(null);
            AbstractButton[][] buttonMatrix = ButtonGenerator.getButtonMatrix();
            setDisable(true);

            Integer[] objIndex = new Integer[]{0,0};

            for(int i = 0; i < buttonMatrix.length -1;i++)
            {
                for(int j = 0; j < buttonMatrix[i].length-1; j++)
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
                        if(buttonMatrix[i + objIndex[0]][j + objIndex[1]].getClass().equals(this.getClass())
                                || buttonMatrix[i + objIndex[0]][j + objIndex[1]].getClass().equals(NumberButton.class))
                        {
                            unlockList.add(buttonMatrix[i + objIndex[0]][j + objIndex[1]]);
                        }
                    } catch (Exception ignored) {
                        //Ignored exception because at the side buttons ArrayIndexOutOfBoundsException will be thrown
                        //every time because of the check of the surrounding buttons
                    }
                }
            }
            unlockList.forEach(AbstractButton::onRightClickEvent);
        }
    }
}
