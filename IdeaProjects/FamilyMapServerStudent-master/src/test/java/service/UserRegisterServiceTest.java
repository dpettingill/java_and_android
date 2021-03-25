package service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.UserRegisterRequest;
import Response.UserRegisterResponse;
import Service.UserRegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegisterServiceTest {
    private UserRegisterRequest urReq;
    private UserRegisterService urs;

    private static final String EMPTY_STRING = "";

    @BeforeEach
    public void setUp() throws DataAccessException {
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
    public void registerPass() throws DataAccessException, FileNotFoundException {
        UserRegisterResponse urRes = urs.register(this.urReq);
        assertNotNull(urRes.getauthtoken(), "authtoken was null OR its variable name did not match that of the expected JSon (see API)");
        assertNotEquals(EMPTY_STRING, urRes.getauthtoken(), "authtoken was empty string, expected non-empty authtoken string");
    }

    @Test
    public void registerFail() throws DataAccessException, FileNotFoundException {
        UserRegisterResponse urRes = urs.register(this.urReq);
        urRes = urs.register(this.urReq);
        assertNull(urRes.getauthtoken(), "authtoken was not null when it should have been (see API)");
        assertNull(urRes.getPersonID(), "personID was not null when it should have been (see API)");
        assertNotNull(urRes.getMessage(), "message was null OR its variable name did not match that of the expected JSon (see API)");
        assertTrue(urRes.getMessage().toLowerCase().contains("error"), "message did not contain 'error' string");
    }

}