package service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Service.PersonService;
import Service.Request.UserRegisterRequest;
import Service.Response.PersonResponse;
import Service.Response.PersonsResponse;
import Service.Response.UserRegisterResponse;
import Service.UserRegisterService;
import client.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {

    private UserRegisterRequest urReq;
    private UserRegisterService urs;
    private PersonService ps;
    private Database db;
    private Connection conn;

    private static final String EMPTY_STRING = "";

    public PersonServiceTest() {
    }

    @BeforeEach
    public void setUp() throws DataAccessException {
        urs = new UserRegisterService();
        urReq = new UserRegisterRequest("Dannyboi", "1234", "test@gmail.com",
                "Danny", "boi", "m");
        ps = new PersonService();
        db = new Database();
        conn = db.getConnection();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void getPersonPass() throws DataAccessException, FileNotFoundException {
        UserRegisterResponse urRes = urs.register(this.urReq);
        PersonResponse pRes = ps.getPerson(conn, urRes.getPersonID(), urRes.getUsername());
        assertEquals(urRes.getPersonID(), pRes.getPersonID(), "personID returned doesn't match personID asked for");
        assertEquals("Danny", pRes.getFirstName(), "firstName of person returned does not match that of user's registration");
        assertEquals("boi", pRes.getLastName(), "lastName of person returned does not match that of user's registration");
        assertEquals("m", pRes.getGender(), "gender of person returned does not match that of user's registration");
        assertEquals(urRes.getUsername(), pRes.getAssociatedUsername());
    }

    @Test
    public void getPersonsPass() throws DataAccessException, FileNotFoundException {
        UserRegisterResponse urRes = urs.register(this.urReq);
        PersonsResponse psRes = ps.getPersons(conn, urRes.getUsername());
        assertNotNull(psRes.getPerson(urRes.getPersonID()), "User's person not found in passoffresult");
    }

    @Test
    public void getPersonFail() throws DataAccessException, FileNotFoundException {
        PersonResponse pRes = ps.getPerson(conn, "stuff", "Dannyboi");
        assertNull(pRes.getPersonID(), "person was not null when it should have been");
    }

    @Test
    public void getPersonsFail() throws DataAccessException, FileNotFoundException {
        PersonsResponse psRes = ps.getPersons(conn, "Dannyboi");
        assertNull(psRes.getData(), "persons was not null when it should have been");
    }
}
