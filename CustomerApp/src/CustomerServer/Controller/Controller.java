package CustomerServer.Controller;

import CustomerModel.Customer;
import CustomerModel.CustomerDto;
import CustomerServer.CustomerManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Controller class and its instance methods and variables.
 * This class receives requests and send responses.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020/02/08
 */

public class Controller implements Runnable {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private CustomerManager customerManager;

    /**
     * Instantiates a new Controller.
     *
     * @param socket the socket
     * @throws IOException the io exception
     */
    public Controller(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        customerManager = new CustomerManager();
    }

    /**
     * Gets customers by id.
     *
     * @param Id the id
     */
    public void getCustomersById(String Id) {
        try {
            ArrayList<Customer> customers = customerManager.searchCustomerId(Id);
            returnCustomerArrayToClient(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void returnCustomerArrayToClient(ArrayList<Customer> customers) throws IOException {
        if (customers.size() != 0) {
            returnSuccess(customers);
        } else {
            returnFailure();
        }
    }

    private void returnFailure() throws IOException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCommand("FAILURE");
        out.writeObject(customerDto);
        out.reset();
    }

    private void returnSuccess(ArrayList<Customer> customers) throws IOException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCommand("SUCCESS");
        customerDto.setCustomers(customers);
        out.writeObject(customerDto);
        out.reset();
    }

    /**
     * Gets customers by last name.
     *
     * @param lastName the last name
     */
    public void getCustomersByLastName(String lastName) {
        try {
            ArrayList<Customer> customers = customerManager.searchCustomerLastName(lastName);
            returnCustomerArrayToClient(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets customers by customer type.
     *
     * @param customerType the customer type
     */
    public void getCustomersByCustomerType(String customerType) {
        try {
            ArrayList<Customer> customers = customerManager.searchCustomerType(customerType);
            returnCustomerArrayToClient(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create customer.
     *
     * @param customer the customer
     * @throws IOException the io exception
     */
    public void createCustomer(Customer customer) throws IOException {
        if (customerManager.addItem(customer)) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCommand("CREATESUCCESS");
            out.writeObject(customerDto);
        } else {
            returnEditFailure();
        }
    }

    private void returnEditFailure() throws IOException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCommand(" ");
        out.writeObject(customerDto);
    }

    /**
     * Edit customer.
     *
     * @param customer the customer
     * @throws IOException the io exception
     */
    public void editCustomer(Customer customer) throws IOException {
        if (customerManager.updateItem(customer)) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCommand("EDITSUCCESS");
            out.writeObject(customerDto);
        } else {
            returnEditFailure();
        }
    }

    /**
     * Delete customer.
     *
     * @param customer the customer
     * @throws IOException the io exception
     */
    public void deleteCustomer(Customer customer) throws IOException {
        if (customerManager.deleteItem(customer.getId())) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCommand("DELETESUCCESS");
            out.writeObject(customerDto);
        } else {
            returnEditFailure();
        }
    }

    private void deleteCustomer(CustomerDto customerDto) throws IOException {
        ArrayList<Customer> existingCustomer = checkExistingCustomer(customerDto);
        if (existingCustomer.size() == 0) {
            returnFailure();
        } else {
            deleteCustomer(customerDto.getCustomers().get(0));
        }
    }

    private void editOrCreateCustomer(CustomerDto customerDto) throws IOException {
        if (customerDto.getCustomers().get(0).getId() == -1) {
            createCustomer(customerDto.getCustomers().get(0));
        } else {
            ArrayList<Customer> existingCustomer = checkExistingCustomer(customerDto);
            if (existingCustomer.size() == 0) {
                returnFailure();
            } else {
                editCustomer(customerDto.getCustomers().get(0));
            }
        }
    }

    private ArrayList<Customer> checkExistingCustomer(CustomerDto customerDto) {
        return customerManager.searchCustomerId(
                Integer.toString(customerDto.getCustomers().get(0).getId()));
    }

    @Override
    public void run() {
        CustomerDto customerDto;
        try {
            while (true) {
                customerDto = (CustomerDto) in.readObject();
                if (customerDto.getCommand().contentEquals("QUIT")) {
                    break;
                }
                switch (customerDto.getCommand()) {
                    case "GETID":
                        getCustomersById(Integer.toString(customerDto.getCustomers().get(0).getId()));
                        break;
                    case "GETLASTNAME":
                        getCustomersByLastName(customerDto.getCustomers().get(0).getLastName());
                        break;
                    case "GETTYPE":
                        getCustomersByCustomerType(customerDto.getCustomers().get(0).getCustomerType());
                        break;
                    case "POST":
                        editOrCreateCustomer(customerDto);
                        break;
                    case "DELETE":
                        deleteCustomer(customerDto);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

