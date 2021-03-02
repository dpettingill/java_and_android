package Service.Response;
import Service.response;
import Model.Event;
/**
 * serializes Event Id response data
 */
public class eventEventIdResponse extends response {
    /**
     * event that we got
     */
    private Event event;


    /**
     * constructor for event Id data
     * @param event event object
     * @param message string
     * @param success boolean
     */
    public eventEventIdResponse(Event event, String message, boolean success) {
        this.event = event;
        this.message = message;
        this.success = success;
    }
}
