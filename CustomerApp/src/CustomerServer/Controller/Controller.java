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
        customerManager = new CustomerManager(); //TODO ADD SYNC
    }

    public void getCustomersById(String Id) {
        try {
            ArrayList<Customer> customers = customerManager.searchCustomerId(Id);
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCommand("SUCCESS");
            customerDto.setCustomers(customers);
            System.out.println(customers.get(0).getLastName());
            out.writeObject(customerDto);
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getCustomersByLastName(String lastName) {
        try {
            ArrayList<Customer> customers = customerManager.searchCustomerLastName(lastName);
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCommand("SUCCESS");
            customerDto.setCustomers(customers);
            out.writeObject(customerDto);
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getCustomersByCustomerType(String customerType) {
        try {
            ArrayList<Customer> customers = customerManager.searchCustomerType(customerType);
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCommand("SUCCESS");
            customerDto.setCustomers(customers);
            out.writeObject(customerDto);
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCustomer(Customer customer) {
        customerManager.addItem(customer);
        //TODO add success or fail message
    }

    public void editCustomer(Customer customer) {
        customerManager.updateItem(customer);
        //TODO add success or fail message
    }

    public void deleteCustomer(Customer customer) {
        customerManager.deleteItem(customer.getId());
        //TODO add success or fail message
    }

    @Override
    public void run() {
        CustomerDto customerDto = null;
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
                        ArrayList<Customer> existingCustomer = customerManager.searchCustomerId(
                                Integer.toString(customerDto.getCustomers().get(0).getId()));
                        if (existingCustomer.size() == 0) {
                            createCustomer(customerDto.getCustomers().get(0));
                        } else {
                            editCustomer(customerDto.getCustomers().get(0));
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
        } try {
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
