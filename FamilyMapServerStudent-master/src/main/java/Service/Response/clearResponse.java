package Service.Response;
import Service.response;

/**
 * serializes a clear response
 */
public class clearResponse extends response {
    /**
     * constructor for serializing clear response data
     * @param message string
     * @param success boolean
     */
    public clearResponse(String message, boolean success)
    {
        this.message = message;
        this.success = success;
    }
}
