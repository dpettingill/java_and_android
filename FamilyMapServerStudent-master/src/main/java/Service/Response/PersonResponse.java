package Service.Response;
import Service.GenericResponse;

/**
 * used to serialize personId response data
 */
public class PersonResponse extends GenericResponse {
    /**
     * username connected to base user
     */
    private final String associatedUsername;
    /**
     * id connected to person
     */
    private final String personId;
    /**
     * first name of person
     */
    private final String firstName;
    /**
     * last name of person
     */
    private final String lastName;
    /**
     * single char of gender of person
     */
    private final String gender;
    /**
     * optional - id of father of person
     */
    private final String fatherId;
    /**
     * option - id of mother of person
     */
    private final String motherId;
    /**
     * optional - id of spouse of person
     */
    private final String spouseId;


    /**
     * constructor for serializing personId response data
     * @param associatedUsername connects to a user object
     * @param personId connects to a person object
     * @param firstName string
     * @param lastName string
     * @param gender char
     * @param fatherId string
     * @param motherId string
     * @param spouseId string
     * @param message string
     * @param success boolean
     */
    public PersonResponse(String associatedUsername, String personId, String firstName, String lastName, String gender, String fatherId, String motherId, String spouseId, String message, boolean success) {
        this.associatedUsername = associatedUsername;
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.spouseId = spouseId;
        this.message = message;
        this.success = success;
    }

    public PersonResponse(String message, boolean success)
    {
        this.associatedUsername = null;
        this.personId = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.fatherId = null;
        this.motherId = null;
        this.spouseId = null;
        this.message = message;
        this.success = success;
    }
}
