package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class PostRequestHandler extends RequestHandler {
    private String reqData;

    public void handle() throws IOException {
        this.setSuccess(false);
        try {
            if (this.getExchange().getRequestMethod().toLowerCase().equals("post"))
            {
                this.setGson();
                InputStream reqBody = this.getExchange().getRequestBody(); // Get the HTTP request headers
                reqData = readString(reqBody);
            }
        } catch (IOException e) { // Some kind of internal error has occurred inside the server (not the // client's fault)
            this.getExchange().sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            this.getExchange().getResponseBody().close();
            e.printStackTrace(); // Display/log the stack trace
        }
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

    public String getReqData() {
        return reqData;
    }
}
