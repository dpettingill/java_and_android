package Handler;

import DataAccess.DataAccessException;
import Service.Request.fillRequest;
import Service.Response.fillResponse;
import Service.fill;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;

public class FillHandler extends PostRequestHandler implements HttpHandler {
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
        super.handle();
        fillRequest fReq = this.getGson().fromJson(this.getReqData(), fillRequest.class);
        fill phil = new fill();
        fillResponse fRes = null;
        try {
            fRes = phil.fill(fReq);
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
    }
}
