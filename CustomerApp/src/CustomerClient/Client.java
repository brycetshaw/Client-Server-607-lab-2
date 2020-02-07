package CustomerClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import CustomerClient.Controller.*;
import CustomerClient.View.ClientInfoView;
import CustomerClient.View.MainView;
import CustomerClient.View.SearchClientView;
import CustomerClient.View.SearchCriteriaView;

public class Client {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client (String serverName, int portNumber) {
        try {
            socket = new Socket(serverName, portNumber);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            SearchCriteriaView searchCriteriaView = new SearchCriteriaView();
            ClientInfoView clientInfoView = new ClientInfoView();
            SearchClientView searchClientView = new SearchClientView();

            MainView mainView = new MainView(searchClientView, clientInfoView, searchCriteriaView);
            mainView.pack();
            mainView.setVisible(true);

            WindowListener windowListener = new WindowListener(mainView, in, out);
            ClientListListener clientListListener = new ClientListListener(clientInfoView, searchClientView);
            SearchListener searchListener = new SearchListener(out, in, searchCriteriaView, searchClientView, clientListListener);
            ClearSearchListener clearSearchListener = new ClearSearchListener(searchCriteriaView, searchClientView, clientListListener);
            SaveListener saveListener = new SaveListener(out, in, clientInfoView, clientListListener, searchClientView, searchListener);
            ClearListener clearListener = new ClearListener(searchClientView,clientInfoView, clientListListener);
            DeleteListener deleteListener = new DeleteListener(out, in, clientInfoView, clientListListener, searchClientView, searchListener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startup() {

    }

    public static void main (String [] args) {
        Client client = new Client("localhost", 5000);
        client.startup();
    }
}
