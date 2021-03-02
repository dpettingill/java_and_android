package Service.Request;
import Model.*;

/**
 * deserializes the request body from a load request
 */
public class loadRequest {
    /**
     * array of users to be loaded in
     */
    User[] user_arr = {};
    /**
     * array of persons same format as in /person/personId
     */
    Person[] person_arr = {};
    /**
     * array of events to be loaded in
     */
    Event[] event_arr = {};
}
