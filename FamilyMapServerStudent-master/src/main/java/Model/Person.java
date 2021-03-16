package Model;

/**
 * class Person creates objects of type Person
 * which can be stored on the Persons table
 */
public class Person {
    /**
     * id used as pk for person table
     */
    private String personID;
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
    private String fatherID;
    /**
     * id that connects to the entry for person's mother
     */
    private String motherID;
    /**
     * String for spouse id
     */
    private String spouseID;


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
        this.personID = id;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherId;
        this.motherID = motherId;
        this.spouseID = spouseId;
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
        this.personID = id;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
    }

    /**
     *
     */
    public Person() {}

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
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
            return oPerson.getPersonID().equals(getPersonID()) &&
                    oPerson.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getGender().equals(getGender()) &&
                    oPerson.getFatherID().equals(getFatherID()) &&
                    oPerson.getMotherID().equals(getMotherID()) &&
                    oPerson.getSpouseID().equals(getSpouseID());
        } else {
            return false;
        }
    }
}
