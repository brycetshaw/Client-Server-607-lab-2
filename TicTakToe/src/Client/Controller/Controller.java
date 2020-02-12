package Client.Controller;

import Model.*;
import Client.View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Controller {

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    private View theView;
    private Board theBoard;
    private char mark;
    private String name;

    public Controller(Socket aSocket, View theView) throws IOException {
        mark = " ".charAt(0);
        this.theView = theView;
        objectInputStream = new ObjectInputStream(aSocket.getInputStream());
        objectOutputStream = new ObjectOutputStream(aSocket.getOutputStream());
        name = theView.getName();
//        settingUpTheGame();
        theView.setVisible(true);
        addButtonListeners();
        listeningState();
    }



    private void listeningState() {
        while (true) {


            System.out.println("listening");


            theBoard = (Board) readIn(objectInputStream);
            System.out.println("recieved");
            System.out.println(name );
            System.out.println(theBoard.toString());



            if(theBoard.getNeedsPlayers() && !theBoard.getxPlayer().equals(name)){
                //case where no players are in the game

                mark = Game.addPlayer(theBoard, name);
                writeOut(theBoard, objectOutputStream);
            } else if(theBoard.getNeedsPlayers() && theBoard.getxPlayer().equals(name)){
                //case where the board is waiting for the other player.
                //this case shouldn't really exist

                theBoard.setMessage("Waiting for the other player");


            }else if (!theBoard.isRunning()) {
                //case where the board has no valid moves.
                theView.enableBoardButtons(false);
                theView.setNewGame(true);
                System.out.println("there is a winner!");
//                mark = Game.getOpponent(mark);
            } else {
                //case where the game is proceeding normally
                theView.enableBoardButtons(true);
                theView.setNewGame(false);
            }
            theView.setMessage(Game.getMessage(theBoard, mark, name));
            theView.updateButtons(theBoard.getTheBoard());
        }
    }

    private void sendResponse() {
        System.out.println("sending response");
        System.out.println(theBoard.toString());
        writeOut(theBoard, objectOutputStream);
    }

    private class boardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String response = actionEvent.getActionCommand();
            System.out.println(mark + " plays " +response);
            int[] move = {Integer.parseInt(response.substring(0, 1)), Integer.parseInt(response.substring(2))};

            if (Game.moveIsValid(theBoard, move) && theBoard.getToPlay() == mark) {
                Game.makeMove(theBoard, mark, move);

                if(!theBoard.isRunning()){
                    gameIsWon();
                }
                sendResponse();
            }
        }
    }

    private class newGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            theBoard.reset();
            sendResponse();
        }
    }

    private void gameIsWon(){
        System.out.println("game is won!");
    }

    private Object readIn(ObjectInputStream ois) {
        try {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
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
        Controller.newGameListener newGameListener = new Controller.newGameListener();
        theView.addNewGameListener(newGameListener);
        theView.setNewGame(false);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Controller.boardListener thisListener = new Controller.boardListener();
                theView.addButtonListeners(i, j, thisListener);
            }
        }
    }

}
