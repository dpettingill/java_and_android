package Service;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Service.Response.*;

import java.sql.Connection;

/**
 * clears the database
 */
public class ClearService {
    /**
     * clears the database
     * @return clearResponse
     */
    public ClearResponse clear() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        ClearResponse clRes;
        try {
            db.clearTables(); //clear everything
            clRes = new ClearResponse("Clear succeeded", true);
        } catch (DataAccessException e){
            clRes = new ClearResponse(
                    "Error clearing the database",
                    false);
        }
        db.closeConnection(true);
        return clRes;
    }
}
