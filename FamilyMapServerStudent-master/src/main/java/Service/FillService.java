package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Service.Response.*;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;


/**
 * creates our data and puts in db
 */
public class FillService {
    private Database db;
    private Connection conn;
    private PersonDAO pDao;
    private EventDAO eDao;
    private location locations;
    private int personCount = 0;
    private int eventCount = 0;
    /**
     * fills data for user with username up to _ generations
     * @return fillResponse
     */
    public FillResponse fill(String username, int generations) throws DataAccessException, FileNotFoundException, SQLException {
        if (generations == -1)
        {
            generations = 4;
        }

        boolean success = this.fillGenerations(username, generations);
        if (success)
        {
            return new FillResponse("Successfully added " + personCount +
                    " persons and " + eventCount + " events to the data base", true);
        } else
        {
            return new FillResponse("Error filling the generations", false);
        }
    }

    public boolean fillGenerations(String username, int num_generations) throws DataAccessException, FileNotFoundException, SQLException {
        db = new Database();
        conn = db.getConnection();
        pDao = new PersonDAO(conn);
        eDao = new EventDAO(conn);

        try {
            pDao.clear(username);
            eDao.clear(username);
            UserDAO uDao = new UserDAO(conn);
            User user = uDao.find(username);
            if (user != null)
            {
                Person person = new Person(user.getPersonId(), user.getUsername(),
                        user.getFirstName(), user.getLastName(), user.getGender(),
                        null, null, null);
                pDao.insert(person);
                personCount++;
                eventGeneratorPerson(person);
                recursiveGenerationAdd(person, 1, num_generations);
            }
            else
            {
                throw new SQLException("user not found with that username");
            }
        } catch (DataAccessException | FileNotFoundException | SQLException e) {
            db.closeConnection(false);
            return false;
        }
        db.closeConnection(true);
        return true;
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
    private void recursiveGenerationAdd(Person person, int gen, int num_gens) throws FileNotFoundException, DataAccessException {
        Gson gson = new Gson();
        Random random = new Random();
        String motherId = UUID.randomUUID().toString();
        String fatherId = UUID.randomUUID().toString();

        //father stuff
        names m_names = gson.fromJson(new FileReader("json/mnames.json"), names.class);
        Person father = new Person(fatherId, person.getAssociatedUsername(),
                m_names.data[random.nextInt(m_names.data.length)],
                person.getLastName(), "m", null, null, motherId);
        person.setFatherID(fatherId);
        pDao.insert(father);
        personCount++;

        //mother stuff
        names f_names = gson.fromJson(new FileReader("json/fnames.json"), names.class);
        Person mother = new Person(motherId, person.getAssociatedUsername(),
                f_names.data[random.nextInt(f_names.data.length)],
                person.getLastName(), "f", null, null, fatherId);
        person.setMotherID(motherId);
        pDao.insert(mother);
        personCount++;

        pDao.update(person); //update new father and mother ids for person

        //event stuff
        eventGenerator(father, mother, gen);

        //recursion stuff
        if (gen < num_gens) {
            recursiveGenerationAdd(father, (gen + 1), num_gens);
            recursiveGenerationAdd(mother, (gen + 1), num_gens);
        }
    }

    private void eventGenerator(Person father, Person mother, int gen) throws FileNotFoundException, DataAccessException {
        Gson gson = new Gson();
        Random random = new Random();
        locations = gson.fromJson(new FileReader("json/locations.json"), location.class);
        int year = 0;

        year = (1996- gen *25);
        newEvent(father, random, "birth", year);
        newEvent(mother, random, "birth", year);
        year = (1996 - (25 * (gen - 1)) - 2);
        newEvent(father, random, "marriage", year);
        newEvent(mother, random, "marriage", year);
        year = (2021-25*(gen - 1));
        newEvent(father, random, "death", year);
        newEvent(mother, random, "death", year);
        eventCount += 6;
    }

    private void eventGeneratorPerson(Person person) throws FileNotFoundException, DataAccessException {
        Gson gson = new Gson();
        Random random = new Random();
        locations = gson.fromJson(new FileReader("json/locations.json"), location.class);
        int year = 0;

        year = 1996;
        newEvent(person, random, "birth", year);
        year = 2006;
        newEvent(person, random, "baseball practice", year);
        year = 2014;
        newEvent(person, random, "high school grad", year);
        eventCount += 3;
    }

    private void newEvent(Person mother, Random random, String eventType, int year) throws DataAccessException {
        data my_data;
        my_data = locations.data[random.nextInt(locations.data.length)];
        Event event = new Event(UUID.randomUUID().toString(),
                mother.getAssociatedUsername(), mother.getPersonID(),
                my_data.latitude, my_data.longitude, my_data.country,
                my_data.city, eventType, year);
        eDao.insert(event);
    }

    //births
    //1996 - 25 = 1971
    // 1996 - 25*(1-1) - 2 = 1994
    // 1996 - 50 = 1946
    // 1996 - 25*(2-1) - 2 = 1969
    //deaths
    // 2021 (at abt 50)
    // 1996
    // 1971
    // 2018

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
