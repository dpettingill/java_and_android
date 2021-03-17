package Handler;

import DataAccess.DataAccessException;
import Service.Response.FillResponse;
import Service.FillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;

public class FillHandler extends RequestHandler implements HttpHandler {
    /**
     * Handle the given request and generate an appropriate response.
     * See {@link HttpExchange} for a description of the steps
     * involved in handling an exchange.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws NullPointerException if exchange is <code>null</code>
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.setExchange(exchange);
        String reqPath = this.getExchange().getRequestURI().getPath();
        String[] tokens = reqPath.split("/");

        FillService phil = new FillService();
        FillResponse fRes = null;
        try {
            if (tokens.length == 4)
            {
                fRes = phil.fill(tokens[2], Integer.parseInt(tokens[3]));
            }
            else
            {
                fRes = phil.fill(tokens[2], -1);
            }
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        String resData = this.getGson().toJson(fRes);
        super.sendResponse(resData);
    }
}
