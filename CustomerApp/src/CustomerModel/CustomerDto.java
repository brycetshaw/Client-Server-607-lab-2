package CustomerModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * CustomerDto class and its instance methods and variables.
 * This class is a super class to transfer information to and from the server.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020/02/08
 */
public class CustomerDto implements Serializable {

    private static final long serialVersionUID = 4L;

    /**
     * String to send requests to the server
     */
    private String command;
    private ArrayList<Customer> customers;
    /**
     * Instantiates a new Customer dto.
     */
    public CustomerDto() {
        command = "";
        customers = new ArrayList<>();
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets command.
     *
     * @param command the command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Gets customers.
     *
     * @return the customers
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets customers.
     *
     * @param customers the customers
     */
    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }
}
