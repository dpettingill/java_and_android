package Handler;

import DataAccess.DataAccessException;
import Service.Request.userLoginRequest;
import Service.Response.userLoginResponse;
import Service.userLogin;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class UserLoginHandler extends PostRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.setExchange(exchange);
        super.handle();
        userLoginRequest urReq = this.getGson().fromJson(this.getReqData(), userLoginRequest.class);
        userLogin ur = new userLogin();
        userLoginResponse urRes = null;
        try {
            urRes = ur.login(urReq);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        String resData = this.getGson().toJson(urRes);
        super.sendResponse(resData);
    }
}
