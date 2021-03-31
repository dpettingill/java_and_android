package Response;
import Model.Person;

/**
 * class to serialize person response data
 */
public class PersonsResponse extends GenericResponse {
    /**
     * array of persons obtained
     */
    Person[] data = {};

    public Person[] getData() {
        return data;
    }

    /**
     * constructor for person response data
     * @param data array of person objects
     * @param message string
     * @param success boolean
     */
    public PersonsResponse(Person[] data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public PersonsResponse(String message, boolean success) {
        this.data = null;
        this.message = message;
        this.success = success;
    }

    public Person getPerson(String personID)
    {
        for(int i = 0; i < this.data.length; i++) {
            if (this.data[i].getPersonID().equals(personID)) {
                return this.data[i];
            }
        }

        return null;
    }
}
