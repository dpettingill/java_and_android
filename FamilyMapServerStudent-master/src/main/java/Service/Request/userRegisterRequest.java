package Service.Request;

/**
 * deserializes json for user/register requests
 */
public class userRegisterRequest {
    /**
     * username for user
     */
    private String username;
    /**
     * password for user
     */
    private String password;
    /**
     * email of user
     */
    private String email;
    /**
     * first name of user
     */
    private String firstName;
    /**
     * last name of user
     */
    private String lastName;
    /**
     * 'f' or 'm' of user
     */
    private char gender;
    /**
     * whether the request had all the right info or not
     */
    private boolean success;
}
