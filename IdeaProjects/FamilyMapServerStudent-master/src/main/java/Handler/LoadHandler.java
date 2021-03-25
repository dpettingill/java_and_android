package Handler;

import DataAccess.DataAccessException;
import Service.LoadService;
import Request.LoadRequest;
import Response.LoadResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class LoadHandler extends PostRequestHandler implements HttpHandler {

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
        LoadRequest lReq = this.getGson().fromJson(this.getReqData(), LoadRequest.class);
        LoadService ls = new LoadService();
        LoadResponse lRes = null;
        try {
            lRes = ls.load(lReq);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        String resData = this.getGson().toJson(lRes);
        super.sendResponse(resData);
    }
}
