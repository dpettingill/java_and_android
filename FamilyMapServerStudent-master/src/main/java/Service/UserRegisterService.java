package Service;
import Model.*;
import Service.Request.*;
import Service.Response.*;
import DataAccess.*;

import java.io.FileNotFoundException;
import java.util.UUID;
import java.sql.Connection;

/**
 * registers a new user
 * returns a message whether successful or not
 */
public class UserRegisterService {
    private static final int NUM_GENS = 4;
    private Connection conn;
    private Database db;
    private PersonDAO pDao;
    private EventDAO eDao;


    /**
     * Creates a new user account, generates 4 generations of ancestor data for
     * the new user, logs the user in, and returns an auth token (in userRegister Response).
     * @param urReq userRegisterRequest
     * @return userRegisterResponse
     */
    public UserRegisterResponse register(UserRegisterRequest urReq) throws DataAccessException, FileNotFoundException {
        db = new Database();
        conn = db.getConnection();
        UserRegisterResponse urRes;
        try {
            UserDAO uDAO = new UserDAO(conn);
            User newUser = new User(urReq.getUsername(), urReq.getPassword(),
                    urReq.getEmail(), urReq.getFirstName(), urReq.getLastName(),
                    urReq.getGender(), UUID.randomUUID().toString());
            uDAO.insert(newUser);

            AuthTokenDAO atDAO = new AuthTokenDAO(conn);
            AuthToken authToken_newUser = new AuthToken(UUID.randomUUID().toString(), newUser.getUsername());
            atDAO.insert(authToken_newUser);
            db.closeConnection(true);

            FillService phil = new FillService();
            phil.fillGenerations(newUser.getUsername(), NUM_GENS); //use fill to generate 4 gens data

            urRes = new UserRegisterResponse(
                    authToken_newUser.getAuthToken(),
                    newUser.getUsername(),
                    newUser.getPersonId(), null, true);
        } catch (Exception e) {
            urRes = new UserRegisterResponse(
                    null, null, null,
                    "Error registering user", false);
            db.closeConnection(false);
        }
        return urRes;
    }
}
