package Server.Controller;

import Model.Board;

import java.io.*;
import java.net.Socket;

public class Controller implements Runnable {

    private ObjectOutputStream clientOutput1;
    private ObjectInputStream clientInput1;

    private ObjectOutputStream clientOutput2;
    private ObjectInputStream clientInput2;


    private String name;
    private Socket player1;
    private Socket player2;
    private Board theBoard;
    private char mark;

    public Controller(Socket clientSocket1, Socket clientSocket2) {

        this.player1 = clientSocket1;
        this.player2 = clientSocket2;
    }


    @Override
    public void run() {

        setObjectStreams();
        play();

    }

    private void play() {
        Board theBoard = new Board();

        try {
            while (theBoard.isRunning()) {

                writeOut(theBoard, clientOutput1);
                theBoard = (Board) readIn(clientInput1);
                writeOut(theBoard, clientOutput2);
                theBoard = (Board) readIn(clientInput2);
            }
            writeOut(theBoard, clientOutput1);
            writeOut(theBoard, clientOutput2);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setObjectStreams() {
        try {
            clientOutput1 = new ObjectOutputStream(player1.getOutputStream());
            clientInput1 = new ObjectInputStream(player1.getInputStream());

            clientOutput2 = new ObjectOutputStream(player2.getOutputStream());
            clientInput2 = new ObjectInputStream(player2.getInputStream());
        } catch (IOException e) {
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



