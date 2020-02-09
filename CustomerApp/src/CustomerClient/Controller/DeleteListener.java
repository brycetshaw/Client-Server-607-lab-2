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
     *
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
                String Id = clientInfoView.getClientId().getText();
                customer.setId(Integer.parseInt(Id));
                customers.add(customer);
                customerDto.setCustomers(customers);
                out.writeObject(customerDto);
                out.reset();
                CustomerDto result = (CustomerDto) in.readObject();
                if (result.getCommand().contentEquals("DELETESUCCESS")) {
                    searchListener.populateSearchResults(true);
                    clientInfoView.showMessage("Customer info successfully deleted!");
                    clearFields();
                } else if (result.getCommand().contentEquals("FAILURE")) {
                    clientInfoView.showMessage("Client with ID: " + Id + " does not exist.  Please double check the Customer Id and try again.");
                } else {
                    clientInfoView.showMessage("Something wrong happened.  Please try again.");
                }
            } catch (NumberFormatException ex) {
                clientInfoView.showMessage("Id field must be filled out in order to attempt deletion");
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
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
}
