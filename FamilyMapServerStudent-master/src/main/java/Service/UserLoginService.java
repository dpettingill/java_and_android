package Service;
import DataAccess.*;
import Model.AuthToken;
import Model.User;
import Service.Request.*;
import Service.Response.*;

import java.sql.Connection;
import java.util.UUID;

/**
 * userLogin request and response
 */
public class UserLoginService {
    private Database db;
    private Connection conn;
    /**
     * will grab info from user login request
     * check if it matches what's in db
     * send a response telling successful or not
     * @param ulr userLoginRequest
     * @return userLoginResponse
     */
    public UserLoginResponse login(UserLoginRequest ulr) throws DataAccessException
    {
        db = new Database();
        conn = db.getConnection();
        UserDAO uDao = new UserDAO(conn);
        UserLoginResponse urRes;
        User user = uDao.find(ulr.getUsername());
        try
        {
            if (user.getPassword().equals(ulr.getPassword())) {
                AuthToken authToken = new AuthToken(
                        UUID.randomUUID().toString(),
                        user.getUsername());
                AuthTokenDAO atDao = new AuthTokenDAO(conn);
                atDao.insert(authToken);
                urRes = new UserLoginResponse(
                        authToken.getAuthToken(),
                        user.getUsername(),
                        user.getPersonId(), null, true);
                db.closeConnection(true);
            }
            else {
                throw new Exception("user tried to log in with wrong password");
            }
        } catch (Exception e)
        {
            urRes = new UserLoginResponse(
                null, null, null,
                    "Error logging in user", false
            );
            db.closeConnection(false);
        }
        return urRes;
    }
}
