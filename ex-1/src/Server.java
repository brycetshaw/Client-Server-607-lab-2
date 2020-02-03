import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Server.
 */
public class Server {

    private Socket aSocket;
    private ServerSocket serverSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;

    private ExecutorService pool;

    /**
     * Instantiates a new Server with a thread pool with fixed thread amounts.
     */
    public Server() {
        try {
            serverSocket = new ServerSocket(9898);
            pool = Executors.newFixedThreadPool(2);
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
        Server myServer = new Server();
        myServer.runServer();
    }

    /**
     * Run server by accepting a connection, instantiating the input and output streams, and calling the palindrome class.
     * If an IO exception occurs, then close the streams and the socket
     */
    public void runServer() {
        try {
            while (true) {
                aSocket = serverSocket.accept();
                System.out.println("Connection accepted!");
                socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
                socketOut = new PrintWriter(aSocket.getOutputStream(), true);

                Palendromer palendromer = new Palendromer(socketIn, socketOut);
                pool.execute(palendromer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Shutting down!");
            socketOut.close();
            socketIn.close();

            pool.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
