package Service.Response;
import Service.*;

/**
 * response to a user registering
 */
public class userRegisterResponse extends response {
    /**
     * unique authentication token
     */
    private String authToken;
    /**
     * associated username
     */
    private String username;
    /**
     * the Id of the person's that we will be registering
     */
    private String personId;


    /**
     * constructor
     * @param authToken string
     * @param username string connects to a user object
     * @param personId string connects to a person object
     * @param message string
     * @param Success boolean
     */
    public userRegisterResponse(String authToken, String username, String personId, String message, boolean Success) {
        this.authToken = authToken;
        this.username = username;
        this.personId = personId;
        this.message = message;
        this.success = success;
    }
}
