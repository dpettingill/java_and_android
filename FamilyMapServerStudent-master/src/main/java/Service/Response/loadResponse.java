package Service.Response;
import Service.response;

/**
 * serializes load response data
 */
public class loadResponse extends response {
    /**
     * constructor for serializing load response data
     * @param message string
     * @param success boolean
     */
    public loadResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
