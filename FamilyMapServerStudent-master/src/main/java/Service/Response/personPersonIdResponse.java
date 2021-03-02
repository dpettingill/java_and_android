package Service.Response;
import Service.response;

/**
 * used to serialize personId response data
 */
public class personPersonIdResponse extends response {
    /**
     * username connected to base user
     */
    private String associatedUsername;
    /**
     * id connected to person
     */
    private String personId;
    /**
     * first name of person
     */
    private String firstName;
    /**
     * last name of person
     */
    private String lastName;
    /**
     * single char of gender of person
     */
    private char gender;
    /**
     * optional - id of father of person
     */
    private String fatherId;
    /**
     * option - id of mother of person
     */
    private String motherId;
    /**
     * optional - id of spouse of person
     */
    private String spouseId;


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
    public personPersonIdResponse(String associatedUsername, String personId, String firstName, String lastName, char gender, String fatherId, String motherId, String spouseId, String message, boolean success) {
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
}
