package Model;

/**
 * class Person creates objects of type Person
 * which can be stored on the Persons table
 */
public class Person {
    /**
     * id used as pk for person table
     */
    private String id;
    /**
     * connects to the user table
     */
    private String associatedUsername;
    /**
     * person's first name
     */
    private String firstName;
    /**
     * person's last name
     */
    private String lastName;
    /**
     * person's gender
     */
    private String gender;
    /**
     * id that connects to the entry for person's father
     */
    private String fatherId;
    /**
     * id that connects to the entry for person's mother
     */
    private String motherId;
    /**
     * String for spouse id
     */
    private String spouseId;


    /**
     *
     * @param id person identifier
     * @param associatedUsername connects to user table
     * @param firstName string
     * @param lastName string
     * @param gender char
     * @param fatherId string
     * @param motherId string
     * @param spouseId string
     */
    public Person(String id, String associatedUsername, String firstName, String lastName, String gender, String fatherId, String motherId, String spouseId) {
        this.id = id;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.spouseId = spouseId;
    }

    /**
     * constructor w/o optionals
     * @param id
     * @param associatedUsername
     * @param firstName
     * @param lastName
     * @param gender
     */
    public Person(String id, String associatedUsername, String firstName, String lastName, String gender) {
        this.id = id;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherId = null;
        this.motherId = null;
        this.spouseId = null;
    }

    /**
     *
     */
    public Person() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public String getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }

    /**
     *
     * @param o object
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Person) {
            Person oPerson = (Person) o;
            return oPerson.getId().equals(getId()) &&
                    oPerson.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getGender().equals(getGender()) &&
                    oPerson.getFatherId().equals(getFatherId()) &&
                    oPerson.getMotherId().equals(getMotherId()) &&
                    oPerson.getSpouseId().equals(getSpouseId());
        } else {
            return false;
        }
    }
}
