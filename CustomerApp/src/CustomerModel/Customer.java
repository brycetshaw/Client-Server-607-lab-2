package CustomerModel;

import java.io.Serializable;

/**
 * Customer class and its instance methods and variables.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */


public class Customer implements Serializable {

    private static final long serialVersionUID = 4L;

    /**
     * Class variable used to create a new id for a new client.
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

    public Customer () {}

    /**
     * Constructor to be used when adding a new client.
     * @param firstName
     * @param lastName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param customerType
     */
    public Customer(String firstName, String lastName, String address, String postalCode, String phoneNumber, String customerType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.customerType = customerType;
    }

    /**
     * Constructor to be used when fetching client information from the database.
     * @param id
     * @param firstName
     * @param lastName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param clientType
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
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the first name of a client.
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name of a client.
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the address of a client.
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the postal code of a client.
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets the phone number of a client.
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the client type of a client.
     * @return
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
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the last name of a client to the one passed in as a parameter.
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the address of a client to the one passed in as a parameter.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the postal code of a client to the one passed in as a parameter.
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Sets the phone number of a client to the one passed in as a parameter.
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the client type of a client to the one passed in as a parameter.
     * @param customerType
     */
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
