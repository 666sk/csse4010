package researchsim.util;

/**
 * Exception thrown when attempting to get the entity at an empty tile.
 */
public class NoSuchEntityException extends Exception {

    /**
     * Constructs a normal NoSuchEntityException with no error message.
     */
    public NoSuchEntityException() {
        super();
    }

    /**
     * Constructs a NoSuchEntityException that contains a helpful messgae
     * detailing why the exception occurred
     * @param message detail message
     */
    public NoSuchEntityException(String message) {
        super(message);
    }
}
