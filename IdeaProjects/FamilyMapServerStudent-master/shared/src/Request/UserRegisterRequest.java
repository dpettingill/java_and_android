package Request;

/**
 * deserializes json for user/register requests
 */
public class UserRegisterRequest {
    /**
     * username for user
     */
    private final String username;
    /**
     * password for user
     */
    private final String password;
    /**
     * email of user
     */
    private final String email;
    /**
     * first name of user
     */
    private final String firstName;
    /**
     * last name of user
     */
    private final String lastName;
    /**
     * 'f' or 'm' of user
     */
    private final String gender;

    public UserRegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

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
