package Service.Response;
import Service.GenericResponse;
import Model.Event;

/**
 * serializes event response data
 */
public class EventsResponse extends GenericResponse {
    private String associatedUsername;
    /**
     * array of events we got
     */
    private Event[] data = {};

    public Event[] getData() {
        return data;
    }

    /**
     * constructor for event response data
     * @param data event object
     * @param message string
     * @param success boolean
     */
    public EventsResponse(Event[] data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public EventsResponse(String message, boolean success) {
        this.data = null;
        this.message = message;
        this.success = success;
    }
}
