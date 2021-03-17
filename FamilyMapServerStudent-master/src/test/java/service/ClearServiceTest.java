package service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Service.ClearService;
import Service.LoadService;
import Service.Request.LoadRequest;
import Service.Request.UserLoginRequest;
import Service.Request.UserRegisterRequest;
import Service.Response.ClearResponse;
import Service.Response.LoadResponse;
import Service.Response.UserLoginResponse;
import Service.Response.UserRegisterResponse;
import Service.UserLoginService;
import Service.UserRegisterService;
import client.Client;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffresult.ClearResult;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClearServiceTest {
    private LoadResponse lRes;
    private ClearService cs;
    private static final String EMPTY_STRING = "";

    @BeforeEach
    public void setUp() throws DataAccessException, FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        LoadRequest loadRequest = gson.fromJson(jsonReader, LoadRequest.class);
        LoadService ls = new LoadService();
        LoadResponse lRes = ls.load(loadRequest);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void clearPass() throws DataAccessException, FileNotFoundException {
        ClearService cs = new ClearService();
        ClearResponse cRes = cs.clear();
        assertNotNull(cRes.getMessage(), "Clear message was null OR its variable name did not match that of the expected JSon (see API)");
        assertNotEquals(EMPTY_STRING, cRes.getMessage(), "Clear message was empty string");
    }

    @Test
    public void clearFail() throws DataAccessException, FileNotFoundException {
        ClearService cs = new ClearService();
        ClearResponse cRes = cs.clear();
        UserLoginService ls = new UserLoginService();
        UserLoginResponse ulRes = ls.login(new UserLoginRequest("sheila", "password"));
        assertNull(ulRes.getAuthtoken(), "authtoken was not null when it should have been (see API)");
        assertNull(ulRes.getPersonID(), "personID was not null when it should have been (see API)");
        assertNotNull(ulRes.getMessage(), "message was null OR its variable name did not match that of the expected JSon (see API)");
        assertTrue(ulRes.getMessage().toLowerCase().contains("error"), "message did not contain 'error' string");
    }

}
