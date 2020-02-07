package CustomerClient.Controller;

import CustomerModel.Customer;
import CustomerClient.View.ClientInfoView;
import CustomerClient.View.SearchClientView;
import CustomerModel.CustomerDto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * DeleteListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */

public class DeleteListener {
    /**
     * Main backend file related to reading/writing to the database.
     */
    private ObjectOutputStream out;
    private ObjectInputStream in;
    /**
     * Panel containing GUI elements related to the form fields for a client.
     */
    private ClientInfoView clientInfoView;
    /**
     * Controller related to dealing with actions on the list element.
     */
    private ClientListListener clientListListener;
    /**
     * Panel containing GUI elements related to displaying the search results to the user.
     */
    private SearchClientView searchClientView;
    /**
     * Controller related to invoking the searching of a client in the backend.
     */
    private SearchListener searchListener;

    /**
     * This constructs the ClientListListener object and adds a listener to listen to a mouse click on the delete button.
     * @param clientManager
     * @param clientInfoView
     * @param clientListListener
     * @param searchClientView
     * @param searchListener
     */
    public DeleteListener(ObjectOutputStream out, ObjectInputStream in, ClientInfoView clientInfoView,
                          ClientListListener clientListListener, SearchClientView searchClientView, SearchListener searchListener) {
        this.out = out;
        this.in = in;
        this.clientInfoView = clientInfoView;
        this.clientListListener = clientListListener;
        this.searchClientView = searchClientView;
        this.searchListener = searchListener;

        this.clientInfoView.addDeleteListener(e -> {
            try {
                CustomerDto customerDto = new CustomerDto();
                ArrayList<Customer> customers = new ArrayList<>();
                customerDto.setCommand("DELETE");
                Customer customer = new Customer();
                customer.setId(Integer.parseInt(clientInfoView.getClientId().getText()));
                customers.add(customer);
                customerDto.setCustomers(customers);
                out.writeObject(customerDto);
                //TODO need to output success message
//                Customer existingClient = clientManager.searchClientId(this.clientInfoView.getClientId().getText());
//                if (existingClient != null) {
//                    deleteClient(existingClient);
//                    clearFields();
//                }
//                else {
//                    clientInfoView.showMessage("Must input a proper client ID!");
//                }
            } catch (NumberFormatException | IOException ex) {
                clientInfoView.showMessage("Id field must be filled out in order to attempt deletion");
            }
        });
    }

    /**
     * Clears all the fields in the ClientInfoView object.  The clearListListener boolean set to false during the
     * clearing of fields in order to prevent the listSelection listener from getting invoked.
     */
    private void clearFields() {
        clientListListener.setListPopulated(false);
        searchClientView.getResultArea().clearSelection();
        clientInfoView.clearFields();
        clientListListener.setListPopulated(true);
    }

    /**
     * Deletes the client in the database and refresh the list of clients.
     * @param existingClient
     */
    private void deleteClient(Customer existingClient) throws IOException, ClassNotFoundException {
        searchListener.populateSearchResults(true);
        clientInfoView.showMessage("Client successfully deleted!");
    }
}
