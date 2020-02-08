package CustomerClient.Controller;

import CustomerModel.Customer;
import CustomerModel.CustomerDto;
import CustomerClient.View.ClientInfoView;
import CustomerClient.View.SearchClientView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SaveListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */

public class SaveListener {
    /**
     * Main backend file related to reading/writing to the database.
     */
    private ObjectInputStream in;
    private ObjectOutputStream out;
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
     * This constructs the SaveListener object and adds a listener to listen to a mouse click on the save button.
     * If the id field is empty, a new client will be added.  If an existing ID is used, the client info will be edited.
     * Otherwise, gives an error to the user.
     *
     * @param clientInfoView
     * @param clientListListener
     * @param searchClientView
     * @param searchListener
     */
    public SaveListener(ObjectOutputStream out, ObjectInputStream in, ClientInfoView clientInfoView,
                        ClientListListener clientListListener, SearchClientView searchClientView, SearchListener searchListener) {
        this.out = out;
        this.in = in;
        this.clientInfoView = clientInfoView;
        this.clientListListener = clientListListener;
        this.searchClientView = searchClientView;
        this.searchListener = searchListener;

        this.clientInfoView.addSaveListener(e -> {
            try {
                if (checkValidFields()) {
                    String Id = clientInfoView.getClientId().getText();
                    CustomerDto customerDto = new CustomerDto();
                    ArrayList<Customer> customers = new ArrayList<>();
                    customerDto.setCommand("POST");
                    Customer customer = new Customer();
                    if (!Id.contentEquals("")) {
                        customer.setId(Integer.parseInt(Id));
                    } else {
                        customer.setId(-1);
                    }
                    customer.setFirstName(clientInfoView.getFirstName().getText());
                    customer.setLastName(clientInfoView.getLastName().getText());
                    customer.setAddress(clientInfoView.getAddress().getText());
                    customer.setPostalCode(clientInfoView.getPostalCode().getText());
                    customer.setPhoneNumber(clientInfoView.getPhoneNum().getText());
                    customer.setCustomerType(clientInfoView.getClientType().getText());
                    customers.add(customer);
                    customerDto.setCustomers(customers);
                    out.writeObject(customerDto);
                    out.reset();
                    CustomerDto result = (CustomerDto) in.readObject();
                    if (result.getCommand().contentEquals("EDITSUCCESS")) {
                        searchListener.populateSearchResults(true);
                        clientInfoView.showMessage("Edited Client info successfully saved!");
                        clearFields();
                    } else if (result.getCommand().contentEquals("CREATESUCCESS")) {
                        searchListener.populateSearchResults(true);
                        clientInfoView.showMessage("Client successfully added!");
                        clearFields();
                    } else if (result.getCommand().contentEquals("FAILURE")) {
                        clientInfoView.showMessage("Client with ID: " + Id + " cannot be found.  Please ensure that the client info to be edited\n" +
                            "has the correct ID or leave the ID field blank to create a new client");
                    } else {
                        clientInfoView.showMessage("Something wrong happened.  Please try again.");
                    }
                }
            } catch (NumberFormatException | IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Clears all the fields in the SearchCriteriaView and the list in SearchClientView object.
     * The clearListListener boolean set to false during the
     * clearing of fields in order to prevent the listSelection listener from getting invoked.
     */
    private void clearFields() {
        clientListListener.setListPopulated(false);
        searchClientView.getResultArea().clearSelection();
        clientInfoView.clearFields();
        clientListListener.setListPopulated(true);
    }

    /**
     * Checks if the fields are valid before adding or editing a client.
     * @return
     */
    private boolean checkValidFields() {
        if (checkFirstName() && checkLastName() && checkAddress() && checkPostalCode()
                && checkPhoneNum() && checkClientType()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the postal code entered is in the correct format and outputs an error message if not correct.
     * @return
     */
    private boolean checkPostalCode() {
        String postalCode = clientInfoView.getPostalCode().getText();
        String pattern = "^([A-Z]\\d[A-Z]) (\\d[A-Z]\\d)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(postalCode);

        if (m.find() & postalCode.length() == 7) {
            return true;
        }
        clientInfoView.showMessage("Postal code must be in the following format: A1A 1A1\n" +
                "(A is a letter and 1 is a digit, with a space separating the third and fourth characters)");
        return false;
    }

    /**
     * Checks if the phone number entered is in the correct format and outputs an error message if not correct.
     * @return
     */
    private boolean checkPhoneNum() {
        String phoneNum = clientInfoView.getPhoneNum().getText();
        String pattern = "^(\\d\\d\\d)-(\\d\\d\\d)-(\\d\\d\\d\\d)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(phoneNum);

        if (m.find() & phoneNum.length() == 12) {
            return true;
        }
        clientInfoView.showMessage("Phone number must be in the following format: 111-111-1111\n" +
                "(1 is a digit)");
        return false;
    }

    /**
     * Checks if the client type entered is in the correct format and outputs an error message if not correct.
     * @return
     */
    private boolean checkClientType() {
        if (clientInfoView.getClientType().getText().contentEquals("C") || clientInfoView.getClientType().getText().contentEquals("R")) {
            return true;
        }
        clientInfoView.showMessage("Client type must be either commercial (C) or residential (R)");
        return false;
    }

    /**
     * Checks if the client first name entered is in the correct format and outputs an error message if not correct.
     * @return
     */
    private boolean checkFirstName() {
        if (clientInfoView.getFirstName().getText().length() > 20) {
            clientInfoView.showMessage("Can not have a first name greater than 20 characters");
            return false;
        } else if (clientInfoView.getFirstName().getText().length() == 0) {
            clientInfoView.showMessage("Must have first name");
            return false;
        }
        return true;
    }

    /**
     * Checks if the client last name entered is in the correct format and outputs an error message if not correct.
     * @return
     */
    private boolean checkLastName() {
        if (clientInfoView.getLastName().getText().length() > 20) {
            clientInfoView.showMessage("Can not have a last name greater than 20 characters");
            return false;
        } else if (clientInfoView.getLastName().getText().length() == 0) {
            clientInfoView.showMessage("Must have last name");
            return false;
        }
        return true;
    }

    /**
     * Checks if the address entered is in the correct format and outputs an error message if not correct.
     * @return
     */
    private boolean checkAddress() {
        if (clientInfoView.getAddress().getText().length() > 50) {
            clientInfoView.showMessage("Can not have an address greater than 50 characters");
            return false;
        } else if (clientInfoView.getAddress().getText().length() == 0) {
            clientInfoView.showMessage("Must have an address");
            return false;
        }
        return true;
    }

}
