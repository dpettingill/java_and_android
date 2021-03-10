package Handler;

import DataAccess.DataAccessException;
import Service.Request.userRegisterRequest;
import Service.Response.userRegisterResponse;
import Service.userRegister;
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
        userRegisterRequest urReq = this.getGson().fromJson(this.getReqData(), userRegisterRequest.class);
        userRegister ur = new userRegister();
        userRegisterResponse urRes = null;
        try {
            urRes = ur.register(urReq);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        String resData = this.getGson().toJson(urRes);
        super.sendResponse(resData);
    }
}

