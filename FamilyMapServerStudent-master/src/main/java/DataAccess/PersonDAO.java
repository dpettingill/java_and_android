package DataAccess;
import Model.Person;

import java.sql.*;


/**
 * used to query person data from db
 */
public class PersonDAO {
    /**
     * Connection to db
     */
    private final Connection conn;

    /**
     * constructor
     * @param conn db connection
     */
    public PersonDAO(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * inserts person onto db
     * @param person a user or a relative of the user
     * @throws DataAccessException
     */
    public void insert(Person person) throws DataAccessException
    {
        String sql = "INSERT INTO Persons (Id, AssociatedUsername, FirstName, LastName, " +
                "Gender, FatherId, MotherId, SpouseId) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error while trying to insert into Persons table");
        }
    }

    public void update(Person person) throws DataAccessException
    {
        String sql = "UPDATE Persons SET FatherId = ?, MotherId = ? WHERE Id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getFatherID());
            stmt.setString(2, person.getMotherID());
            stmt.setString(3, person.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error while trying to insert into Persons table");
        }
    }

    /**
     * finds a person in the persons table
     * @param Id String
     * @return Person
     * @throws DataAccessException
     */
    public Person find(String Id) throws DataAccessException {
        Person person = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE Id =?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("Id"), rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("FatherId"),
                        rs.getString("MotherId"), rs.getString("SpouseId"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null; //if not found then return null
                }
            }
        }
        return person;
    }

    public Person[] findAll(String username) throws DataAccessException {
        Person[] persons = null;
        ResultSet rs = null;
        int size = 0;

        //get the count real quick
        String sql = "SELECT COUNT(AssociatedUsername) AS total FROM Persons WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            size = rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //now the actual stuff
        sql = "SELECT * FROM Persons WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            int i = 0;
            persons = new Person[size];
            while (rs.next()) {
                Person person = new Person(rs.getString("Id"), rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("FatherId"),
                        rs.getString("MotherId"), rs.getString("SpouseId"));
                persons[i] = person; //don't forget to make sure events is big enough
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null; //error occurs return null
                }
            }
        }
        return persons;
    }

    /**
     * clears the Persons table completely
     */
    public void clear() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Persons";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while clearing the Persons table");
        }
    }

    public boolean clear(String username) throws SQLException
    {
        PreparedStatement stmt = null;
        try {
            String sql = "delete from Persons where AssociatedUsername = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            int rows_affected = stmt.executeUpdate();
            return (rows_affected == 1);
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * deletes person from db
     * @param person a user or a relative of the user
     */
    public void delete(Person person)
    {

    }
}
