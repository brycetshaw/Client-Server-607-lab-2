package CustomerModel;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerDto implements Serializable {

    private static final long serialVersionUID = 4L;

    private String command;
    private ArrayList<Customer> customers;

    public CustomerDto() {
        command = "";
        customers = new ArrayList<>();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }
}
