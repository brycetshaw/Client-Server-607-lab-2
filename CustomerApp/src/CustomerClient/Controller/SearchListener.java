package CustomerClient.Controller;

import CustomerClient.View.MainView;
import CustomerClient.View.SearchClientView;
import CustomerClient.View.SearchCriteriaView;
import CustomerModel.Customer;
import CustomerModel.CustomerDto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * SearchListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */

public class SearchListener extends BaseListener{

    private ClientListListener clientListListener;

    public SearchListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out, ClientListListener clientListListener) {
        super(mainView, in, out);
        this.clientListListener = clientListListener;

        this.mainView.getSearchCriteriaView().addSearchListener(e -> {
            try {
                populateSearchResults(false);
            } catch (IOException | ClassNotFoundException | NumberFormatException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Searches for the client(s) based on the radio button selected and populates the list element with the results.
     *
     * @param suppressMessages
     */
    public void populateSearchResults(Boolean suppressMessages) throws IOException, ClassNotFoundException, NumberFormatException {
        clearPreviousResults();
        String searchCriteria = mainView.getSearchCriteriaView().getSearchField().getText();

        if (mainView.getSearchCriteriaView().getClientId().isSelected()) {
            CustomerDto customerDto = createIdDto(searchCriteria);
            writeRequest(customerDto);
            CustomerDto output = (CustomerDto) in.readObject();
            outputResponse(suppressMessages, output);
        } else if (mainView.getSearchCriteriaView().getLastName().isSelected()) {
            CustomerDto customerDto = createLastNameDto(searchCriteria);
            writeRequest(customerDto);
            CustomerDto output = (CustomerDto) in.readObject();
            outputResponse(suppressMessages, output);
        } else if (mainView.getSearchCriteriaView().getClientType().isSelected()) {
            CustomerDto customerDto = createTypeDto(searchCriteria);
            writeRequest(customerDto);
            CustomerDto output = (CustomerDto) in.readObject();
            outputResponse(suppressMessages, output);
        } else {
            mainView.getClientInfoView().showMessage("Please select an option");
        }
    }

    private void clearPreviousResults() {
        clientListListener.setListPopulated(false);
        mainView.getSearchClientView().getListModel().removeAllElements();
    }

    private CustomerDto createIdDto(String searchCriteria) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCommand("GETID");
        ArrayList<Customer> customers = new ArrayList<>();
        Customer temp = new Customer();
        temp.setId(Integer.parseInt(searchCriteria));
        customers.add(temp);
        customerDto.setCustomers(customers);
        return customerDto;
    }

    private CustomerDto createLastNameDto(String searchCriteria) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCommand("GETLASTNAME");
        ArrayList<Customer> customers = new ArrayList<>();
        Customer temp = new Customer();
        temp.setLastName(searchCriteria);
        customers.add(temp);
        customerDto.setCustomers(customers);
        return customerDto;
    }

    private CustomerDto createTypeDto(String searchCriteria) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCommand("GETTYPE");
        ArrayList<Customer> customers = new ArrayList<>();
        Customer temp = new Customer();
        temp.setCustomerType(searchCriteria);
        customers.add(temp);
        customerDto.setCustomers(customers);
        return customerDto;
    }

    private void outputResponse(Boolean suppressMessages, CustomerDto output) {
        if (output.getCommand().contentEquals("SUCCESS")) {
            for (Customer customer : output.getCustomers()) {
                mainView.getSearchClientView().getListModel().addElement(customer);
            }
            clientListListener.setListPopulated(true);
        } else if (!suppressMessages && output.getCommand().contentEquals("FAILURE")) {
            mainView.getSearchClientView().showMessage("No search results found with the entered parameters");
        } else {
            mainView.getSearchClientView().showMessage("Something went wrong. Please try again");
        }
    }

    private void writeRequest(CustomerDto customerDto) {
        try {
            out.writeObject(customerDto);
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
