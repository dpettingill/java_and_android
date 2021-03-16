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
        return psRes;
    }

    public PersonResponse getPerson(Connection conn, String personId, String associatedUsername) throws DataAccessException
    {
        PersonDAO pDAO = new PersonDAO(conn);
        Person person = pDAO.find(personId);
        PersonResponse pRes = null;
        if (!person.getAssociatedUsername().equals(associatedUsername))
        {
            return pRes;
        }
        else if (person != null)
        {
            pRes = new PersonResponse(person.getPersonID(), person.getAssociatedUsername(),
                    person.getFirstName(), person.getLastName(), person.getGender(),
                    person.getFatherID(), person.getMotherID(), person.getSpouseID(), null, true);
        }
        return pRes;
    }
}
