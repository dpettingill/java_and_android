package Service.Response;
import Service.response;

/**
 * serializes fillResponse data
 */
public class fillResponse extends response {
    /**
     * constructor for serializing fill response data
     * @param message string
     * @param success boolean
     */
    public fillResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
