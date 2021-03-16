package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {
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
        String urlPath = exchange.getRequestURI().toString();
        if (urlPath == null || urlPath.equals("/"))
        {
            urlPath = "/index.html";
        }
        String filePath = "web" + urlPath;
        File file = new File(filePath);
        if (file.exists())
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //send status code
            OutputStream respBody = exchange.getResponseBody();
            Files.copy(file.toPath(), respBody);
            respBody.close();
        }
        else
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0); //return 404 error
            filePath = "web/HTML/404.html";
            file = new File(filePath);
            OutputStream respBody = exchange.getResponseBody();
            Files.copy(file.toPath(), respBody);
            respBody.close();
        }
    }
}
