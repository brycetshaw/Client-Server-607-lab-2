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
        mark = " ".charAt(0);
        this.theGame = theGame;
        this.theView = theView;
        objectInputStream = new ObjectInputStream(aSocket.getInputStream());
        objectOutputStream = new ObjectOutputStream(aSocket.getOutputStream());
        name = theView.getName();
//        playGame();
        theView.setVisible(true);
        theBoard = (Board) readIn(objectInputStream);
        mark = theGame.addPlayer(theBoard, name);
        writeOut(theBoard, objectOutputStream);
        theBoard = (Board) readIn(objectInputStream);
        addButtonListeners();
//        theView.addNewGameListener(new TikTakToeController.anotherGameListener());

    }

//        private class newGameListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            theView.setVisible(true);
//            int[] gameType = newGame.getGameType();
//            theModel.setGame(gameType);
//            newGame.dispose();
//
//        }
//    }



//    private void joinGame() {
//        try {
//
//            theBoard = (Board) readIn(objectInputStream);
//
//
//            mark = theGame.addPlayer(theBoard, name);
//            theView.sendRequest("hi " + name + ". You are" +
//                    " playing as " + mark + "\n");
//            writeOut(theBoard, objectOutputStream);
//
//
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

//    private void playGame() {
//        try {
//            while (true) {
//                theBoard = (Board) readIn(objectInputStream);
//                theView.sendRequest(theGame.display(theBoard));
//                if (theGame.checkWinner(theBoard, mark)) {
//                    theBoard.setRunning(false);
//                    theBoard.setMessage("Game over!");
//                    break;
//                }
//                boolean moveIsValid = false;
//                do {
//                    theView.sendRequest("write your chosen XY coordinate.");
//                    String response = theView.getResponse();
//                    theView.sendRequest(response);
//                    try {
//                        int[] move = {response.charAt(0) - 48, response.charAt(1) - 48};
//                        theView.sendRequest("x:" + move[0] + " y:" + move[1]);
//                        moveIsValid = theGame.makeMove(theBoard, mark, move);
//                    } catch (Exception e) {
//                        System.out.println("exceptiopm");
//                    }
//
//                } while (!moveIsValid);
//
//                writeOut(theBoard, objectOutputStream);
//
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

//    private void play() {
//
//        try {
//
//            while (true) {
//
//
//                Message theMessage = (Message) readIn(objectInputStream);
//
//                runTurn(theMessage);
//
//                theView.sendRequest(theGame.display(theBoard));
//                if (theGame.checkWinner(theBoard, mark)) {
//                    theBoard.setRunning(false);
//                    theBoard.setMessage("Game over!");
//                    break;
//                }
//                boolean moveIsValid = false;
//                do {
//                    theView.sendRequest("write your chosen XY coordinate.");
//                    String response = theView.getResponse();
//                    theView.sendRequest(response);
//                    try {
//                        int[] move = {response.charAt(0) - 48, response.charAt(1) - 48};
//                        theView.sendRequest("x:" + move[0] + " y:" + move[1]);
//                        moveIsValid = theGame.makeMove(theBoard, mark, move);
//                    } catch (Exception e) {
//                        System.out.println("exceptiopm");
//                    }
//
//                } while (!moveIsValid);
//
//                writeOut(theBoard, objectOutputStream);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

//    private void runTurn(Message theMessage) {
//
//
//        switch (theMessage.getHeader()){
//            case "move":
//                theMessage.getBody().charAt();
//                theBoard.setToPlay(mark);
//                theView.setMessage(name + "! It's your turn.");
//
//                return;
//            case "set":
//                th
//        }
//    }

    private class boardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String response = actionEvent.getActionCommand();
            System.out.println(response);
            int[] move = {Integer.parseInt(response.substring(0, 1)), Integer.parseInt(response.substring(2))};

            if (theGame.isPlaying(theBoard, mark) && theGame.isPlaying(theBoard, mark)) {
                theGame.makeMove(theBoard, mark, move);
                theView.updateButtons(theBoard.getTheBoard());

            }
            System.out.println(actionEvent.getActionCommand());
            writeOut(theBoard, objectOutputStream);
            theBoard = (Board)readIn(objectInputStream);
            theView.setMessage(theBoard.getMessage()); //TODO breaks MVC a little bit.
            theView.updateButtons(theBoard.getTheBoard());
        }
    }

    private Object readIn(ObjectInputStream ois)  {
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
//
//
//    private class boardListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//
//            String response = actionEvent.getActionCommand();
//            System.out.println(response);
//            int i = Integer.parseInt(response.substring(0, 1));
//            int j = Integer.parseInt(response.substring(2));
//
//            if (((JButton) actionEvent.getSource()).getText().equals(Character.toString(Constants.SPACE_CHAR))) {
//                theModel.markBoard(i, j, mark);
//                theModel.getCurrentPlayer().play();
//
//            }
//            System.out.println(actionEvent.getActionCommand());
//        }
//    }
//
//    private class newGameListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            theView.setVisible(true);
//            int[] gameType = newGame.getGameType();
//            theModel.setGame(gameType);
//            newGame.dispose();
//
//        }
//    }
//
//    private void startNewGame() {
//
//    }
//
//    private class anotherGameListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            setNewGame();
//            theView.setVisible(false);
//            theView = new TikTakToeView();
//            theModel = new TikTakToeModel(theView);
//            addButtonListeners();
//            theView.addNewGameListener(new TikTakToeController.anotherGameListener());
//
//
//        }
//    }


}
