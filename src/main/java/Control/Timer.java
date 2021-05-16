package Control;

/**
 * This class represents a timer that can be started and stopped at any time and it gives back the time between them.
 */
public class Timer {
    private Long startTime;
    private Long deltaTime;
    private Long endTime;
    private static Timer instance = new Timer();

    private Timer(){}

    public static Timer getInstance()
    {
        return instance;
    }

    /**
     * Gets the current time in milliseconds.
     */
    public void startTimer()
    {
        startTime = System.currentTimeMillis();
    }

    /**
     * Gets the current system time in milliseconds and sets teh time between this and the start time.
     */
    public void endTimer()
    {
        endTime = System.currentTimeMillis();
        deltaTime = endTime - startTime;
    }

    public Long getDeltaTime() {
        return deltaTime;
    }
}
