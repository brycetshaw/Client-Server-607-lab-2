package model;


/**
 * The type Client.
 */
public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private Character clientType;

    /**
     * Instantiates a new Client.
     *
     * @param firstName   the first name
     * @param lastName    the last name
     * @param address     the address
     * @param phoneNumber the phone number
     * @param clientType  the client type
     */
    public Client( String firstName, String lastName, String address, String phoneNumber, Character clientType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;

        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
    }

    /**
     * Instantiates a new Client.
     *
     * @param id          the id
     * @param firstName   the first name
     * @param lastName    the last name
     * @param address     the address
     * @param phoneNumber the phone number
     * @param clientType  the client type
     */
    public Client(int id, String firstName, String lastName, String address, String phoneNumber, char clientType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;

        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
    }


    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets client type.
     *
     * @return the client type
     */
    public Character getClientType() {
        return clientType;
    }

    @Override
    public String toString() {
        return "" +
                "" + id +
                " " + firstName  +
                " " + lastName +
                ", address=" + address +
                ", phoneNumber=" + phoneNumber +
                ", clientType=" + clientType;
    }

    /**
     * Pass object data string [ ].
     *
     * @return the string [ ]
     */
    public String[] passObjectData() {
        String[] data = new String[6];
        data[0] = Integer.toString(id);
        data[1] = firstName;
        data[2] = lastName;
        data[3] = address;
        data[4] = phoneNumber;
        data[5] = Character.toString(clientType);
        return data;
    }
}
