package Client;


import Client.Controller.Controller;
import Model.Game;
import Client.View.View;

import java.io.*;
import java.net.Socket;

public class Client {


    private Socket aSocket;
//
//    private BufferedReader stdIn;
//
//    private ObjectInputStream objectInputStream;
//    private ObjectOutputStream objectOutputStream;
//
//
//    private Game theGame;
//    private Board theBoard;
//    private char mark;
//    private String name;

    public Client(String serverName, int portNumber) throws IOException {
        aSocket = new Socket(serverName, portNumber);

        View theView = new View();
        Game theGame = new Game();
        Controller controller = new Controller(aSocket, theView, theGame);
//        stdIn = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("Waiting for another player to join");
//        objectInputStream = new ObjectInputStream(aSocket.getInputStream());
//        objectOutputStream = new ObjectOutputStream(aSocket.getOutputStream());
//        System.out.println("Server Connected.");
//        joinGame();
    }


    public static void main(String[] args) {


        boolean hasConnected = false;
        while (!hasConnected) {
            try {
                Client aClient = new Client("localhost", 9091);

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

//
//    private void joinGame() {
//
//
//        try {
//            theBoard = (Board) objectInputStream.readObject();
//            System.out.println("Welcome to tic tak toe. what is your name?");
//            name = th
//            mark = theGame.addPlayer(theBoard, name);
//            objectOutputStream.writeObject(theBoard);
//
//            System.out.println("hi " + name + ". You are" +
//                    " playing as " + mark + "\n");
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void playGame() {
//        try {
//
//            while (true) {
//                theModel = (Model) objectInputStream.readObject();
//                theModel.display();
//                int[] move = new int[2];
//                do {
//                    System.out.println("input x:");
//                    move[0] = Integer.valueOf(stdIn.readLine());
//                    System.out.println("input y:");
//                    move[1] = Integer.valueOf(stdIn.readLine());
//                } while (!theModel.play(move, mark));
//                objectOutputStream.writeObject(theModel);
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

}
