package Service;

import DataAccess.DataAccessException;
import DataAccess.PersonDAO;
import Model.Person;
import Service.Response.*;

import java.sql.Connection;

/**
 * returns all family members of user
 */
public class PersonService {
    /**
     * gets an array of persons and returns to caller
     * all family members of user
     */
    public PersonsResponse getPersons(Connection conn, String associatedUsername) throws DataAccessException
    {
        PersonDAO pDAO = new PersonDAO(conn);
        Person persons[] = pDAO.findAll(associatedUsername);
        PersonsResponse psRes = null;
        if (persons != null)
        {
            psRes = new PersonsResponse(persons, null, true);
        }
        else
        {
            psRes = new PersonsResponse("Error getting persons array", false);
        }
        return psRes;
    }

    public PersonResponse getPerson(Connection conn, String personId) throws DataAccessException
    {
        PersonDAO pDAO = new PersonDAO(conn);
        Person person = pDAO.find(personId);
        PersonResponse pRes = null;
        if (person != null)
        {
            pRes = new PersonResponse(person.getAssociatedUsername(),
                    person.getId(), person.getFirstName(), person.getLastName(), person.getGender(),
                    person.getFatherId(), person.getMotherId(), person.getSpouseId(), null, true);
        }
        else
        {
            pRes = new PersonResponse("Error getting person", false);
        }
        return pRes;
    }
}