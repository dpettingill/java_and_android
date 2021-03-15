package DataAccess;
import Model.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * used to access events from Events table in db
 */
public class EventDAO {
    /**
     * Connection to database
     */
    private final Connection conn;

    /**
     * constructor
     * @param conn db connection
     */
    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * inserts event into db
     * @param event person's life event
     * @throws DataAccessException
    */
    public void insert(Event event) throws DataAccessException
    {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Events (Id, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getId());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonId());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     *
     * @param eventID String
     * @return Event
     * @throws DataAccessException
     */
    public Event find(String eventID) throws DataAccessException
    {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE Id = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("Id"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * finds all events associated with a user
     * @param username
     * @return
     */
    public Event[] findAll(String username) throws DataAccessException {
        Event[] events = new Event[100];
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                Event event = new Event(rs.getString("Id"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                if (i > events.length - 1) //check if i is out of bounds
                {
                    events = Arrays.copyOf(events, events.length * 2);
                }
                events[i] = event; //don't forget to make sure events is big enough
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return events;
    }

    /**
     * deletes event from db
     * @param event person's life event
     */
    public void delete(Event event)
    {

    }

    /**
     * clears the Events table completely
     */
    public void clear()
    {

    }

    /**
     * clears associated events
     * @param username
     * @throws DataAccessException
     */
    public boolean clear(String username) throws SQLException
    {
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM Events WHERE AssociatedUsername = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            int rows_affected = stmt.executeUpdate();
            return (rows_affected == 1);
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }
}








