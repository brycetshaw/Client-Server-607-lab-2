package CustomerClient.Controller;

import CustomerClient.View.MainView;
import CustomerModel.Customer;
import CustomerModel.CustomerDto;

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
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020 /02/08
 */
public class SaveListener extends BaseListener {
    /**
     * Controller related to dealing with actions on the list element.
     */
    private CustomerListListener customerListListener;
    /**
     * Controller related to invoking the searching of a client in the backend.
     */
    private SearchListener searchListener;


    /**
     * Instantiates a new Save listener.
     *
     * @param mainView             the main view
     * @param in                   the in
     * @param out                  the out
     * @param customerListListener the customer list listener
     * @param searchListener       the search listener
     */
    public SaveListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out, CustomerListListener customerListListener, SearchListener searchListener) {
        super(mainView, in, out);
        this.customerListListener = customerListListener;
        this.searchListener = searchListener;

        this.mainView.getCustomerInfoView().addSaveListener(e -> {
            try {
                if (checkValidFields()) {
                    String Id = mainView.getCustomerInfoView().getClientId().getText();
                    CustomerDto customerDto = new CustomerDto();
                    setDtoParams(mainView, Id, customerDto);
                    sendDtoToServer(out, customerDto);
                    CustomerDto result = (CustomerDto) in.readObject();
                    readDtoAndRespond(mainView, searchListener, Id, result);
                }
            } catch (NumberFormatException | IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void readDtoAndRespond(MainView mainView, SearchListener searchListener, String id, CustomerDto result) throws IOException, ClassNotFoundException {
        if (result.getCommand().contentEquals("EDITSUCCESS")) {
            searchListener.populateSearchResults(true);
            mainView.getCustomerInfoView().showMessage("Edited Client info successfully saved!");
            clearFields();
        } else if (result.getCommand().contentEquals("CREATESUCCESS")) {
            searchListener.populateSearchResults(true);
            mainView.getCustomerInfoView().showMessage("Client successfully added!");
            clearFields();
        } else if (result.getCommand().contentEquals("FAILURE")) {
            mainView.getCustomerInfoView().showMessage("Client with ID: " + id + " cannot be found.  Please ensure that the client info to be edited\n" +
                    "has the correct ID or leave the ID field blank to create a new client");
        } else {
            mainView.getCustomerInfoView().showMessage("Something wrong happened.  Please try again.");
        }
    }

    private void sendDtoToServer(ObjectOutputStream out, CustomerDto customerDto) throws IOException {
        out.writeObject(customerDto);
        out.reset();
    }

    private void setDtoParams(MainView mainView, String id, CustomerDto customerDto) {
        ArrayList<Customer> customers = new ArrayList<>();
        customerDto.setCommand("POST");
        Customer customer = new Customer();
        setCustomerParams(mainView, id, customer);
        customers.add(customer);
        customerDto.setCustomers(customers);
    }

    private void setCustomerParams(MainView mainView, String id, Customer customer) {
        if (!id.contentEquals("")) {
            customer.setId(Integer.parseInt(id));
        } else {
            customer.setId(-1);
        }
        customer.setFirstName(mainView.getCustomerInfoView().getFirstName().getText());
        customer.setLastName(mainView.getCustomerInfoView().getLastName().getText());
        customer.setAddress(mainView.getCustomerInfoView().getAddress().getText());
        customer.setPostalCode(mainView.getCustomerInfoView().getPostalCode().getText());
        customer.setPhoneNumber(mainView.getCustomerInfoView().getPhoneNum().getText());
        customer.setCustomerType(mainView.getCustomerInfoView().getClientType().getText());
    }

    /**
     * Clears all the fields in the SearchCriteriaView and the list in SearchCustomerView object.
     * The clearListListener boolean set to false during the
     * clearing of fields in order to prevent the listSelection listener from getting invoked.
     */
    private void clearFields() {
        customerListListener.setListPopulated(false);
        mainView.getSearchCustomerView().getResultArea().clearSelection();
        mainView.getCustomerInfoView().clearFields();
        customerListListener.setListPopulated(true);
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
        String postalCode = mainView.getCustomerInfoView().getPostalCode().getText();
        String pattern = "^([A-Z]\\d[A-Z]) (\\d[A-Z]\\d)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(postalCode);

        if (m.find() & postalCode.length() == 7) {
            return true;
        }
        mainView.getCustomerInfoView().showMessage("Postal code must be in the following format: A1A 1A1\n" +
                "(A is a letter and 1 is a digit, with a space separating the third and fourth characters)");
        return false;
    }

    /**
     * Checks if the phone number entered is in the correct format and outputs an error message if not correct.
     *
     * @return
     */
    private boolean checkPhoneNum() {
        String phoneNum = mainView.getCustomerInfoView().getPhoneNum().getText();
        String pattern = "^(\\d\\d\\d)-(\\d\\d\\d)-(\\d\\d\\d\\d)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(phoneNum);

        if (m.find() & phoneNum.length() == 12) {
            return true;
        }
        mainView.getCustomerInfoView().showMessage("Phone number must be in the following format: 111-111-1111\n" +
                "(1 is a digit)");
        return false;
    }

    /**
     * Checks if the client type entered is in the correct format and outputs an error message if not correct.
     *
     * @return
     */
    private boolean checkClientType() {
        if (mainView.getCustomerInfoView().getClientType().getText().contentEquals("C") || mainView.getCustomerInfoView().getClientType().getText().contentEquals("R")) {
            return true;
        }
        mainView.getCustomerInfoView().showMessage("Client type must be either commercial (C) or residential (R)");
        return false;
    }

    /**
     * Checks if the client first name entered is in the correct format and outputs an error message if not correct.
     *
     * @return
     */
    private boolean checkFirstName() {
        if (mainView.getCustomerInfoView().getFirstName().getText().length() > 20) {
            mainView.getCustomerInfoView().showMessage("Can not have a first name greater than 20 characters");
            return false;
        } else if (mainView.getCustomerInfoView().getFirstName().getText().length() == 0) {
            mainView.getCustomerInfoView().showMessage("Must have first name");
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
        if (mainView.getCustomerInfoView().getLastName().getText().length() > 20) {
            mainView.getCustomerInfoView().showMessage("Can not have a last name greater than 20 characters");
            return false;
        } else if (mainView.getCustomerInfoView().getLastName().getText().length() == 0) {
            mainView.getCustomerInfoView().showMessage("Must have last name");
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
        if (mainView.getCustomerInfoView().getAddress().getText().length() > 50) {
            mainView.getCustomerInfoView().showMessage("Can not have an address greater than 50 characters");
            return false;
        } else if (mainView.getCustomerInfoView().getAddress().getText().length() == 0) {
            mainView.getCustomerInfoView().showMessage("Must have an address");
            return false;
        }
        return true;
    }

}
