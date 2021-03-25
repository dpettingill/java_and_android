package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import Model.Event;
import Response.*;

import java.sql.Connection;

/**
 * returns all events
 */
public class EventService {
    /**
     * gets all events of user's family members
     * @return eventResponse
     */
    public EventsResponse getEvents(Connection conn, String associatedUsername) throws DataAccessException {
        EventDAO eDao = new EventDAO(conn);
        Event[] events = eDao.findAll(associatedUsername); //find all events method here using this username
        EventsResponse esRes = null;
        if (events != null)
        {
            esRes = new EventsResponse(events, null, true);
        }
        else
        {
            esRes = new EventsResponse("Error getting events", false);
        }
        return esRes;
    }

    /**
     * gets specified event
     * @param eventId int
     * @return eventEventIdResponse
     */
    public EventResponse getEvent(Connection conn, String eventId, String associatedUsername) throws DataAccessException
    {
        EventDAO eDao = new EventDAO(conn);
        Event event = eDao.find(eventId);
        EventResponse eRes = null;
        if (event == null || !event.getAssociatedUsername().equals(associatedUsername))
        {
            eRes = new EventResponse("Error getting the event", false);
        }
        else if (event != null)
        {
            eRes = new EventResponse(event.getAssociatedUsername(),
                    event.getEventID(), event.getPersonID(), event.getLatitude(),
                    event.getLongitude(), event.getCountry(), event.getCity(),
                    event.getEventType(), event.getYear(), null, true);
        }
        return eRes;
    }
}
