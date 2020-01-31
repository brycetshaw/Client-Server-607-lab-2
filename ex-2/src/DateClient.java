import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DateClient {
    private PrintWriter socketOut;
    private Socket aSocket;
    private BufferedReader stdIn;
    private BufferedReader socketIn;

    protected DateClient(String serverName, int portNumber) throws IOException {

        aSocket = new Socket(serverName, portNumber);
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
        socketOut = new PrintWriter((aSocket.getOutputStream()), true);
        System.out.println("Server Connected.");
    }

    private void communicate() {

        String line = "";
        String response = "";
        boolean running = true;
        while (running) {
            try {
                System.out.println("Please select an Option (DATE/TIME) ");
                line = stdIn.readLine();


                socketOut.println(line);
                if (line.equals("QUIT")) {
                    break;
                }
                response = socketIn.readLine();
                System.out.println(response);

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

    public static void main(String[] args) {
        boolean hasConnected = false;
        while (!hasConnected) {

            try {
                DateClient aClient = new DateClient("localhost", 9090);
                aClient.communicate();
                hasConnected = true;


            } catch (IOException e) {
                System.out.println("Connection Failed. Retrying connection in 2s");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }
        }


    }
}