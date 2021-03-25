package Response;

/**
 * serializes load response data
 */
public class LoadResponse extends GenericResponse {
    /**
     * constructor for serializing load response data
     * @param message string
     * @param success boolean
     */
    public LoadResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
