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
    public userRegisterResponse register(userRegisterRequest urReq) throws DataAccessException {
        db = new Database();
        conn = db.getConnection();
        db.clearTables();

        UserDAO uDAO = new UserDAO(conn);
        User newUser = new User(urReq.getUsername(), urReq.getPassword(),
                urReq.getEmail(), urReq.getFirstName(), urReq.getLastName(),
                urReq.getGender(), UUID.randomUUID().toString());
        uDAO.insert(newUser);

        AuthTokenDAO atDAO = new AuthTokenDAO(conn);
        AuthToken authToken_newUser = new AuthToken(UUID.randomUUID().toString(), newUser.getUsername());
        atDAO.insert(authToken_newUser);

        pDao = new PersonDAO(conn);
        Person person_newUser = new Person(UUID.randomUUID().toString(), newUser.getUsername(),
                newUser.getFirstName(), newUser.getLastName(), newUser.getGender(),
                null, null, null);
        pDao.insert(person_newUser);

        eDao = new EventDAO(conn);

        //also generate 4 gens of data here

        //return success response here
        userRegisterResponse urRes = new userRegisterResponse(
                authToken_newUser.getAuthToken(),
                newUser.getUsername(),
                person_newUser.getId(), null, true);
        return urRes;
    }

    /**
     * ok so I need to make a recursive function that will add 4 generations of data
     * things to worry about:
     * age constraints - each generation needs ot be older than the last
     * event timing constraints - birth needs to be the first event,
     * marriage - christening - travel next, then death
     * say I start with nancy frost I need 3 events for her
     * then I need to create data for both her parents bill frost and georgina frost
     * then data for their parents etc
     * do I want the base case to be 4 generations?
     * pass a null person into each function and then load them up with info?
     * set their id to that of the
     */
    private void recursiveGenerationAdd(Person person, int gen) throws FileNotFoundException {
        Gson gson = new Gson();
        Random random = new Random();
        String m_id = UUID.randomUUID().toString();
        String f_id = UUID.randomUUID().toString();

        //father stuff
        names m_names = gson.fromJson(new FileReader("json/mnames.json"), names.class);
        Person father = new Person(f_id, person.getAssociatedUsername(),
                m_names.data[random.nextInt(m_names.data.length)],
                person.getLastName(), "m", null, null, m_id);
        person.setFatherId(f_id);

        //mother stuff
        names f_names = gson.fromJson(new FileReader("json/fnames.json"), names.class);
        Person mother = new Person(m_id, person.getAssociatedUsername(),
                f_names.data[random.nextInt(f_names.data.length)],
                person.getLastName(), "f", null, null, f_id);
        person.setMotherId(m_id);
        if (gen <= NUM_GENS) {
            recursiveGenerationAdd(father, (gen + 1));
            recursiveGenerationAdd(mother, (gen + 1));
        }

        //now events



    }

    private void eventGenerator(Person father, Person mother, int gen) throws FileNotFoundException {
        Gson gson = new Gson();
        Random random = new Random();

        location locations = gson.fromJson(new FileReader("json/locations.json"), location.class);
        data my_data = locations.data[random.nextInt(locations.data.length)];
        Event birth = new Event(UUID.randomUUID().toString(),
                father.getAssociatedUsername(), father.getId(),
                my_data.latitude, my_data.longitude, my_data.country,
                my_data.city, "birth", (2021-gen*25));
    }

    class names
    {
        private String data[];
    }

    class location
    {
        private data data[];
    }

    class data
    {
        private String country;
        private String city;
        private float latitude;
        private float longitude;

        /**
         *
         * @param country
         * @param city
         * @param latitude
         * @param longitude
         */
        public data(String country, String city, float latitude, float longitude)
        {
            this.country = country;
            this.city = city;
            this.latitude = latitude;
            this.longitude = longitude;
        }

    }


}
