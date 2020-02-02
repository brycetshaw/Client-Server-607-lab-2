package Controller;


import Model.Model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private BufferedReader xSocketInput;
    private BufferedReader ySocketInput;

    private ObjectOutputStream xObjectOutput;
    private ObjectOutputStream yObjectOutput;

    private ServerSocket serverSocket;

    private Socket xSocket;
    private Socket ySocket;

    private Model theModel;

    private ExecutorService pool;

    public Server(int port) {
        try {

            serverSocket = new ServerSocket(port);
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

            xSocket = serverSocket.accept();
            System.out.println("x player joined");
            ySocket = serverSocket.accept();
            System.out.println("y player joined");



            xSocketInput = new BufferedReader(new InputStreamReader(xSocket.getInputStream()));
            ySocketInput = new BufferedReader(new InputStreamReader(ySocket.getInputStream()));

            xObjectOutput = new ObjectOutputStream(xSocket.getOutputStream());
            yObjectOutput = new ObjectOutputStream(ySocket.getOutputStream());

            StringBuffer xName = new StringBuffer(xSocketInput.readLine());
            StringBuffer yName = new StringBuffer(ySocketInput.readLine());

            theModel = new Model(xName.toString(),yName.toString());

            xObjectOutput.writeObject(theModel);
            yObjectOutput.writeObject(theModel);

            while (true){

            }




        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
