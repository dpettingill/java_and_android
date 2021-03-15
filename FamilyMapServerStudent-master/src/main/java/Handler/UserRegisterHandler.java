package Handler;

import DataAccess.DataAccessException;
import Service.Request.UserRegisterRequest;
import Service.Response.UserRegisterResponse;
import Service.UserRegisterService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * POST
 */
public class UserRegisterHandler extends PostRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.setExchange(exchange);
        super.handle();
        UserRegisterRequest urReq = this.getGson().fromJson(this.getReqData(), UserRegisterRequest.class);
        UserRegisterService ur = new UserRegisterService();
        UserRegisterResponse urRes = null;
        try {
            urRes = ur.register(urReq);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        String resData = this.getGson().toJson(urRes);
        super.sendResponse(resData);
    }
}

