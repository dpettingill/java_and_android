package Handler;
import java.io.*;
import java.net.*;

import DataAccess.DataAccessException;
import Service.Request.userRegisterRequest;
import Service.Response.userRegisterResponse;
import Service.userRegister;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * POST
 */
public class UserRegisterHandler implements HttpHandler {
    /**
     *  Handles HTTP requests containing the "/games/list" URL path.
     *  The "exchange" parameter is an HttpExchange object, which is
     *  defined by Java.
     *  In this context, an "exchange" is an HTTP request/response pair
     *  (i.e., the client and server exchange a request and response).
     *  The HttpExchange object gives the handler access to all of the
     *  details of the HTTP request (Request type [GET or POST],
     *  request headers, request body, etc.).
     *  The HttpExchange object also gives the handler the ability
     *  to construct an HTTP response and send it back to the client
     *  (Status code, headers, response body, etc.).
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws NullPointerException if exchange is <code>null</code>
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody(); // Get the HTTP request headers
                String reqData = readString(reqBody);
                Gson gson = new Gson();
                userRegisterRequest urReq = gson.fromJson(reqData, userRegisterRequest.class);
                userRegister ur = new userRegister();

                userRegisterResponse urRes = ur.register(urReq);
                String resData = gson.toJson(urRes); //serialize to json
                Headers resHeaders = exchange.getResponseHeaders();
                String[] tokens = resData.split(",");
                resHeaders.add("Authorization", tokens[0]);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //send status code
                OutputStream respBody = exchange.getResponseBody();
                writeString(resData, respBody); // Write the JSON string to the output stream.
                respBody.close();

                success = true;
            }

            if (!success) { // The HTTP request was invalid somehow, so we return a "bad request"
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException | DataAccessException e) { // Some kind of internal error has occurred inside the server (not the // client's fault)
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace(); // Display/log the stack trace
        }
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    /*
		The readString method shows how to read a String from an InputStream.
	*/
    private String readString(InputStream is) throws IOException {
            StringBuilder sb = new StringBuilder();
            InputStreamReader sr = new InputStreamReader(is);
            char[] buf = new char[1024];
            int len;
            while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
            }
            return sb.toString();
        }
}

