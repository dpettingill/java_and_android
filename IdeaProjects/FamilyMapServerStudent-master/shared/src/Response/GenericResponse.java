package Response;

/**
 * helper class that all responses inherit from
 */
public class GenericResponse {
    /**
     * response message
     */
    public String message;
    /**
     * whether a successful response or not
     */
    public boolean success;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}