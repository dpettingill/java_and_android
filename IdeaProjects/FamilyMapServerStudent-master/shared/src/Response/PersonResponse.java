package Response;

/**
 * used to serialize personId response data
 */
public class PersonResponse extends GenericResponse {
    /**
     * id connected to person
     */
    private final String personID;
    /**
     * username connected to base user
     */
    private final String associatedUsername;
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
    private final String fatherID;
    /**
     * option - id of mother of person
     */
    private final String motherID;
    /**
     * optional - id of spouse of person
     */
    private final String spouseID;

    public String getPersonID() {
        return personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

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
    public PersonResponse(String personId, String associatedUsername, String firstName, String lastName, String gender, String fatherId, String motherId, String spouseId, String message, boolean success) {
        this.personID = personId;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherId;
        this.motherID = motherId;
        this.spouseID = spouseId;
        this.message = message;
        this.success = success;
    }

    public PersonResponse(String message, boolean success)
    {
        this.associatedUsername = null;
        this.personID = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
        this.message = message;
        this.success = success;
    }
}
