package Control;

public class RevealButton {

    private static Controller controller;
    private static Integer revealedButtonNumber = 0;
    private static Integer bombNumber = 0;
    private static Integer buttonNumber = 0;
    private static Integer flagNumber = 0;

    public static void incrementRevealedNumber()
    {
        revealedButtonNumber++;
        checkIfWin();
    }
    public static void incrementFlagNumber()
    {
        flagNumber++;
        checkIfWin();
    }
    public static void decrementFlagNumber()
    {
        flagNumber--;
    }
    public static void setBombNumber(Integer num)
    {
        bombNumber = num;
    }
    public static void setButtonNumber(Integer num)
    {
        buttonNumber = num;
    }
    public static void restartReveledCounter()
    {
        revealedButtonNumber = 0;
    }
    public static void restartFlagNumber()
    {
        flagNumber = 0;
    }

    public static void setController(Controller l)
    {
        controller = l;
    }

    private static void checkIfWin()
    {
        if(revealedButtonNumber + bombNumber == buttonNumber && flagNumber == bombNumber)
        {
            restartFlagNumber();
            restartReveledCounter();
            controller.console.setText("Congratulation! You made it!");
        }
    }
    public static void lose()
    {
        restartFlagNumber();
        restartReveledCounter();
        controller.console.setText("Do not worry! Better luck next time!");
        controller.restartState();
    }

}
