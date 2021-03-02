package Model;

/**
 * Creates an object of type User
 * has members String username, String password, String email,
 * String firstName, String lastName, char gender, and int personId
 * has constructor that sets all data members or none
 */
public class User {
    /**
     * stores the username
     */
    private String username;
    /**
     * stores the user's password
     */
    private String password;
    /**
     * stores the user's email
     */
    private String email;
    /**
     * stores the user's first name
     */
    private String firstName;
    /**
     * stores the user's last name
     */
    private String lastName;
    /**
     * stores a char representing the user's gender
     */
    private String gender;
    /**
     * stores an id to link to the person table
     */
    private String personId;


    /**
     *
     * @param username username for the given user
     * @param password password for the given user
     * @param email email for the given user
     * @param firstName first name of the given user
     * @param lastName last name of the given user
     * @param gender single char 'f' or 'm' for gender of user
     * @param personId id associated with person object for the user
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personId = personId;
    }

    /**
     *
     */
    public User() {}

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     */
    public String getPersonId() {
        return personId;
    }

    /**
     *
     * @param personId string
     */
    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
