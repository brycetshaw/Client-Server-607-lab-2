package Server.Controller;

import Server.Model.Board;

import java.io.*;
import java.net.Socket;

public class Controller implements Runnable {

    private ObjectOutputStream clientObjectOutput;
    private ObjectInputStream clientObjectInput;
    private String name;
    private PlayerSocket player1;
    private PlayerSocket player2;
    private Board theBoard;
    private char mark;

    public Controller(Board theBoard, Socket clientSocket1, Socket clientSocket2) {
        this.theBoard = theBoard;
        this.player1 = new PlayerSocket(clientSocket1);
        this.player1 = new PlayerSocket(clientSocket2);
    }


    @Override
    public void run() {
        setObjectStreams();

        try {
            clientObjectOutput.writeObject(theModel);
            name = (String) clientObjectInput.readObject();
            synchronized (theModel){
                mark = theModel.addPlayer(name);
                theModel.notifyAll();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(" " + name + " waiting for the model");

        while (true) {
            try {
                play();
            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    private void play() throws InterruptedException, IOException, ClassNotFoundException {
        synchronized (theModel) {
            if (theModel.getToPlay() == mark) {
                clientObjectOutput.writeObject(theModel);
            }
            theModel = (Model) clientObjectInput.readObject();
            theModel.notifyAll();
        }
    }

    private void setObjectStreams() {
        try {
            clientObjectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            clientObjectInput = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



