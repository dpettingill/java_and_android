package DataAccess;
import Model.Event;
import Model.User;

import java.sql.*;

/**
 * DAO for users
 */
public class UserDAO {
    /**
     * Connection to db
     */
    private final Connection conn;

    /**
     * Constructor
     * @param conn Connection
     */
    public UserDAO(Connection conn)
    {
        this.conn = conn;
    }


    /**
     * inserts a user into Users table
     * @param user user object
     */
    public void insert(User user) throws DataAccessException
    {
        String sql = "INSERT INTO Users (Username, Password, Email, FirstName, LastName, " +
                "Gender, PersonId) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error while trying to insert into Users table");
        }
    }

    /**
     * finds a user id'ed by username
     * @param username String
     * @return Person
     * @throws DataAccessException
     */
    public User find(String username) throws DataAccessException
    {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("PersonId"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * clears the Persons table completely
     */
    public void clear() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while clearing the Users table");
        }
    }

    /**
     * deletes a user from Users table
     * @param user user object
     */
    public void delete(User user)
    {

    }
}
