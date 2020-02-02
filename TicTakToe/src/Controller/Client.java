package Controller;

import Model.Model;

import java.io.*;
import java.net.Socket;

public class Client {


    private Socket aSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private BufferedReader stdIn;

    private ObjectInputStream objectInputStream;


    private Model theModel;

    public Client(String serverName, int portNumber) throws IOException{

            aSocket = new Socket(serverName, portNumber);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
//            socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            socketOut = new PrintWriter(aSocket.getOutputStream(), true);
        System.out.println("here");
            objectInputStream = new ObjectInputStream(aSocket.getInputStream());
            System.out.println("Server Connected.");
    }


    public static void main(String[] args) {
        boolean hasConnected = false;
        while (!hasConnected) {

            try {

                Client aClient = new Client("localhost", 9090);
                aClient.playGame();
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


    private void playGame() {
        System.out.println("what is yer name?");
        try {
            String line = stdIn.readLine();
            socketOut.println(line);
            theModel = (Model) objectInputStream.readObject();
            System.out.println("got the model!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
