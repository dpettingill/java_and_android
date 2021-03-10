package Handler;

import DataAccess.DataAccessException;
import Service.Response.clearResponse;
import Service.Response.userLoginResponse;
import Service.clear;
import Service.userLogin;
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
        clear cl = new clear();
        clearResponse clRes = null;
        try {
            clRes = cl.clear();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        String resData = this.getGson().toJson(clRes);
        super.sendResponse(resData);
    }
}
