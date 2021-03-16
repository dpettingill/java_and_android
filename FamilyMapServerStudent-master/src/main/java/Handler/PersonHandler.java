package Handler;

import DataAccess.DataAccessException;
import Model.AuthToken;
import Service.PersonService;
import Service.Response.PersonResponse;
import Service.Response.PersonsResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public class PersonHandler extends GetRequestHandler implements HttpHandler {
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
            PersonService personService = new PersonService();
            AuthToken authToken = this.getAuthToken();
            PersonResponse singlePersonRes = null;
            PersonsResponse multiplePersonsRes = null;

            if (this.getTokensLength() == 3 && authToken != null) //get indiv event
            {
                singlePersonRes = personService.getPerson(this.getConn(), this.getTokens()[2]); //getToken()[2] = personId
            }
            else if (this.getTokensLength() == 2 && authToken != null)  //get all events
            {
                multiplePersonsRes = personService.getPersons(this.getConn(), authToken.getUsername());
            }

            if (authToken == null || (singlePersonRes == null && multiplePersonsRes == null))
            {
                singlePersonRes = new PersonResponse("Error getting the person(s)", false);
            }
            resData = singlePersonRes == null ? this.getGson().toJson(multiplePersonsRes) : this.getGson().toJson(singlePersonRes);

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
