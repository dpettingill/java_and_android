package Service.Response;
import Service.*;

/**
 * response to a user registering
 */
public class UserRegisterResponse extends GenericResponse {
    /**
     * unique authentication token
     */
    private final String authtoken;
    /**
     * associated username
     */
    private final String username;
    /**
     * the Id of the person's that we will be registering
     */
    private final String personID;


    /**
     * constructor
     * @param authToken string
     * @param username string connects to a user object
     * @param personId string connects to a person object
     * @param message string
     * @param success boolean
     */
    public UserRegisterResponse(String authToken, String username, String personId, String message, boolean success) {
        this.authtoken = authToken;
        this.username = username;
        this.personID = personId;
        this.message = message;
        this.success = success;
    }

    public String getauthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }
}
