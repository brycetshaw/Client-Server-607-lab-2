package Client;


import java.io.*;
import java.net.Socket;

public class Client {


    private Socket aSocket;

    private BufferedReader stdIn;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;


    private Model theModel;
    private char mark;
    private String name;

    public Client(String serverName, int portNumber, String name) throws IOException {
        this.name = name;
        aSocket = new Socket(serverName, portNumber);
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Waiting for another player to join");
        objectInputStream = new ObjectInputStream(aSocket.getInputStream());
        objectOutputStream = new ObjectOutputStream(aSocket.getOutputStream());
        System.out.println("Server Connected.");
        joinGame();
    }


    public static void main(String[] args) {
        System.out.println("Welcome to tic tak toe. what is your name?");
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String name = "";
        try {
            name = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean hasConnected = false;
        while (!hasConnected) {
            try {
                Client aClient = new Client("localhost", 9091, name);
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


    private void joinGame() {

        try {
            theModel = (Model) objectInputStream.readObject();
            mark = theModel.addPlayer(name);
            objectOutputStream.writeObject(theModel);
            System.out.println("hi " + name + ". You are" +
                    " playing as " + mark + "\n");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void playGame() {
        try {

            while (true) {
                theModel = (Model) objectInputStream.readObject();
                theModel.display();
                int[] move = new int[2];
                do {
                    System.out.println("input x:");
                    move[0] = Integer.valueOf(stdIn.readLine());
                    System.out.println("input y:");
                    move[1] = Integer.valueOf(stdIn.readLine());
                } while (!theModel.play(move, mark));
                objectOutputStream.writeObject(theModel);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
