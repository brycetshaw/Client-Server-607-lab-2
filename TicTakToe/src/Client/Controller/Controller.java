package Client.Controller;

import Model.*;
import Client.View.*;

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
        this.theGame = theGame;
        this.theView = theView;
        objectInputStream = new ObjectInputStream(aSocket.getInputStream());
        objectOutputStream = new ObjectOutputStream(aSocket.getOutputStream());
        joinGame();
        playGame();
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

    private Object readIn(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        return ois.readObject();
    }

    private static void writeOut(java.io.Serializable obj, ObjectOutputStream oos) throws IOException {
        oos.writeObject(obj);
    }

}
