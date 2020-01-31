import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {


    private Socket aSocket;
    private ServerSocket serverSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;

    private ExecutorService pool;

    public Server() {
        try {
            serverSocket = new ServerSocket(9898);
            pool = Executors.newFixedThreadPool(2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void runServer () {

        try{

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

            // TODO how do we reach this unreachable code?
            System.out.println("Shutting down!");
            socketOut.close();
            socketIn.close();

            pool.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        Server myServer = new Server();
        myServer.runServer();

    }
}
