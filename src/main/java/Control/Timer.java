package Control;

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

    public void startTimer()
    {
        startTime = System.currentTimeMillis();
    }
    public void endTimer()
    {
        endTime = System.currentTimeMillis();
        deltaTime = endTime - startTime;
    }

    public Long getDeltaTime() {
        return deltaTime;
    }
}
