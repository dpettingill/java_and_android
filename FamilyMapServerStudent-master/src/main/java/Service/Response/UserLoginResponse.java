package Service.Response;
import Service.GenericResponse;

/**
 * response for a user login
 */
public class UserLoginResponse extends GenericResponse {
    /**
     * unique authentication token
     */
    private final String authToken;
    /**
     * username assigned to this user
     */
    private final String username;
    /**
     * id connecting to person object
     */
    private final String personId;


    /**
     * constructor
     * @param authToken string
     * @param username string connects to a user object
     * @param personId string connects to a person object
     * @param message string
     * @param success boolean
     */
    public UserLoginResponse(String authToken, String username, String personId, String message, boolean success) {
        this.authToken = authToken;
        this.username = username;
        this.personId = personId;
        this.message = message;
        this.success = success;
    }
}
