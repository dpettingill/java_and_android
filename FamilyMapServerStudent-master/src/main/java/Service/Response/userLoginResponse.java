package Service.Response;
import Service.response;

/**
 * response for a user login
 */
public class userLoginResponse extends response {
    /**
     * unique authentication token
     */
    private String authToken;
    /**
     * username assigned to this user
     */
    private String username;
    /**
     * id connecting to person object
     */
    private String personId;


    /**
     * constructor
     * @param authToken string
     * @param username string connects to a user object
     * @param personId string connects to a person object
     * @param message string
     * @param success boolean
     */
    public userLoginResponse(String authToken, String username, String personId, String message, boolean success) {
        this.authToken = authToken;
        this.username = username;
        this.personId = personId;
        this.message = message;
        this.success = success;
    }
}
