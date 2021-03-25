package Request;

/**
 * deserializes a user/login request
 */
public class UserLoginRequest {
    /**
     * potential username
     */
    private final String username;
    /**
     * attempted password
     */
    private final String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
