package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import Model.Event;
import Service.Response.*;

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
        Event events[] = eDao.findAll(associatedUsername); //find all events method here using this username
        EventsResponse esRes = null;
        if (events != null)
        {
            esRes = new EventsResponse(events, null, true);
        }
        return esRes;
    }

    /**
     * gets specified event
     * @param eventId int
     * @return eventEventIdResponse
     */
    public EventResponse getEvent(Connection conn, String eventId) throws DataAccessException
    {
        EventDAO eDao = new EventDAO(conn);
        Event event = eDao.find(eventId);
        EventResponse eRes = null;
        if (event != null)
        {
            eRes = new EventResponse(event.getAssociatedUsername(),
                    event, null, true);
        }
        return eRes;
    }
}
