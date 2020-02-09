package CustomerClient;

import CustomerClient.Controller.*;
import CustomerClient.View.CustomerInfoView;
import CustomerClient.View.MainView;
import CustomerClient.View.SearchCustomerView;
import CustomerClient.View.SearchCriteriaView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Client class and its instance methods and variables.
 * This class is the main class for the client side.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020/02/08
 */
public class Client {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * Instantiates a new Client.
     *
     * @param serverName the server name
     * @param portNumber the port number
     */
    public Client (String serverName, int portNumber) {
        try {
            socket = new Socket(serverName, portNumber);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Startup.
     */
    public void startup() {
        MainView mainView = createViews();
        createListeners(mainView);
    }

    private MainView createViews() {
        SearchCriteriaView searchCriteriaView = new SearchCriteriaView();
        CustomerInfoView customerInfoView = new CustomerInfoView();
        SearchCustomerView searchCustomerView = new SearchCustomerView();
        MainView mainView = new MainView(searchCustomerView, customerInfoView, searchCriteriaView);
        mainView.pack();
        mainView.setVisible(true);
        return mainView;
    }

    private void createListeners(MainView mainView) {
        WindowListener windowListener = new WindowListener(mainView, in, out);
        CustomerListListener customerListListener = new CustomerListListener(mainView, in, out);
        SearchListener searchListener = new SearchListener(mainView, in, out, customerListListener);
        ClearSearchListener clearSearchListener = new ClearSearchListener(mainView, in, out, customerListListener);
        SaveListener saveListener = new SaveListener(mainView, in, out, customerListListener, searchListener);
        ClearListener clearListener = new ClearListener(mainView, in, out, customerListListener);
        DeleteListener deleteListener = new DeleteListener(mainView, in, out, customerListListener, searchListener);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main (String [] args) {
        // This is to connect the client to the server located on EC2 VM
        Client client = new Client("18.232.131.125", 5000);

        // This is to connect the client to the server located on local machine
//        Client client = new Client("localhost", 5000);
        client.startup();
    }
}
