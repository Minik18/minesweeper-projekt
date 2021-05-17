package Exception;

/**
 * An exception represents an unknown button type. Thrown when given the unknown {@link Button.State} value.
 */
public class UnknownButtonException extends Exception{
    /**
     * A constructor which calls the {@link Exception} class's constructor to make this object.
     * @param message A reason why this exception had been thrown.
     */
    public UnknownButtonException(String message)
    {
        super(message);
    }
}
