package Client;


import Client.Controller.Controller;
import Model.Game;
import Client.View.View;

import java.io.*;
import java.net.Socket;

public class Client {


    private Socket aSocket;

    public Client(String serverName, int portNumber) throws IOException {
        aSocket = new Socket(serverName, portNumber);

        View theView = new View();
        Controller controller = new Controller(aSocket, theView);
    }


    public static void main(String[] args) {


        boolean hasConnected = false;
        while (!hasConnected) {
            try {
                Client aClient = new Client("3.87.99.9", 9090);
//                Client aClient = new Client("localhost", 9090);
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
