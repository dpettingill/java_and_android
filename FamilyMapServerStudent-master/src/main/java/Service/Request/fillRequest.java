package Service.Request;

/**
 * deserializes a fill Request
 */
public class fillRequest {
    /**
     * username of intended user
     */
    private String username;
    /**
     * num generations
     */
    private int generations;


    /**
     * getter for the username info
     * @return string
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter for the generations info
     * @return int
     */
    public int getGenerations() {
        return generations;
    }
}
