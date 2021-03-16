package Handler;

import DataAccess.DataAccessException;
import Model.AuthToken;
import Service.EventService;
import Service.Response.EventResponse;
import Service.Response.EventsResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public class EventHandler extends GetRequestHandler implements HttpHandler {
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
        String resData = null;
        try {
            super.handle();
            EventService eventService = new EventService();
            AuthToken authToken = this.getAuthToken();
            EventResponse singleEventRes = null;
            EventsResponse multipleEventsRes = null;
            if (this.getTokensLength() == 3 && authToken != null) //get indiv event
            {
                singleEventRes = eventService.getEvent(this.getConn(), this.getTokens()[2], authToken.getUsername()); //getToken()[2] = eventId
            }
            else if (this.getTokensLength() == 2 && authToken != null)  //get all events
            {
                multipleEventsRes = eventService.getEvents(this.getConn(), authToken.getUsername());
            }

            if (authToken == null || (singleEventRes == null && multipleEventsRes == null))
            {
                singleEventRes = new EventResponse("Error with the event(s)", false);
            }
            resData = singleEventRes == null ? this.getGson().toJson(multipleEventsRes) : this.getGson().toJson(singleEventRes);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        super.sendResponse(resData);
        try {
            this.getDb().closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
