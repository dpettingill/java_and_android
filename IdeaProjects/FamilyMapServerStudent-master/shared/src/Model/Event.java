package Model;

/**
 * Event class creates objects of type Event
 * can be stored on Events table
 */
public class Event {
    /**
     * uniquely defines event
     */
    private String eventID;
    /**
     * connects to user table
     */
    private String associatedUsername;
    /**
     * connects to person table
     */
    private String personID;
    /**
     * latitude of event occurrence
     */
    private float latitude;
    /**
     * longitude of event occurrence
     */
    private float longitude;
    /**
     * country where event happened
     */
    private String country;
    /**
     * city event happened in
     */
    private String city;
    /**
     * type of event - marriage, birth etc
     */
    private String eventType;
    /**
     * year of event
     */
    private int year;


    /**
     *
     * @param id primary key for an event aka unique identifier
     * @param associatedUsername links to the current user
     * @param personId links to the person who had the event
     * @param latitude coordinate for the where
     * @param longitude coordinate for the where
     * @param country country of occurrence
     * @param city city of occurrence
     * @param eventType what kind of event? baptism, marriage, death etc
     * @param year year of occurrence
     */
    public Event(String id, String associatedUsername, String personId, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.eventID = id;
        this.associatedUsername = associatedUsername;
        this.personID = personId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * overload no param constructor
     */
    public Event() {}

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * equals function
     * @param o Object to test against
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Event) {
            Event oEvent = (Event) o;
            return oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    oEvent.getLatitude() == (getLatitude()) &&
                    oEvent.getLongitude() == (getLongitude()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear() == (getYear());
        } else {
            return false;
        }
    }
}
