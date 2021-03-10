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
    private String gender;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
