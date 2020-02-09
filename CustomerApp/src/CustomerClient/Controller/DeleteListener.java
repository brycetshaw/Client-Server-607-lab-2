package CustomerClient.Controller;

import CustomerClient.View.MainView;
import CustomerModel.Customer;
import CustomerModel.CustomerDto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * DeleteListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020 /02/08
 */
public class DeleteListener extends BaseListener{
    /**
     * Controller related to dealing with actions on the list element.
     */
    private CustomerListListener customerListListener;
    /**
     * Controller related to invoking the searching of a client in the backend.
     */
    private SearchListener searchListener;

    /**
     * Instantiates a new Delete listener.
     *
     * @param mainView             the main view
     * @param in                   the in
     * @param out                  the out
     * @param customerListListener the customer list listener
     * @param searchListener       the search listener
     */
    public DeleteListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out, CustomerListListener customerListListener, SearchListener searchListener) {
        super(mainView, in, out);
        this.customerListListener = customerListListener;
        this.searchListener = searchListener;

        this.mainView.getCustomerInfoView().addDeleteListener(e -> {
            try {
                CustomerDto customerDto = new CustomerDto();
                String Id = populateDtoParams(mainView, customerDto);
                sendDtoToServer(out, customerDto);
                CustomerDto result = (CustomerDto) in.readObject();
                readDtoAndRespond(mainView, searchListener, Id, result);
            } catch (NumberFormatException ex) {
                mainView.getCustomerInfoView().showMessage("Id field must be filled out in order to attempt deletion");
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void sendDtoToServer(ObjectOutputStream out, CustomerDto customerDto) throws IOException {
        out.writeObject(customerDto);
        out.reset();
    }

    private void readDtoAndRespond(MainView mainView, SearchListener searchListener, String id, CustomerDto result) throws IOException, ClassNotFoundException {
        if (result.getCommand().contentEquals("DELETESUCCESS")) {
            searchListener.populateSearchResults(true);
            mainView.getCustomerInfoView().showMessage("Customer info successfully deleted!");
            clearFields();
        } else if (result.getCommand().contentEquals("FAILURE")) {
            mainView.getCustomerInfoView().showMessage("Client with ID: " + id + " does not exist.  Please double check the Customer Id and try again.");
        } else {
            mainView.getCustomerInfoView().showMessage("Something wrong happened.  Please try again.");
        }
    }

    private String populateDtoParams(MainView mainView, CustomerDto customerDto) {
        ArrayList<Customer> customers = new ArrayList<>();
        customerDto.setCommand("DELETE");
        Customer customer = new Customer();
        String Id = mainView.getCustomerInfoView().getClientId().getText();
        customer.setId(Integer.parseInt(Id));
        customers.add(customer);
        customerDto.setCustomers(customers);
        return Id;
    }

    /**
     * Clears all the fields in the CustomerInfoView object.  The clearListListener boolean set to false during the
     * clearing of fields in order to prevent the listSelection listener from getting invoked.
     */
    private void clearFields() {
        customerListListener.setListPopulated(false);
        mainView.getSearchCustomerView().getResultArea().clearSelection();
        mainView.getCustomerInfoView().clearFields();
        customerListListener.setListPopulated(true);
    }
}
