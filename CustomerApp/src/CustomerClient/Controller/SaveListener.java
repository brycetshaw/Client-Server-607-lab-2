package CustomerClient.Controller;

import CustomerModel.Customer;
import CustomerModel.CustomerDto;
import CustomerClient.View.ClientInfoView;
import CustomerClient.View.SearchClientView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
     * @param clientManager
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
                String ID = this.clientInfoView.getClientId().getText();
                CustomerDto customerDto = new CustomerDto();
                ArrayList<Customer> customers = new ArrayList<>();
                customerDto.setCommand("POST");
                Customer customer = new Customer();
                customer.setId(Integer.parseInt(ID));
                customer.setFirstName(clientInfoView.getFirstName().getText());
                customer.setLastName(clientInfoView.getLastName().getText());
                customer.setAddress(clientInfoView.getAddress().getText());
                customer.setPostalCode(clientInfoView.getPostalCode().getText());
                customer.setPhoneNumber(clientInfoView.getPhoneNum().getText());
                customer.setCustomerType(clientInfoView.getClientType().getText());
                customers.add(customer);
                customerDto.setCustomers(customers);
                out.writeObject(customerDto);
//                Customer client = (Customer) in.readObject();
//                if (client == null) {
//                    this.clientInfoView.showMessage("Client with ID: " + ID + " cannot be found.  Please ensure that the client info to be edited\n" +
//                            "has the correct ID or leave the ID field blank to create a new client");
//                } else {
//                    editExistingClient(client);
//                    clearFields();
//                }
            } catch (NumberFormatException | IOException ex) {
//                addNewClient();
//                clearFields();
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


//    /**
//     * Edits the client info in the database and refresh the list.
//     * @param client
//     */
//    private void editExistingClient(Client client) {
//        if (checkValidFields()) {
//            checkChangedInfo(client);
//            clientManager.updateItem(client);
//            searchListener.populateSearchResults(true);
//            clientInfoView.showMessage("Edited Client info successfully saved!");
//        }
//    }
//
//    /**
//     * Adds new client to the database and refresh the list.
//     */
//    private void addNewClient() {
//        if (!clientInfoView.getClientId().getText().contentEquals("")) {
//            clientInfoView.showMessage("Cannot specify a user ID when creating a new user\n(Leave the ID field blank)");
//        } else {
//            if (checkValidFields()) {
//                Client client = new Client(
//                        clientInfoView.getFirstName().getText(),
//                        clientInfoView.getLastName().getText(),
//                        clientInfoView.getAddress().getText(),
//                        clientInfoView.getPostalCode().getText(),
//                        clientInfoView.getPhoneNum().getText(),
//                        clientInfoView.getClientType().getText());
//                clientManager.addItem(client);
//                searchListener.populateSearchResults(true);
//                clientInfoView.showMessage("Client successfully added!");
//            }
//        }
//    }
//
//    /**
//     * Checks if the fields are valid before adding or editing a client.
//     * @return
//     */
//    private boolean checkValidFields() {
//        if (checkFirstName() && checkLastName() && checkAddress() && checkPostalCode()
//                && checkPhoneNum() && checkClientType()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * Checks if the postal code entered is in the correct format and outputs an error message if not correct.
//     * @return
//     */
//    private boolean checkPostalCode() {
//        String postalCode = clientInfoView.getPostalCode().getText();
//        String pattern = "^([A-Z]\\d[A-Z]) (\\d[A-Z]\\d)$";
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(postalCode);
//
//        if (m.find() & postalCode.length() == 7) {
//            return true;
//        }
//        clientInfoView.showMessage("Postal code must be in the following format: A1A 1A1\n" +
//                "(A is a letter and 1 is a digit, with a space separating the third and fourth characters)");
//        return false;
//    }
//
//    /**
//     * Checks if the phone number entered is in the correct format and outputs an error message if not correct.
//     * @return
//     */
//    private boolean checkPhoneNum() {
//        String phoneNum = clientInfoView.getPhoneNum().getText();
//        String pattern = "^(\\d\\d\\d)-(\\d\\d\\d)-(\\d\\d\\d\\d)$";
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(phoneNum);
//
//        if (m.find() & phoneNum.length() == 12) {
//            return true;
//        }
//        clientInfoView.showMessage("Phone number must be in the following format: 111-111-1111\n" +
//                "(1 is a digit)");
//        return false;
//    }
//
//    /**
//     * Checks if the client type entered is in the correct format and outputs an error message if not correct.
//     * @return
//     */
//    private boolean checkClientType() {
//        if (clientInfoView.getClientType().getText().contentEquals("C") || clientInfoView.getClientType().getText().contentEquals("R")) {
//            return true;
//        }
//        clientInfoView.showMessage("Client type must be either commercial (C) or residential (R)");
//        return false;
//    }
//
//    /**
//     * Checks if the client first name entered is in the correct format and outputs an error message if not correct.
//     * @return
//     */
//    private boolean checkFirstName() {
//        if (clientInfoView.getFirstName().getText().length() > 20) {
//            clientInfoView.showMessage("Can not have a first name greater than 20 characters");
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * Checks if the client last name entered is in the correct format and outputs an error message if not correct.
//     * @return
//     */
//    private boolean checkLastName() {
//        if (clientInfoView.getLastName().getText().length() > 20) {
//            clientInfoView.showMessage("Can not have a last name greater than 20 characters");
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * Checks if the address entered is in the correct format and outputs an error message if not correct.
//     * @return
//     */
//    private boolean checkAddress() {
//        if (clientInfoView.getAddress().getText().length() > 50) {
//            clientInfoView.showMessage("Can not have an address greater than 50 characters");
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * Checks if each field had changed info from the existing client info and updates the client info accordingly.
//     * @param client
//     */
//    private void checkChangedInfo(Client client) {
//        if (!client.getFirstName().equals(clientInfoView.getFirstName().getText())) {
//            client.setFirstName(clientInfoView.getFirstName().getText());
//        }
//        if (!client.getLastName().equals(clientInfoView.getLastName().getText())) {
//            client.setLastName(clientInfoView.getLastName().getText());
//        }
//        if (!client.getAddress().equals(clientInfoView.getAddress().getText())) {
//            client.setAddress(clientInfoView.getAddress().getText());
//        }
//        if (!client.getPostalCode().equals(clientInfoView.getPostalCode().getText())) {
//            client.setPostalCode(clientInfoView.getPostalCode().getText());
//        }
//        if (!client.getPhoneNumber().equals(clientInfoView.getPhoneNum().getText())) {
//            client.setPhoneNumber(clientInfoView.getPhoneNum().getText());
//        }
//        if (!client.getClientType().equals(clientInfoView.getClientType().getText())) {
//            client.setClientType(clientInfoView.getClientType().getText());
//        }
//    }
}
