package Response;

/**
 * response for a user login
 */
public class UserLoginResponse extends GenericResponse {
    /**
     * unique authentication token
     */
    private final String authtoken;
    /**
     * username assigned to this user
     */
    private final String username;
    /**
     * id connecting to person object
     */
    private final String personID;

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    /**
     * constructor
     * @param authToken string
     * @param username string connects to a user object
     * @param personId string connects to a person object
     * @param message string
     * @param success boolean
     */
    public UserLoginResponse(String authToken, String username, String personId, String message, boolean success) {
        this.authtoken = authToken;
        this.username = username;
        this.personID = personId;
        this.message = message;
        this.success = success;
    }
}
