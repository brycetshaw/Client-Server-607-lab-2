package Server.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PlayerSocket {

    private ObjectOutputStream clientObjectOutput;
    private ObjectInputStream clientObjectInput;

    public PlayerSocket(Socket clientSocket) throws IOException {
        clientObjectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
        clientObjectInput = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void objectOut(E object){
        clientObjectOutput.writeObject(object);

    }

}
