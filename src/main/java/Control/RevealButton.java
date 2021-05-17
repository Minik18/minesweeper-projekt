package Control;

import Button.State;
import Logging.Log;

/**
 * This class represents an object which keeps tracking the number of revealed and flag tiles.
 */
public class RevealButton {

    private static Controller controller;
    private static Integer revealedButtonNumber = 0;
    private static Integer bombNumber = 0;
    private static Integer buttonNumber = 0;
    private static Integer flagNumber = 0;

    /**
     * Increment the revealed number of tiles by 1.
     */
    public static void incrementRevealedNumber()
    {
        revealedButtonNumber++;
        checkIfWin();
    }
    /**
     * Increment the number of flag tiles by 1.
     */
    public static void incrementFlagNumber()
    {
        flagNumber++;
        checkIfWin();
    }
    /**
     * Decrement the number of flag tiles by 1.
     */
    public static void decrementFlagNumber()
    {
        flagNumber--;
    }

    /**
     * Sets the number of bombs value.
     * @param num The number of {@link State#BOMB} tiles.
     */
    public static void setBombNumber(Integer num)
    {
        bombNumber = num;
    }
    /**
     * Sets the number of all the tiles in the panel..
     * @param num The number of all tiles.
     */
    public static void setButtonNumber(Integer num)
    {
        buttonNumber = num;
    }
    /**
     * Sets the revealed number of tiles to 0.
     */
    public static void restartReveledCounter()
    {
        revealedButtonNumber = 0;
    }
    /**
     * Sets the number of flag tiles to 0.
     */
    public static void restartFlagNumber()
    {
        flagNumber = 0;
    }
    /**
     * Sets the controller that contains the main stage elements.
     */
    public static void setController(Controller l)
    {
        controller = l;
    }

    /**
     * Checks if the player has lost the game. The player is lost the game when a bomb tile is pressed.
     * If yes, restarts the tracked numbers and calls {@link Controller#restartState()} method.
     */
    public static void lose()
    {
        Log.log("info",RevealButton.class.getName() + " - Lost game!");
        restartFlagNumber();
        restartReveledCounter();
        controller.console.setText("Do not worry! Better luck next time!");
        controller.restartState();
    }

    private static void checkIfWin()
    {
        if(revealedButtonNumber + bombNumber == buttonNumber && flagNumber == bombNumber)
        {
            Log.log("info",RevealButton.class.getName() + " - Won game!");
            restartFlagNumber();
            restartReveledCounter();
            controller.console.setText("Congratulation! You made it!");
            controller.winState(bombNumber);
        }
    }


}
