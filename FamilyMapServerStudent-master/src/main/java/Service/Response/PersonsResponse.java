package Service.Response;
import Service.GenericResponse;
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
        int i = 0;
        while (i < data.length - 1)
        {
            if (data[i].getPersonID().equals(personID))
            {
                return data[i];
            }
            i++;
        }
        return null;
    }
}
