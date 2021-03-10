package Service;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Service.Response.*;

import java.sql.Connection;

/**
 * clears the database
 */
public class clear {
    /**
     * clears the database
     * @return clearResponse
     */
    public clearResponse clear() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        clearResponse clRes;
        try {
            db.clearTables(); //clear everything
            clRes = new clearResponse(null, true);
        } catch (DataAccessException e){
            clRes = new clearResponse(
                    "Error clearing the database",
                    false);
        }
        db.closeConnection(true);
        return clRes;
    }
}
