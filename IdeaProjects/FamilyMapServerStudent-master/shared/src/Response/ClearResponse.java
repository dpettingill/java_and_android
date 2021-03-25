package Response;

/**
 * serializes a clear response
 */
public class ClearResponse extends GenericResponse {
    /**
     * constructor for serializing clear response data
     * @param message string
     * @param success boolean
     */
    public ClearResponse(String message, boolean success)
    {
        this.message = message;
        this.success = success;
    }
}
