package Service.Response;
import Service.*;

/**
 * response to a user registering
 */
public class UserRegisterResponse extends GenericResponse {
    /**
     * unique authentication token
     */
    private final String authToken;
    /**
     * associated username
     */
    private final String username;
    /**
     * the Id of the person's that we will be registering
     */
    private final String personId;


    /**
     * constructor
     * @param authToken string
     * @param username string connects to a user object
     * @param personId string connects to a person object
     * @param message string
     * @param Success boolean
     */
    public UserRegisterResponse(String authToken, String username, String personId, String message, boolean success) {
        this.authToken = authToken;
        this.username = username;
        this.personId = personId;
        this.message = message;
        this.success = success;
    }
}
