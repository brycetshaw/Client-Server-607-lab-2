package CustomerClient;

import CustomerClient.Controller.*;
import CustomerClient.View.ClientInfoView;
import CustomerClient.View.MainView;
import CustomerClient.View.SearchClientView;
import CustomerClient.View.SearchCriteriaView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client (String serverName, int portNumber) {
        try {
            socket = new Socket(serverName, portNumber);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startup() {
        MainView mainView = createViews();
        createListeners(mainView);
    }

    private MainView createViews() {
        SearchCriteriaView searchCriteriaView = new SearchCriteriaView();
        ClientInfoView clientInfoView = new ClientInfoView();
        SearchClientView searchClientView = new SearchClientView();
        MainView mainView = new MainView(searchClientView, clientInfoView, searchCriteriaView);
        mainView.pack();
        mainView.setVisible(true);
        return mainView;
    }

    private void createListeners(MainView mainView) {
        WindowListener windowListener = new WindowListener(mainView, in, out);
        ClientListListener clientListListener = new ClientListListener(mainView, in, out);
        SearchListener searchListener = new SearchListener(mainView, in, out, clientListListener);
        ClearSearchListener clearSearchListener = new ClearSearchListener(mainView, in, out, clientListListener);
        SaveListener saveListener = new SaveListener(mainView, in, out, clientListListener, searchListener);
        ClearListener clearListener = new ClearListener(mainView, in, out, clientListListener);
        DeleteListener deleteListener = new DeleteListener(mainView, in, out, clientListListener, searchListener);
    }

    public static void main (String [] args) {
        // This is to connect the client to the server located on EC2 VM
//        Client client = new Client("18.232.131.125", 5000);

        // This is to connect the client to the server located on local machine
        Client client = new Client("localhost", 5000);
        client.startup();
    }
}
