package service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Service.LoadService;
import Service.Request.UserRegisterRequest;
import Service.Response.UserRegisterResponse;
import Service.UserRegisterService;
import client.Client;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Service.Request.LoadRequest;
import Service.Response.LoadResponse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadServiceTest {
    private LoadService ls;
    private LoadRequest loadRequest;
    private Database db;

    private static final String EMPTY_STRING = "";

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        //We are creating a JsonReader from the LoadData.json file
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        Gson gson = new Gson();
        loadRequest = gson.fromJson(jsonReader, LoadRequest.class);
        ls = new LoadService();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void loadPass1() throws DataAccessException, FileNotFoundException {
        LoadResponse loadResponse = ls.load(loadRequest);
        assertNotNull(loadResponse.getMessage(), "message was null OR its variable name did not match that of the expected JSon (see API)");
        assertNotEquals(EMPTY_STRING, loadResponse.getMessage(), "message was empty string");
        assertFalse(loadResponse.getMessage().toLowerCase().contains("error"), "message contained an error");

    }

    @Test
    public void loadPass2() throws DataAccessException, FileNotFoundException {
        LoadResponse loadResponse = ls.load(loadRequest);
        int users = loadRequest.getUsers().length;
        int persons = loadRequest.getPersons().length;
        int events = loadRequest.getEvents().length;
        String[] message = loadResponse.getMessage().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        Assertions.assertEquals("Successfully added ", message[0], "First part of passoffresult message does not match API");
        Assertions.assertEquals(users, Integer.parseInt(message[1]), "Incorrect number of users added");
        Assertions.assertEquals(" users, ", message[2], "Second part of passoffresult message does not match API");
        Assertions.assertEquals(persons, Integer.parseInt(message[3]), "Incorrect number of persons added");
        Assertions.assertEquals(" persons, and ", message[4], "Third part of passoffresult message does not match API");
        Assertions.assertEquals(events, Integer.parseInt(message[5]), "Incorrect number of events added");
    }
}
