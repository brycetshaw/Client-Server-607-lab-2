package Client.Controller;

import Model.*;
import Client.View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Controller {

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    private Game theGame;
    private View theView;
    private Board theBoard;
    private char mark;
    private String name;


    public Controller(Socket aSocket, View theView, Game theGame) throws IOException {

        mark = " ".charAt(0);
        this.theGame = theGame;
        this.theView = theView;
        objectInputStream = new ObjectInputStream(aSocket.getInputStream());
        objectOutputStream = new ObjectOutputStream(aSocket.getOutputStream());
        name = theView.getName();

        theView.setVisible(true);
        theBoard = (Board) readIn(objectInputStream);
        theView.updateButtons(theBoard.getTheBoard());
        addButtonListeners();
        mark = theGame.addPlayer(theBoard, name);
        writeOut(theBoard, objectOutputStream);

        listeningState();
        playingState();


//        theBoard = (Board) readIn(objectInputStream);

//        theView.addNewGameListener(new TikTakToeController.anotherGameListener());

    }

    private void playingState() {
        theView.updateButtons(theBoard.getTheBoard());
        theView.enableButtons();
    }

    private void listeningState() {

        theView.updateButtons(theBoard.getTheBoard());
        theView.disableButtons();
        theBoard = (Board) readIn(objectInputStream);
        playingState();
    }

    private void sendResponse(){


        writeOut(theBoard, objectOutputStream);
        theView.updateButtons(theBoard.getTheBoard());//TODO breaks MVC a little bit.
        listeningState();
    }

    private class boardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String response = actionEvent.getActionCommand();
            System.out.println(response);
            int[] move = {Integer.parseInt(response.substring(0, 1)), Integer.parseInt(response.substring(2))};

            if (theGame.moveIsValid(theBoard, move)) {
                theGame.makeMove(theBoard, mark, move);
                theView.updateButtons(theBoard.getTheBoard());
                sendResponse();
            }
            System.out.println(actionEvent.getActionCommand());



//            theView.setMessage(theBoard.getMessage());


        }
    }

    private Object readIn(ObjectInputStream ois) {
        try {
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void writeOut(java.io.Serializable obj, ObjectOutputStream oos) {
        try {
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addButtonListeners() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Controller.boardListener thisListener = new Controller.boardListener();
                theView.addButtonListeners(i, j, thisListener);

            }
        }
    }
}
