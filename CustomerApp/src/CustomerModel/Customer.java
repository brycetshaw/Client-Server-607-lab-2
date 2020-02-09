package CustomerModel;

import java.io.Serializable;

/**
 * Customer class and its instance methods and variables.
 * This class is the main model of the application.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020/02/08
 */

public class Customer implements Serializable {

    private static final long serialVersionUID = 4L;

    /**
     * Class variable used to create a new id for a new client.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Instance variable that represents the system assigned Id of a client.
     */
    private int id;
    /**
     * Instance variable that represents the first name of a client.
     */
    private String firstName;
    /**
     * Instance variable that represents the last name of a client.
     */
    private String lastName;
    /**
     * Instance variable that represents the address of a client.
     */
    private String address;
    /**
     * Instance variable that represents the postal code of a client.
     */
    private String postalCode;
    /**
     * Instance variable that represents the phone number of a client.
     */
    private String phoneNumber;
    /**
     * Instance variable that represents the client type of a client.
     */
    private String customerType;

    /**
     * Instantiates a new Customer.
     */
    public Customer () {}

    /**
     * Constructor to be used when fetching client information from the database.
     *
     * @param id          the id
     * @param firstName   the first name
     * @param lastName    the last name
     * @param address     the address
     * @param postalCode  the postal code
     * @param phoneNumber the phone number
     * @param clientType  the client type
     */
    public Customer(int id, String firstName, String lastName, String address, String postalCode, String phoneNumber, String clientType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.customerType = clientType;
    }

    /**
     * Gets the Id of a client.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the first name of a client.
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name of a client.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the address of a client.
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the postal code of a client.
     *
     * @return postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets the phone number of a client.
     *
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the client type of a client.
     *
     * @return customer type
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * Method to convert the information of a client into a string.
     * @return
     */
    public String toString() {
        String client = this.id + " " + this.firstName + " " + this.lastName + " " + this.customerType;
        return client;
    }

    /**
     * Sets the first name of a client to the one passed in as a parameter.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the last name of a client to the one passed in as a parameter.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the address of a client to the one passed in as a parameter.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the postal code of a client to the one passed in as a parameter.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Sets the phone number of a client to the one passed in as a parameter.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the client type of a client to the one passed in as a parameter.
     *
     * @param customerType the customer type
     */
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
