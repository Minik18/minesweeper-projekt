package Control;


public class RevealButton {

    private static Integer revealedButtonNumber = 0;
    private static Integer bombNumber = 0;
    private static Integer buttonNumber = 0;

    public static void incrementRevealedNumber()
    {
        revealedButtonNumber++;
    }
    public static void setBombNumber(Integer num)
    {
        bombNumber = num;
    }
    public static void setButtonNumber(Integer num)
    {
        buttonNumber = num;
    }

}
