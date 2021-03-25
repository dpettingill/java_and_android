package DataAccess;

import Model.AuthToken;
import java.sql.*;

/**
 * used to insert, delete or get authTokens from the db
 */
public class AuthTokenDAO {
    /**
     * Connection to db
     */
    private final Connection conn;

    /**
     * Constructor
     * @param conn
     */
    public AuthTokenDAO(Connection conn) { this.conn = conn; }

    /**
     * inserts token into db
     * @param token unique identifier
     */
    public void insert(AuthToken token) throws DataAccessException {
        String sql = "INSERT INTO AuthTokens (AuthToken, AssociatedUsername) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token.getAuthToken());
            stmt.setString(2, token.getUsername());

            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new DataAccessException("Error while inserting into AuthTokens");
        }
    }

    /**
     * gets token associated with username from db
     * @param value name of user
     * @return AuthToken
     */
    public AuthToken find(String value, String column) throws DataAccessException
    {
        AuthToken token;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens WHERE " + column + " = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            rs = stmt.executeQuery();
            if (rs.next()) {
                token = new AuthToken(rs.getString("AuthToken"), rs.getString("AssociatedUsername"));
                return token;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding AuthToken");
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
     * clears the AuthTokens table completely
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()) {
            String sql = "DELETE FROM AuthTokens";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Error while clearing AuthTokens");
        }
    }

    /**
     * deletes token from db
     * @param token unique identifier
     */
    public void delete(AuthToken token)
    {

    }
}

