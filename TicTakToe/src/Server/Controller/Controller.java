package Server.Controller;

import Model.Board;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements Runnable {

    private ObjectOutputStream clientOutput1;
    private ObjectInputStream clientInput1;

    private ObjectOutputStream clientOutput2;
    private ObjectInputStream clientInput2;

    private Socket player1;
    private Socket player2;

    private ExecutorService pool;

    public Controller(Socket clientSocket1, Socket clientSocket2) {
        this.player1 = clientSocket1;
        this.player2 = clientSocket2;
        pool = Executors.newFixedThreadPool(2);
    }


    @Override
    public void run() {
        setObjectStreams();
        startTheGame();
    }

    private void startTheGame() {
        Board theBoard = new Board();

        try {
            writeOut(theBoard, clientOutput1);
            theBoard = (Board) readIn(clientInput1);
            writeOut(theBoard, clientOutput2);
            theBoard = (Board) readIn(clientInput2);
            writeOut(theBoard, clientOutput1);

            while (true) {

//                writeOut(theBoard, clientOutput2);
                theBoard = (Board) readIn(clientInput1);
                if(theBoard.isShutdown()) throw new IOException();

                System.out.println("received from 1");
                writeOut(theBoard, clientOutput2);
                writeOut(theBoard, clientOutput1);
                theBoard = (Board) readIn(clientInput2);
                if(theBoard.isShutdown()) throw new IOException();
                System.out.println("rec'd 2");
                writeOut(theBoard, clientOutput1);
                writeOut(theBoard, clientOutput2);
                System.out.println("wrote to 1");

            }
        } catch (ClassNotFoundException | IOException e) {
            theBoard.setShutdown(true);
            try {
                System.out.println("try to shutdown 2");
                writeOut(theBoard, clientOutput2);
            } catch (IOException ex) {
                try {
                    System.out.println("try to shutdown 1");
                    writeOut(theBoard, clientOutput1);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }

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



