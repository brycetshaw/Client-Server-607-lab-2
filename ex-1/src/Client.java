import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The type Client.
 */
public class Client {
    private PrintWriter socketOut;
    private Socket palinSocket;
    private BufferedReader stdIn;
    private BufferedReader socketIn;

    /**
     * Instantiates a new Client.
     *
     * @param serverName the server name
     * @param portNumber the port number
     */
    protected Client(String serverName, int portNumber) {
        try {
            palinSocket = new Socket(serverName, portNumber);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            socketIn = new BufferedReader(new InputStreamReader(
                    palinSocket.getInputStream()));
            socketOut = new PrintWriter((palinSocket.getOutputStream()), true);
        } catch (IOException e) {
            System.err.println(e.getStackTrace().toString());
        }
    }

    /**
     * Communicate with the server to take in input from the user and get the proper output
     */
    private void communicate() {
        String line = "";
        String response = "";
        boolean running = true;
        while (running) {
            try {
                System.out.println("please enter a word: ");
                line = stdIn.readLine();
                    socketOut.println(line);
                    response = socketIn.readLine();
                System.out.println(response);
                running = !response.equals("Good Bye!");
            } catch (IOException e) {
                System.out.println("Sending error: " + e.getMessage());
            }
        }
        try {
            stdIn.close();
            socketIn.close();
            socketOut.close();
        } catch (IOException e) {
            System.out.println("Closing error: " + e.getMessage());
        }

    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Client aClient = new Client("localhost", 9898);
        aClient.communicate();
    }
}