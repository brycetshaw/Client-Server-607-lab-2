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



    private Socket aSocket;



    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;


    private Game theGame;
    private View theView;
    private Board theBoard;
    private char mark;
    private String name;


    public Controller(Socket aSocket, View theView, Game theGame) throws IOException {
//        setNewGame();
        this.theGame = theGame;
        this.theView = theView;
        objectInputStream = new ObjectInputStream(aSocket.getInputStream());
        objectOutputStream = new ObjectOutputStream(aSocket.getOutputStream());
        joinGame();
        playGame();

        addButtonListeners();
        theView.addNewGameListener(new TikTakToeController.anotherGameListener());
    }


    private void joinGame() {
        try {

            theView.sendRequest("Welcome to tic tak toe. what is your name?");
            name = theView.getResponse();
            theBoard = (Board) readIn(objectInputStream);


            mark = theGame.addPlayer(theBoard, name);
            theView.sendRequest("hi " + name + ". You are" +
                    " playing as " + mark + "\n");
            writeOut(theBoard, objectOutputStream);


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void playGame() {
        try {
            while (true) {
                theBoard = (Board) readIn(objectInputStream);
                theView.sendRequest(theGame.display(theBoard));
                if(theGame.checkWinner(theBoard, mark)){
                    theBoard.setRunning(false);
                    theBoard.setMessage("Game over!");
                    break;
                }
                boolean moveIsValid = false;
                do{
                    theView.sendRequest("write your chosen XY coordinate.");
                    String response = theView.getResponse();
                    theView.sendRequest(response);
                    try{
                        int[] move = {response.charAt(0)-48, response.charAt(1)-48};
                        theView.sendRequest("x:"+ move[0] + " y:" + move[1]);
                        moveIsValid = theGame.makeMove(theBoard, mark, move);
                    } catch (Exception e) {
                        System.out.println("exceptiopm");
                    }

                } while (!moveIsValid);

                writeOut(theBoard,objectOutputStream);

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class boardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String response = actionEvent.getActionCommand();
            System.out.println(response);
            int i = Integer.parseInt(response.substring(0,1));
            int j = Integer.parseInt(response.substring(2));

            if(((JButton) actionEvent.getSource()).getText().equals(Character.toString(Constants.SPACE_CHAR))) {
                theModel.markBoard(i,j,mark);
                theModel.getCurrentPlayer().play();

            }
            System.out.println(actionEvent.getActionCommand());
        }
    }

    private Object readIn(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        return ois.readObject();
    }

    private static void writeOut(java.io.Serializable obj, ObjectOutputStream oos) throws IOException {
        oos.writeObject(obj);
    }

    private void addButtonListeners() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Controller.boardListener thisListener = new Controller.boardListener();
                theView.addButtonListeners(i, j, thisListener);

            }
        }
    }



//    public TikTakToeController(TikTakToeModel theModel, TikTakToeView theView) {
//        setNewGame();
//        this.theModel = theModel;
//        this.theView = theView;
//        addButtonListeners();
//        theView.addNewGameListener(new TikTakToeController.anotherGameListener());
//
//
//    }

//    private void setNewGame() {
//        this.newGame = new NewGame();
//        newGame.addGoListener(new TikTakToeController.newGameListener());
//    }



    private class boardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String response = actionEvent.getActionCommand();
            System.out.println(response);
            int i = Integer.parseInt(response.substring(0,1));
            int j = Integer.parseInt(response.substring(2));

            if(((JButton) actionEvent.getSource()).getText().equals(Character.toString(Constants.SPACE_CHAR))) {
                theModel.markBoard(i,j,mark);
                theModel.getCurrentPlayer().play();

            }
            System.out.println(actionEvent.getActionCommand());
        }
    }

    private class newGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            theView.setVisible(true);
            int[] gameType = newGame.getGameType();
            theModel.setGame(gameType);
            newGame.dispose();

        }
    }

    private void startNewGame(){

    }

    private class anotherGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setNewGame();
            theView.setVisible(false);
            theView = new TikTakToeView();
            theModel = new TikTakToeModel(theView);
            addButtonListeners();
            theView.addNewGameListener(new TikTakToeController.anotherGameListener());


        }
    }


}
