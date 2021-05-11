package Control;


public class RevealButton {

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

    private static void checkIfWin()
    {
        if(revealedButtonNumber + bombNumber == buttonNumber && flagNumber == bombNumber)
        {
            //TODO: Handle win
        }
    }

}
