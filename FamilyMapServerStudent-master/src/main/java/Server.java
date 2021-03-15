import java.io.*;
import java.net.*;

import Handler.*;
import com.sun.net.httpserver.*;

/*
	This example demonstrates the basic structure of the Family Map Server
	(although it is for a fictitious "Ticket to Ride" game, not Family Map).
	The example is greatly simplfied to help you more easily understand the
	basic elements of the server.

	The Server class is the "main" class for the server (i.e., it contains the
		"main" method for the server program).
	When the server runs, all command-line arguments are passed in to Server.main.
	For this server, the only command-line argument is the port number on which
		the server should accept incoming client connections.
*/
public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    // This method initializes and runs the server.
    // The "portNumber" parameter specifies the port number on which the
    // server should accept incoming client connections.
    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server");

        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null); // This line is necessary, but its function is unimportant for our purposes.
        System.out.println("Creating contexts");

        server.createContext("/", new FileHandler());
        server.createContext("/user/register", new UserRegisterHandler());
        server.createContext("/user/login", new UserLoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/event", new EventHandler());

        /**
         * server.createContext("/load"), new LoadHandler();
         * server.createContext("/person/[personId]"), new PersonHandler();
         * server.createContext("/person"), new PersonsHandler();
         */

        System.out.println("Starting server"); // Log message indicating the HttpServer soon will be accepting incoming client conns
        server.start();
        System.out.println("Server started"); // Log message indicating the server has successfully started.
    }

    // "main" method for the server program
    // "args" should contain one command-line argument, which is the port number
    // on which the server should accept incoming client connections.
    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}

