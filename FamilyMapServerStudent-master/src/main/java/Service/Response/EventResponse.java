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
    private String eventID;

    /**
     * person's id
     */
    private String personID;

    /**
     * latitude string
     */
    private float latitude;

    /**
     * longitude string
     */
    private float longitude;

    /**
     * country of event
     */
    private String country;

    /**
     * city of event
     */
    private String city;

    /**
     * what kind of event
     */
    private String eventType;

    /**
     * year of event
     */
    private int year;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public EventResponse(String associatedUsername, String eventID, String personID, float latitude, float longitude,
                         String country, String city, String eventType, int year, String message, boolean success) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.message = message;
        this.success = success;
    }

    public EventResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
