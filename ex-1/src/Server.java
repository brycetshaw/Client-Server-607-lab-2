import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private Socket aSocket;
    private ServerSocket serverSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;

    public Server() {
        try {
            serverSocket = new ServerSocket(8099);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        Server myServer = new Server();
        try {
            // accepting the connection
            myServer.aSocket = myServer.serverSocket.accept();
            System.out.println("Connection accepted by server!");
            myServer.socketIn = new BufferedReader(new InputStreamReader(myServer.aSocket.getInputStream()));
            myServer.socketOut = new PrintWriter((myServer.aSocket.getOutputStream()), true);

            Capitalizer cap1 = new Capitalizer (myServer.socketIn, myServer.socketOut);
            //Creating a thread for a client
            Thread t1 = new Thread (cap1);
            t1.start();

            // accepting the connection
            myServer.aSocket = myServer.serverSocket.accept();
            System.out.println("Connection accepted by server!");
            myServer.socketIn = new BufferedReader(new InputStreamReader(myServer.aSocket.getInputStream()));
            myServer.socketOut = new PrintWriter((myServer.aSocket.getOutputStream()), true);

            Capitalizer cap2 = new Capitalizer (myServer.socketIn, myServer.socketOut);
            //Creating a thread for a client
            Thread t2 = new Thread (cap2);
            t2.start();

            try {
                t1.join();
                t2.join();
            }catch (InterruptedException e) {
                System.out.println("Thread Error!");
            }

            myServer.socketIn.close();
            myServer.socketOut.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
