package Service.Request;
import Model.*;

/**
 * deserializes the request body from a load request
 */
public class LoadRequest {
    /**
     * array of users to be loaded in
     */
    private final User[] users = {};
    /**
     * array of persons same format as in /person/personId
     */
    private final Person[] persons = {};
    /**
     * array of events to be loaded in
     */
    private final Event[] events = {};

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}
