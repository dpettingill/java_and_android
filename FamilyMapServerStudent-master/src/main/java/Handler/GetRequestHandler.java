package Handler;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import com.sun.net.httpserver.Headers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Connection;

public class GetRequestHandler extends RequestHandler {
    private String[] tokens;
    private Database db;
    private Connection conn;
    private AuthToken at;

    public void handle() throws IOException, DataAccessException {
        String reqPath = this.getExchange().getRequestURI().getPath();
        tokens = reqPath.split("/");
        db = new Database();
        conn = db.getConnection();
        Headers reqHeaders = this.getExchange().getRequestHeaders();
        if (reqHeaders.containsKey("Authorization"))
        {
            String authToken = reqHeaders.getFirst("Authorization");
            AuthTokenDAO atDao = new AuthTokenDAO(conn);
            at = atDao.find(authToken, "AuthToken");
        }
    }

    public AuthToken getAuthToken() {
        return at;
    }

    public Database getDb() {
        return db;
    }

    public Connection getConn() {
        return conn;
    }

    public String[] getTokens() { return this.tokens; }

    public int getTokensLength() { return this.tokens.length; }
}

