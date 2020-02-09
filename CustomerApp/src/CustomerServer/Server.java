package CustomerServer;

import CustomerServer.Controller.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class and its instance methods and variables.
 * This class is the main class for the server side.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020/02/08
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private ExecutorService pool;

    /**
     * Instantiates a new Server.
     */
    public Server() {
        try {
            serverSocket = new ServerSocket(5000);
            pool = Executors.newFixedThreadPool(5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.runServer();
    }

    /**
     * Run server.
     */
    public void runServer() {
        try {
            while (true) {
                socket = serverSocket.accept();
                Controller controller = new Controller(socket);
                pool.execute(controller);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
