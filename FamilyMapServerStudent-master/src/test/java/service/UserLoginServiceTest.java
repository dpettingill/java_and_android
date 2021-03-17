package service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Service.Request.UserLoginRequest;
import Service.Request.UserRegisterRequest;
import Service.Response.UserLoginResponse;
import Service.Response.UserRegisterResponse;
import Service.UserLoginService;
import Service.UserRegisterService;
import client.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserLoginServiceTest {
    private UserRegisterRequest urReq;
    private UserRegisterService urs;
    private UserLoginRequest ulReq;
    private UserLoginService uls;

    private static final String EMPTY_STRING = "";

    @BeforeEach
    public void setUp() throws DataAccessException {
        urs = new UserRegisterService();
        urReq = new UserRegisterRequest("Dannyboi", "1234", "test@gmail.com",
                "Danny", "boi", "m");
        uls = new UserLoginService();
        ulReq = new UserLoginRequest("Dannyboi", "1234");
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void loginPass() throws DataAccessException, FileNotFoundException {
        UserRegisterResponse urRes = urs.register(this.urReq);
        UserLoginResponse ulRes = uls.login(this.ulReq);
        assertNotNull(ulRes.getAuthtoken(), "authtoken was null OR its variable name did not match that of the expected JSon (see API)");
        assertNotEquals(EMPTY_STRING, ulRes.getAuthtoken(), "authtoken was empty string, expected non-empty authtoken string");
    }

    @Test
    public void loginFail() throws DataAccessException, FileNotFoundException {
        UserLoginResponse ulRes = uls.login(this.ulReq);
        assertNull(ulRes.getAuthtoken(), "authtoken was not null when it should have been (see API)");
        assertNull(ulRes.getPersonID(), "personID was not null when it should have been (see API)");
        assertNotNull(ulRes.getMessage(), "message was null OR its variable name did not match that of the expected JSon (see API)");
        assertTrue(ulRes.getMessage().toLowerCase().contains("error"), "message did not contain 'error' string");
    }

}
