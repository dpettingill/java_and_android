package Service.Response;
import Service.GenericResponse;
import Model.Event;
/**
 * serializes Event Id response data
 */
public class EventResponse extends GenericResponse {
    private String associatedUsername;
    /**
     * event that we got
     */
    private final Event event;


    /**
     * constructor for event Id data
     * @param event event object
     * @param message string
     * @param success boolean
     */
    public EventResponse(String associatedUsername, Event event, String message, boolean success) {
        this.associatedUsername = associatedUsername;
        this.event = event;
        this.message = message;
        this.success = success;
    }

    public EventResponse(String message, boolean success) {
        this.associatedUsername = null;
        this.event = null;
        this.message = message;
        this.success = success;
    }
}
