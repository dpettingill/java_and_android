package DataAccess;
import Model.*;

/**
 * used to insert, delete or get authTokens from the db
 */
public class AuthTokenDAO {
    /**
     * inserts token into db
     * @param token unique identifier
     */
    public void insert(AuthToken token)
    {

    }

    /**
     * deletes token from db
     * @param token unique identifier
     */
    public void delete(AuthToken token)
    {

    }

    /**
     * gets token associated with username from db
     * @param username name of user
     * @return AuthToken
     */
    public AuthToken getToken(String username)
    {
        return null;
    }

    /**
     * clears the AuthTokens table completely
     */
    public void clear()
    {

    }
}

