package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.*;
import Response.*;

import java.sql.Connection;
import java.util.UUID;

/**
 * clears db and then loads posted data into newly cleared db
 */
public class LoadService {
    /**
     * clears db
     * then loads info from load request into db
     *
     * @param lr loadRequest
     * @return loadResponse
     */
    public LoadResponse load(LoadRequest lr) throws DataAccessException {
        Database db = new Database();
        Connection conn = null;
        try {
            conn = db.getConnection();
            db.clearTables(); //clear
            User[] users = lr.getUsers();
            int i = 0;
            UserDAO uDAO = new UserDAO(conn);
            while (i < users.length) {
                User newUser = new User(users[i].getUsername(), users[i].getPassword(),
                        users[i].getEmail(), users[i].getFirstName(), users[i].getLastName(),
                        users[i].getGender(), users[i].getPersonId());
                uDAO.insert(newUser);
                i++;
            }
            i = 0;
            PersonDAO pDAO = new PersonDAO(conn);
            Person[] persons = lr.getPersons();
            while (i < persons.length) {
                Person newPerson = new Person(persons[i].getPersonID(), persons[i].getAssociatedUsername(),
                        persons[i].getFirstName(), persons[i].getLastName(),
                        persons[i].getGender(), persons[i].getFatherID(),
                        persons[i].getMotherID(), persons[i].getSpouseID());
                pDAO.insert(newPerson);
                i++;
            }
            i = 0;
            EventDAO eDAO = new EventDAO(conn);
            Event[] events = lr.getEvents();
            while (i < events.length) {
                Event newEvent = new Event(events[i].getEventID(), events[i].getAssociatedUsername(),
                        events[i].getPersonID(), events[i].getLatitude(), events[i].getLongitude(),
                        events[i].getCountry(), events[i].getCity(), events[i].getEventType(),
                        events[i].getYear());
                eDAO.insert(newEvent);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new LoadResponse("Error clearing then loading in info", false);
        }
        db.closeConnection(true);
        return new LoadResponse("Successfully added " + lr.getUsers().length +
                " users, " + lr.getPersons().length + " persons, and " + lr.getEvents().length + " events " +
                "to the database", true);
    }
}
