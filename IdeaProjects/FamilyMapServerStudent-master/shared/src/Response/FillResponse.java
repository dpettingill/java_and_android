package Response;

/**
 * serializes fillResponse data
 */
public class FillResponse extends GenericResponse {
    /**
     * constructor for serializing fill response data
     * @param message string
     * @param success boolean
     */
    public FillResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
