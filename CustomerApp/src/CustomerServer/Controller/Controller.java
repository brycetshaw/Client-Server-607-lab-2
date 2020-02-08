package CustomerServer.Controller;

import CustomerModel.Customer;
import CustomerModel.CustomerDto;
import CustomerServer.CustomerManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Controller implements Runnable {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private CustomerManager customerManager;

    public Controller(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        customerManager = new CustomerManager();
    }

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

    public void getCustomersByLastName(String lastName) {
        try {
            ArrayList<Customer> customers = customerManager.searchCustomerLastName(lastName);
            returnCustomerArrayToClient(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getCustomersByCustomerType(String customerType) {
        try {
            ArrayList<Customer> customers = customerManager.searchCustomerType(customerType);
            returnCustomerArrayToClient(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void editCustomer(Customer customer) throws IOException {
        if (customerManager.updateItem(customer)) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCommand("EDITSUCCESS");
            out.writeObject(customerDto);
        } else {
            returnEditFailure();
        }
    }

    public void deleteCustomer(Customer customer) throws IOException {
        if (customerManager.deleteItem(customer.getId())) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCommand("DELETESUCCESS");
            out.writeObject(customerDto);
        } else {
            returnEditFailure();
        }
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
                        if (customerDto.getCustomers().get(0).getId() == -1) {
                            createCustomer(customerDto.getCustomers().get(0));
                        } else {
                            ArrayList<Customer> existingCustomer = customerManager.searchCustomerId(
                                    Integer.toString(customerDto.getCustomers().get(0).getId()));
                            if (existingCustomer.size() == 0) {
                                returnFailure();
                            } else {
                                editCustomer(customerDto.getCustomers().get(0));
                            }
                        }
                        break;
                    case "DELETE":
                        deleteCustomer(customerDto.getCustomers().get(0));
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

