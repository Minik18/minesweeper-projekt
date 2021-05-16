package Exception;

/**
 * An exception represents an unknown button type. Thrown when given the unknown {@link Button.State} value.
 */
public class UnknownButtonException extends Exception{
    public UnknownButtonException(String message)
    {
        super(message);
    }
}
