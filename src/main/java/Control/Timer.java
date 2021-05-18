package Control;

/**
 * This class represents a timer that can be started and stopped at any time and it gives back the time between them.
 */
public class Timer {
    private Long startTime;
    private Long deltaTime;
    private Long endTime;
    private static final Timer instance = new Timer();

    private Timer(){}

    /**
     * Gets a {@link Timer} object.
     * @return An instantiated {@link Timer} object.
     */
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

    /**
     * Gets the elapsed time between the {@link Timer#startTime} and {@link Timer#endTime} methods.
     * @return A time elapsed between those methods.
     */
    public Long getDeltaTime() {
        return deltaTime;
    }
}
