package Service.Request;

/**
 * deserializes a user/login request
 */
public class userLoginRequest {
    /**
     * potential username
     */
    private String username;
    /**
     * attempted password
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
