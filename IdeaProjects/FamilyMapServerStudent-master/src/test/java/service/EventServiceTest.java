package service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Service.EventService;
import Service.LoadService;
import Request.LoadRequest;
import Request.UserRegisterRequest;
import Response.EventResponse;
import Response.EventsResponse;
import Response.LoadResponse;
import Response.UserRegisterResponse;
import Service.UserRegisterService;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventServiceTest {
    private UserRegisterRequest urReq;
    private UserRegisterService urs;
    private EventService es;
    private static final String EMPTY_STRING = "";
    private static final String ASTEROIDS1_EVENT_ID = "Sheila_Asteroids";
    private static final String ASTEROIDS1_USERNAME = "sheila";

    @BeforeEach
    public void setUp() throws DataAccessException {
        urs = new UserRegisterService();
        urReq = new UserRegisterRequest("Dannyboi", "1234", "test@gmail.com",
                "Danny", "boi", "m");
        es = new EventService();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void getEventPass() throws DataAccessException, FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        LoadRequest loadRequest = gson.fromJson(jsonReader, LoadRequest.class);
        LoadService ls = new LoadService();
        LoadResponse lRes = ls.load(loadRequest);
        Database db = new Database();
        Connection conn = db.getConnection();
        EventResponse eRes = es.getEvent(conn, ASTEROIDS1_EVENT_ID, ASTEROIDS1_USERNAME);
        Assertions.assertTrue(eRes.getMessage() == null || !eRes.getMessage().toLowerCase().contains("error"), "Result contains an error message");
        db.closeConnection(true);
    }

    @Test
    public void getEventsPass() throws DataAccessException, FileNotFoundException {
        UserRegisterResponse urRes = urs.register(this.urReq);
        Database db = new Database();
        Connection conn = db.getConnection();
        EventsResponse eRes = es.getEvents(conn, urRes.getUsername());
        assertNotNull(eRes.getData(), "why is our events data null?");
        db.closeConnection(true);
    }

    @Test
    public void getEventFail() throws DataAccessException, FileNotFoundException {
        UserRegisterResponse urRes = urs.register(this.urReq);
        try {
            urRes = urs.register(this.urReq);
        } catch (DataAccessException exception) {

        }
        Database db = new Database();
        Connection conn = db.getConnection();
        EventResponse eRes = null;
        eRes = es.getEvent(conn, "id", "wrong name");
        assertNull(eRes.getAssociatedUsername(), "why is there a name here");
        assertNull(eRes.getPersonID(), "why is there an id here");
        assertNull(eRes.getEventID(), "why is there an event id here");
        assertNotNull(eRes.getMessage(), "message was null OR its variable name did not match that of the expected JSon (see API)");
        assertTrue(eRes.getMessage().toLowerCase().contains("error"), "message did not contain 'error' string");
        db.closeConnection(false);
    }

    @Test
    public void getEventsFail() throws DataAccessException, FileNotFoundException {
        UserRegisterResponse urRes = urs.register(this.urReq);
        try {
            urRes = urs.register(this.urReq);
        } catch (DataAccessException exception) {

        }
        Database db = new Database();
        Connection conn = db.getConnection();
        EventsResponse eRes = es.getEvents(conn, "wrong name");
        assertNull(eRes.getData(), "Events list was given when the auth token was bad");
        assertNotNull(eRes.getMessage(), "message was null OR its variable name did not match that of the expected JSon (see API)");
        assertNotEquals(EMPTY_STRING, eRes.getMessage(), "message was empty string, should have contained an error message");
        assertTrue(eRes.getMessage().toLowerCase().contains("error"), "message did not contain 'error' string");
        db.closeConnection(false);
    }

}
