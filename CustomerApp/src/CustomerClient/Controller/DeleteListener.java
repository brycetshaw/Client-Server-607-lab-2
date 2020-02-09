package CustomerClient.Controller;

import CustomerClient.View.MainView;
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

public class DeleteListener extends BaseListener{
    /**
     * Controller related to dealing with actions on the list element.
     */
    private ClientListListener clientListListener;
    /**
     * Controller related to invoking the searching of a client in the backend.
     */
    private SearchListener searchListener;

    public DeleteListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out, ClientListListener clientListListener, SearchListener searchListener) {
        super(mainView, in, out);
        this.clientListListener = clientListListener;
        this.searchListener = searchListener;

        this.mainView.getClientInfoView().addDeleteListener(e -> {
            try {
                CustomerDto customerDto = new CustomerDto();
                ArrayList<Customer> customers = new ArrayList<>();
                customerDto.setCommand("DELETE");
                Customer customer = new Customer();
                String Id = mainView.getClientInfoView().getClientId().getText();
                customer.setId(Integer.parseInt(Id));
                customers.add(customer);
                customerDto.setCustomers(customers);
                out.writeObject(customerDto);
                out.reset();
                CustomerDto result = (CustomerDto) in.readObject();
                if (result.getCommand().contentEquals("DELETESUCCESS")) {
                    searchListener.populateSearchResults(true);
                    mainView.getClientInfoView().showMessage("Customer info successfully deleted!");
                    clearFields();
                } else if (result.getCommand().contentEquals("FAILURE")) {
                    mainView.getClientInfoView().showMessage("Client with ID: " + Id + " does not exist.  Please double check the Customer Id and try again.");
                } else {
                    mainView.getClientInfoView().showMessage("Something wrong happened.  Please try again.");
                }
            } catch (NumberFormatException ex) {
                mainView.getClientInfoView().showMessage("Id field must be filled out in order to attempt deletion");
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
        mainView.getSearchClientView().getResultArea().clearSelection();
        mainView.getClientInfoView().clearFields();
        clientListListener.setListPopulated(true);
    }
}
