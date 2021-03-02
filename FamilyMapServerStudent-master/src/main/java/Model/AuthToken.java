package Model;

/**
 * AuthToken Class creates objects of type AuthToken
 * can be stored as entries on AuthTokens table
 */
public class AuthToken {
    /**
     * authToken uniquely identifies each logged in user
     */
    private String authToken;
    /**
     * name of user that has been authenticated
     */
    private String username;


    /**
     * authToken constructor
     * @param authToken unique identifier for a logged in user
     * @param username id of the person associated with the authToken
     */
    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    /**
     * constuctor overload with no params
     */
    public AuthToken() {}

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
