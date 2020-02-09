package CustomerClient.Controller;

import CustomerClient.View.MainView;
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

public class SaveListener extends BaseListener {
    /**
     * Controller related to dealing with actions on the list element.
     */
    private ClientListListener clientListListener;
    /**
     * Controller related to invoking the searching of a client in the backend.
     */
    private SearchListener searchListener;


    public SaveListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out, ClientListListener clientListListener, SearchListener searchListener) {
        super(mainView, in, out);
        this.clientListListener = clientListListener;
        this.searchListener = searchListener;

        this.mainView.getClientInfoView().addSaveListener(e -> {
            try {
                if (checkValidFields()) {
                    String Id = mainView.getClientInfoView().getClientId().getText();
                    CustomerDto customerDto = new CustomerDto();
                    ArrayList<Customer> customers = new ArrayList<>();
                    customerDto.setCommand("POST");
                    Customer customer = new Customer();
                    setCustomerParams(mainView, Id, customer);
                    customers.add(customer);
                    customerDto.setCustomers(customers);
                    out.writeObject(customerDto);
                    out.reset();
                    CustomerDto result = (CustomerDto) in.readObject();
                    if (result.getCommand().contentEquals("EDITSUCCESS")) {
                        searchListener.populateSearchResults(true);
                        mainView.getClientInfoView().showMessage("Edited Client info successfully saved!");
                        clearFields();
                    } else if (result.getCommand().contentEquals("CREATESUCCESS")) {
                        searchListener.populateSearchResults(true);
                        mainView.getClientInfoView().showMessage("Client successfully added!");
                        clearFields();
                    } else if (result.getCommand().contentEquals("FAILURE")) {
                        mainView.getClientInfoView().showMessage("Client with ID: " + Id + " cannot be found.  Please ensure that the client info to be edited\n" +
                                "has the correct ID or leave the ID field blank to create a new client");
                    } else {
                        mainView.getClientInfoView().showMessage("Something wrong happened.  Please try again.");
                    }
                }
            } catch (NumberFormatException | IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setCustomerParams(MainView mainView, String id, Customer customer) {
        if (!id.contentEquals("")) {
            customer.setId(Integer.parseInt(id));
        } else {
            customer.setId(-1);
        }
        customer.setFirstName(mainView.getClientInfoView().getFirstName().getText());
        customer.setLastName(mainView.getClientInfoView().getLastName().getText());
        customer.setAddress(mainView.getClientInfoView().getAddress().getText());
        customer.setPostalCode(mainView.getClientInfoView().getPostalCode().getText());
        customer.setPhoneNumber(mainView.getClientInfoView().getPhoneNum().getText());
        customer.setCustomerType(mainView.getClientInfoView().getClientType().getText());
    }

    /**
     * Clears all the fields in the SearchCriteriaView and the list in SearchClientView object.
     * The clearListListener boolean set to false during the
     * clearing of fields in order to prevent the listSelection listener from getting invoked.
     */
    private void clearFields() {
        clientListListener.setListPopulated(false);
        mainView.getSearchClientView().getResultArea().clearSelection();
        mainView.getClientInfoView().clearFields();
        clientListListener.setListPopulated(true);
    }

    /**
     * Checks if the fields are valid before adding or editing a client.
     *
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
     *
     * @return
     */
    private boolean checkPostalCode() {
        String postalCode = mainView.getClientInfoView().getPostalCode().getText();
        String pattern = "^([A-Z]\\d[A-Z]) (\\d[A-Z]\\d)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(postalCode);

        if (m.find() & postalCode.length() == 7) {
            return true;
        }
        mainView.getClientInfoView().showMessage("Postal code must be in the following format: A1A 1A1\n" +
                "(A is a letter and 1 is a digit, with a space separating the third and fourth characters)");
        return false;
    }

    /**
     * Checks if the phone number entered is in the correct format and outputs an error message if not correct.
     *
     * @return
     */
    private boolean checkPhoneNum() {
        String phoneNum = mainView.getClientInfoView().getPhoneNum().getText();
        String pattern = "^(\\d\\d\\d)-(\\d\\d\\d)-(\\d\\d\\d\\d)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(phoneNum);

        if (m.find() & phoneNum.length() == 12) {
            return true;
        }
        mainView.getClientInfoView().showMessage("Phone number must be in the following format: 111-111-1111\n" +
                "(1 is a digit)");
        return false;
    }

    /**
     * Checks if the client type entered is in the correct format and outputs an error message if not correct.
     *
     * @return
     */
    private boolean checkClientType() {
        if (mainView.getClientInfoView().getClientType().getText().contentEquals("C") || mainView.getClientInfoView().getClientType().getText().contentEquals("R")) {
            return true;
        }
        mainView.getClientInfoView().showMessage("Client type must be either commercial (C) or residential (R)");
        return false;
    }

    /**
     * Checks if the client first name entered is in the correct format and outputs an error message if not correct.
     *
     * @return
     */
    private boolean checkFirstName() {
        if (mainView.getClientInfoView().getFirstName().getText().length() > 20) {
            mainView.getClientInfoView().showMessage("Can not have a first name greater than 20 characters");
            return false;
        } else if (mainView.getClientInfoView().getFirstName().getText().length() == 0) {
            mainView.getClientInfoView().showMessage("Must have first name");
            return false;
        }
        return true;
    }

    /**
     * Checks if the client last name entered is in the correct format and outputs an error message if not correct.
     *
     * @return
     */
    private boolean checkLastName() {
        if (mainView.getClientInfoView().getLastName().getText().length() > 20) {
            mainView.getClientInfoView().showMessage("Can not have a last name greater than 20 characters");
            return false;
        } else if (mainView.getClientInfoView().getLastName().getText().length() == 0) {
            mainView.getClientInfoView().showMessage("Must have last name");
            return false;
        }
        return true;
    }

    /**
     * Checks if the address entered is in the correct format and outputs an error message if not correct.
     *
     * @return
     */
    private boolean checkAddress() {
        if (mainView.getClientInfoView().getAddress().getText().length() > 50) {
            mainView.getClientInfoView().showMessage("Can not have an address greater than 50 characters");
            return false;
        } else if (mainView.getClientInfoView().getAddress().getText().length() == 0) {
            mainView.getClientInfoView().showMessage("Must have an address");
            return false;
        }
        return true;
    }

}
