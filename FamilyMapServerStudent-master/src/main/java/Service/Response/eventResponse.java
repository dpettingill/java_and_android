package Service.Response;
import Service.response;
import Model.Event;

/**
 * serializes event response data
 */
public class eventResponse extends response {
    /**
     * array of events we got
     */
    private Event[] event = {};


    /**
     * constructor for event response data
     * @param event event object
     * @param message string
     * @param success boolean
     */
    public eventResponse(Event[] event, String message, boolean success) {
        this.event = event;
        this.message = message;
        this.success = success;
    }
}
