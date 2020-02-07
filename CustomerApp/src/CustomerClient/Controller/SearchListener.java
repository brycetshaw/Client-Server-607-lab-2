package CustomerClient.Controller;

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

public class SearchListener {
    /**
     * Input and output streams.
     */
    private ObjectInputStream in;
    private ObjectOutputStream out;
    /**
     * Panel containing GUI elements related to search criteria for clients.
     */
    private SearchCriteriaView searchCriteriaView;
    /**
     * Panel containing GUI elements related to displaying the search results to the user.
     */
    private SearchClientView searchClientView;
    /**
     * Controller related to dealing with actions on the list element.
     */
    private ClientListListener clientListListener;

    /**
     * This constructs the SaveListener object and adds a listener to listen to a mouse click on the search button.
     *
     *
     * @param searchCriteriaView
     * @param searchClientView
     * @param clientListListener
     */
    public SearchListener(ObjectOutputStream out, ObjectInputStream in, SearchCriteriaView searchCriteriaView,
                          SearchClientView searchClientView, ClientListListener clientListListener) {
        this.out = out;
        this.in = in;
        this.searchCriteriaView = searchCriteriaView;
        this.searchClientView = searchClientView;
        this.clientListListener = clientListListener;

        this.searchCriteriaView.addSearchListener(e -> {
            try {
                populateSearchResults(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Searches for the client(s) based on the radio button selected and populates the list element with the results.
     *
     * @param suppressMessages
     */
    public void populateSearchResults(Boolean suppressMessages) throws IOException, ClassNotFoundException {
        clientListListener.setListPopulated(false);
        searchClientView.getListModel().removeAllElements();
        String searchCriteria = searchCriteriaView.getSearchField().getText();

        if (searchCriteriaView.getClientId().isSelected()) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCommand("GETID");
            ArrayList<Customer> customers = new ArrayList<>();
            Customer temp = new Customer();
            temp.setId(Integer.parseInt(searchCriteria));
            customers.add(temp);
            customerDto.setCustomers(customers);
            try {
                out.writeObject(customerDto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            CustomerDto output = (CustomerDto) in.readObject();
            if (output != null) {
                for (Customer customer : output.getCustomers()) {
                    searchClientView.getListModel().addElement(customer);
                }
                clientListListener.setListPopulated(true);
            } else {
                if (!suppressMessages) {
                    searchClientView.showMessage("No search results found with the entered parameters");
                }
            }
        } else if (searchCriteriaView.getLastName().isSelected()) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCommand("GETLASTNAME");
            ArrayList<Customer> customers = new ArrayList<>();
            Customer temp = new Customer();
            temp.setLastName(searchCriteria);
            customers.add(temp);
            customerDto.setCustomers(customers);
            try {
                out.writeObject(customerDto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            CustomerDto output = (CustomerDto) in.readObject();
            if (output != null) {
                for (Customer customer : output.getCustomers()) {
                    searchClientView.getListModel().addElement(customer);
                }
                clientListListener.setListPopulated(true);
            } else {
                if (!suppressMessages) {
                    searchClientView.showMessage("No search results found with the entered parameters");
                }
            }
        } else if (searchCriteriaView.getClientType().isSelected()) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCommand("GETTYPE");
            ArrayList<Customer> customers = new ArrayList<>();
            Customer temp = new Customer();
            temp.setCustomerType(searchCriteria);
            customers.add(temp);
            customerDto.setCustomers(customers);
            try {
                out.writeObject(customerDto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            CustomerDto output = (CustomerDto) in.readObject();
            if (output != null) {
                for (Customer customer : output.getCustomers()) {
                    searchClientView.getListModel().addElement(customer);
                }
                clientListListener.setListPopulated(true);
            } else {
                if (!suppressMessages) {
                    searchClientView.showMessage("No search results found with the entered parameters");
                }
            }
        }
    }

    /**
     * Searches for clients based on either last name or client type and populates the list if found,
     * otherwise displays error message.
     * @param suppressMessages
     * @param clients
     */
//    private void searchClient(Boolean suppressMessages, ArrayList<Client> clients) {
//        ArrayList<Client> clientArray = clients;
//        if (clientArray.size() != 0) {
//            for (Client client : clientArray) {
//                searchClientView.getListModel().addElement(client);
//            }
//            clientListListener.setListPopulated(true);
//        } else {
//            if (!suppressMessages) {
//                searchClientView.showMessage("No search results found with the entered parameters");
//            }
//        }
//    }
//
//    /**
//     * Searches for the client based on Id and populates the list if found, otherwise displays error message.
//     * @param suppressMessages
//     * @param searchCriteria
//     */
//    private void searchClientId(Boolean suppressMessages, String searchCriteria) {
//        try {
//            Client client = clientManager.searchClientId(searchCriteria);
//            if (client != null) {
//                searchClientView.getListModel().addElement(client);
//                clientListListener.setListPopulated(true);
//            }
//            else {
//                if (!suppressMessages) {
//                    searchClientView.showMessage("No search results found with the entered parameters");
//                }
//            }
//        } catch (NumberFormatException e) {
//            if (!suppressMessages) {
//                searchClientView.showMessage("No search results found with the entered parameters");
//            }
//        }
//    }
}