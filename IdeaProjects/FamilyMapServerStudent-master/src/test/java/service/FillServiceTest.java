package service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Service.EventService;
import Service.FillService;
import Service.PersonService;
import Request.UserRegisterRequest;
import Response.EventsResponse;
import Response.FillResponse;
import Response.PersonsResponse;
import Response.UserRegisterResponse;
import Service.UserRegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Model.Person;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    private FillService fs;
    private UserRegisterRequest urReq;
    private UserRegisterService urs;
    private static final String EMPTY_STRING = "";

    @BeforeEach
    public void setUp() throws DataAccessException {
        fs = new FillService();
        urs = new UserRegisterService();
        urReq = new UserRegisterRequest("Dannyboi", "1234", "test@gmail.com",
                "Danny", "boi", "m");
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void fillPass1() throws DataAccessException, FileNotFoundException, SQLException {
        urs.register(urReq);
        int generations = 5;
        FillResponse fRes = fs.fill("Dannyboi", generations);
        int minimumPeople = (int) Math.pow(2, generations + 1) - 1;
        int minEvents = minimumPeople * 2;
        assertNotNull(fRes.getMessage(), "message was null OR its variable name did not match that of the expected JSon (see API)");
        //Checks to see if you filled fillResult with a message String
        assertNotEquals(EMPTY_STRING, fRes.getMessage(), "message was empty string");
        //Splits the fillResult message into four crucial parts
        String[] message = fRes.getMessage().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        //Checks to be sure the fillResult message starts with the phrase "Successfully added "
        assertEquals("Successfully added ", message[0], "First part of passoffresult message does not match API");
        //Checks to be sure the fillResult message confirms that 63 people were added into the database
        assertTrue(minimumPeople <= Integer.parseInt(message[1]), "Not enough people added");
        //Checks to be sure the fillResult message has the phrase " persons and " in between listing the number of people and the number of events
        assertEquals(" persons and ", message[2], "Second part of passoffresult message does not match API");
        //Checks to be sure the fillResult message confirms that 187 events were added into the database
        assertTrue(minEvents <= Integer.parseInt(message[3]), "Not enough events added");
    }

    @Test
    public void fillPass2() throws DataAccessException, FileNotFoundException, SQLException {
        Database db = new Database();
        Connection conn = db.getConnection();
        UserRegisterResponse urRes = urs.register(urReq);
        int generations = 5;
        FillResponse fRes = fs.fill("Dannyboi", generations);
        PersonService ps = new PersonService();
        EventService es = new EventService();
        PersonsResponse pRes = ps.getPersons(conn, urRes.getUsername());
        EventsResponse eRes = es.getEvents(conn, urRes.getUsername());
        Person userPerson = pRes.getPerson(urRes.getPersonID());
        assertNotNull(userPerson, "User's Person not included in passoffresult");
        Person userFather = pRes.getPerson(userPerson.getFatherID());
        Person userMother = pRes.getPerson(userPerson.getMotherID());
        assertNotNull(userFather, "User's Father's Person not included in passoffresult");
        assertNotNull(userMother, "User's Mother's Person not included in passoffresult");
        db.closeConnection(true);
    }


}
