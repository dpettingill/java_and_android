package Handler;

import com.sun.net.httpserver.Headers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class LoadHandler {

//    try {
//        if (exchange.getRequestMethod().toLowerCase().equals("post")) {
//            Headers reqHeaders = exchange.getRequestHeaders(); // Get the HTTP request headers
//            if (reqHeaders.containsKey("Authorization")) { // Check to see if an "Authorization" header is present
//                String authToken = reqHeaders.getFirst("Authorization"); // Extract the auth token from the "Authorization" header
//
//                if (authToken.equals("afj232hj2332")) {
//
//                    // This is the JSON data we will return in the HTTP response body
//                    // (this is unrealistic because it always returns the same answer).
//                    String respData =
//                            "{ \"game-list\": [" +
//                                    "{ \"name\": \"fhe game\", \"player-count\": 3 }," +
//                                    "{ \"name\": \"work game\", \"player-count\": 4 }," +
//                                    "{ \"name\": \"church game\", \"player-count\": 2 }" +
//                                    "]" +
//                                    "}";
//
//                    // Start sending the HTTP response to the client, starting with
//                    // the status code and any defined headers.
//                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//
//                    // Now that the status code and headers have been sent to the client,
//                    // next we send the JSON data in the HTTP response body.
//
//                    // Get the response body output stream.
//                    OutputStream respBody = exchange.getResponseBody();
//                    // Write the JSON string to the output stream.
//                    writeString(respData, respBody);
//                    // Close the output stream.  This is how Java knows we are done
//                    // sending data and the response is complete/
//                    respBody.close();
//
//                    success = true;
//                }
//            }
//        }
//
//        if (!success) {
//            // The HTTP request was invalid somehow, so we return a "bad request"
//            // status code to the client.
//            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//            // Since the client request was invalid, they will not receive the
//            // list of games, so we close the response body output stream,
//            // indicating that the response is complete.
//            exchange.getResponseBody().close();
//        }
//    }
//        catch (
//    IOException e) {
//        // Some kind of internal error has occurred inside the server (not the
//        // client's fault), so we return an "internal server error" status code
//        // to the client.
//        exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
//        // Since the server is unable to complete the request, the client will
//        // not receive the list of games, so we close the response body output stream,
//        // indicating that the response is complete.
//        exchange.getResponseBody().close();
//
//        // Display/log the stack trace
//        e.printStackTrace();
//    }
//}
//
//    /*
//        The writeString method shows how to write a String to an OutputStream.
//    */
//    private void writeString(String str, OutputStream os) throws IOException {
//        OutputStreamWriter sw = new OutputStreamWriter(os);
//        sw.write(str);
//        sw.flush();
//    }
}
