package Handler;

import DataAccess.DataAccessException;
import Response.ClearResponse;
import Service.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ClearHandler extends RequestHandler implements HttpHandler {
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
        ClearService cl = new ClearService();
        ClearResponse clRes = null;
        try {
            clRes = cl.clear();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        String resData = this.getGson().toJson(clRes);
        super.sendResponse(resData);
    }
}
