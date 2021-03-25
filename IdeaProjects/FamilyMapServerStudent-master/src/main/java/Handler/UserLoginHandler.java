package Handler;

import DataAccess.DataAccessException;
import Request.UserLoginRequest;
import Response.UserLoginResponse;
import Service.UserLoginService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class UserLoginHandler extends PostRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.setExchange(exchange);
        super.handle();
        UserLoginRequest urReq = this.getGson().fromJson(this.getReqData(), UserLoginRequest.class);
        UserLoginService ur = new UserLoginService();
        UserLoginResponse urRes = null;
        try {
            urRes = ur.login(urReq);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        String resData = this.getGson().toJson(urRes);
        super.sendResponse(resData);
    }
}
