package Service;
import Model.*;
import Service.Request.*;
import Service.Response.*;
import DataAccess.*;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.UUID;
import java.sql.Connection;

/**
 * registers a new user
 * returns a message whether successful or not
 */
public class userRegister {
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
    public userRegisterResponse register(userRegisterRequest urReq) throws DataAccessException, FileNotFoundException {
        db = new Database();
        conn = db.getConnection();
        db.clearTables();
        userRegisterResponse urRes;
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

            fill phil = new fill();
            phil.fillGenerations(newUser.getUsername(), NUM_GENS); //use fill to generate 4 gens data

            urRes = new userRegisterResponse(
                    authToken_newUser.getAuthToken(),
                    newUser.getUsername(),
                    newUser.getPersonId(), null, true);
        } catch (Exception e) {
            urRes = new userRegisterResponse(
                    null, null, null,
                    "Error registering user", false);
        }
        return urRes;
    }

    //generate events for the new user??
//        Gson gson = new Gson();
//        Random random = new Random();
//        location locations = gson.fromJson(new FileReader("json/locations.json"), location.class);
//        data my_data = locations.data[random.nextInt(locations.data.length)];
//        Event birth = new Event(UUID.randomUUID().toString(), newUser.getUsername(), person_newUser.getId(), my_data.latitude,
//                my_data.longitude, my_data.country, my_data.city, 1996);




}
