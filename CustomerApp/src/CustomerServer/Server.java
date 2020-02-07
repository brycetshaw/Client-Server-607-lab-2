package CustomerServer;

import CustomerServer.Controller.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private ExecutorService pool;

    public Server() {
        try {
            serverSocket = new ServerSocket(5000);
            pool = Executors.newFixedThreadPool(5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer();
    }

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
