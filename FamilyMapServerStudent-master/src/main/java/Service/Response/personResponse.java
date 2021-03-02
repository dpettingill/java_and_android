package Service.Response;
import Service.response;
import Model.Person;

/**
 * class to serialize person response data
 */
public class personResponse extends response {
    /**
     * array of persons obtained
     */
    Person[] person_arr = {};


    /**
     * constructor for person response data
     * @param person_arr array of person objects
     * @param message string
     * @param success boolean
     */
    public personResponse(Person[] person_arr, String message, boolean success) {
        this.person_arr = person_arr;
        this.message = message;
        this.success = success;
    }
}
