package Server;


import Model.Board;
import Server.Controller.*;


import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;

    private Board theBoard;

    private ExecutorService pool;

    public Server(int port) {
        try {
//            InetAddress address = Inet4Address.getByName("3.87.99.9");
            serverSocket = new ServerSocket(  port);
            pool = Executors.newFixedThreadPool(2);
            System.out.println("Tic Tack Toe Server is running on port: " + port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Server server = new Server(9090);

        server.runServer();
    }

    private void runServer() {
        try {

            while (true) {
                Socket xSocket = serverSocket.accept();
                Socket ySocket = serverSocket.accept();
                pool.execute(new Controller( xSocket, ySocket));
                System.out.println("game started!");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
