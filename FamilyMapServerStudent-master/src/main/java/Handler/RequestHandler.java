package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class RequestHandler {
    private HttpExchange exchange;
    private boolean success = false;
    private Gson gson = new Gson();

    public void sendResponse(String resData) throws IOException {
        try {
            Headers resHeaders = exchange.getResponseHeaders();
            //I don't think I actually need this
//            String[] tokens = resData.split(",");
//            resHeaders.add("Authorization", tokens[0]);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //send status code
            OutputStream respBody = exchange.getResponseBody();
            writeString(resData, respBody); // Write the JSON string to the output stream.
            respBody.close();
            success = true;
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace(); // Display/log the stack trace
        }

        if (!success) { // The HTTP request was invalid somehow, so we return a "bad request"
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
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
    public String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    public HttpExchange getExchange() {
        return exchange;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setExchange(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson() { this.gson = new Gson(); }
}
