package CustomerServer;

import CustomerModel.Customer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerManager {

    public IdGenerator idGenerator= new IdGenerator(); //how come i cant declare this in the constructor>??

    /**
     * Connection object used to interface with the MySQL database.
     */
    public Connection jdbc_connection;
    /**
     * PreparedStatement object used to send commands to the database.
     */
    public PreparedStatement preparedStatement;
    /**
     * String objects designating the database name, table name, and file name.
     */
    public String databaseName = "CustomerDB", tableName = "customerTable", dataFile = "customers.txt";

    /**
     * String objects used to login into MySql.
     *
     * NOTE: Should configure these variables for own MySQL environment
     * NOTE2: If you have not created your first database in mySQL yet, leave the
     *        "[DATABASE NAME]" blank to get a connection and create one with the createDB() method.
     */
//    public String connectionInfo = "jdbc:mysql://customerdatabase.cerypxhrqdnm.us-east-1.rds.amazonaws.com:3306/customerdb",
//            login          = "admin",
//            password       = "adminadmin";
    public String connectionInfo = "jdbc:mysql://localhost:3306/customerdb",
            login          = "root",
            password       = "Computer,043";

    /**
     * Constructs the ClientManager object and connect to the database.
     */
    public CustomerManager()
    {
        try{
            // If this throws an error, make sure you have added the mySQL connector JAR to the project
            Class.forName("com.mysql.cj.jdbc.Driver");

            // If this fails make sure your connectionInfo and login/password are correct
            jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
            System.out.println("Connected to: " + connectionInfo + "\n");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    /**
     * Creates the database.
     */
    public void createDB()
    {
        try {
            String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            preparedStatement = jdbc_connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Created Database " + databaseName);
        }
        catch( SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a data table inside of the database to hold clients
     */
    public void createTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "ID INT(4) NOT NULL, " +
                "FIRSTNAME VARCHAR(20) NOT NULL, " +
                "LASTNAME VARCHAR(20) NOT NULL, " +
                "ADDRESS VARCHAR(50) NOT NULL, " +
                "POSTALCODE CHAR(7) NOT NULL, " +
                "PHONENUMBER CHAR(12) NOT NULL, " +
                "CUSTOMERTYPE CHAR(1) NOT NULL, " +
                "PRIMARY KEY ( ID ))";
        try{
            preparedStatement = jdbc_connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Created Table " + tableName);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Removes the data table from the database (and all the data held within it!)
     */
    public void removeTable()
    {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try{
            preparedStatement = jdbc_connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Removed Table " + tableName);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void getLatestId() {
        String sql = "SELECT MAX(ID) AS LatestId FROM " + tableName;
        ResultSet result;
        try {
            preparedStatement = jdbc_connection.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            if (result.next()) {
                if ((result.getInt("LatestId") == -1)) {
                    idGenerator.setIdGen(1);
                } else {
                    idGenerator.setIdGen(result.getInt("LatestId") + 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills the data table with all the clients from the text file 'customers.txt' if found
     */
    public void fillTable()
    {
        try{
            getLatestId();
            Scanner sc = new Scanner(new FileReader(dataFile));
            while(sc.hasNext())
            {
                String customerInfo[] = sc.nextLine().split(";");
                addItem( new Customer(
                        idGenerator.getIdGen(),
                        customerInfo[0],
                        customerInfo[1],
                        customerInfo[2],
                        customerInfo[3],
                        customerInfo[4],
                        customerInfo[5]
                ) );
            }
            sc.close();
        }
        catch(FileNotFoundException e)
        {
            System.err.println("File " + dataFile + " Not Found!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Add a customer to the database table
     * @param customer
     */
    public boolean addItem(Customer customer)
    {
        getLatestId();
        String sql = "INSERT INTO " + tableName +
                " (ID, FIRSTNAME, LASTNAME, ADDRESS, POSTALCODE, PHONENUMBER, CUSTOMERTYPE) VALUES (?,?,?,?,?,?,?)";
        try{
            preparedStatement = jdbc_connection.prepareStatement(sql);
            preparedStatement.setInt(1, idGenerator.getIdGen());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getPostalCode());
            preparedStatement.setString(6, customer.getPhoneNumber());
            preparedStatement.setString(7, customer.getCustomerType());
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update an existing client's info in the database table
     * @param customer
     */
    public boolean updateItem(Customer customer)
    {
        String sql = "UPDATE " + tableName +
                " SET FIRSTNAME = ?, LASTNAME = ?, ADDRESS = ?, POSTALCODE = ?, PHONENUMBER = ?, CUSTOMERTYPE = ? WHERE ID = ?";
        try{
            preparedStatement = jdbc_connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getPostalCode());
            preparedStatement.setString(5, customer.getPhoneNumber());
            preparedStatement.setString(6, customer.getCustomerType());
            preparedStatement.setInt(7, customer.getId());
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete an existing client's info in the database table
     * @param ID
     */
    public boolean deleteItem(int ID)
    {
        String sql = "DELETE FROM " + tableName +
                " WHERE ID = ?";
        try{
            preparedStatement = jdbc_connection.prepareStatement(sql);
            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method should search the database table for a client matching the ID parameter and return that client.
     * It returns null if no clients matching that ID are found.
     * @param customerID
     * @return
     */
    public ArrayList<Customer> searchCustomerId(String customerID)
    {
        String sql = "SELECT * FROM " + tableName + " WHERE ID = ? ";
        ResultSet customer;
        try {
            preparedStatement = jdbc_connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(customerID));
            customer = preparedStatement.executeQuery();
            ArrayList <Customer> customerArray = new ArrayList<>();
            if(customer.next())
            {
                customerArray.add(new Customer(customer.getInt("ID"),
                        customer.getString("FIRSTNAME"),
                        customer.getString("LASTNAME"),
                        customer.getString("ADDRESS"),
                        customer.getString("POSTALCODE"),
                        customer.getString("PHONENUMBER"),
                        customer.getString("CUSTOMERTYPE")));
            }
            return customerArray;

        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }

    /**
     * This method should search the database table for a client matching the Last Name parameter and return the list
     * of clients.
     * It returns an empty array if no clients matching that last name are found and returns null if
     * the query failed.
     * @param lastName
     * @return
     */
    public ArrayList<Customer> searchCustomerLastName(String lastName)
    {
        String sql = "SELECT * FROM " + tableName + " WHERE LASTNAME = ? ";
        ResultSet customer;
        try {
            preparedStatement = jdbc_connection.prepareStatement(sql);
            preparedStatement.setString(1, lastName);
            customer = preparedStatement.executeQuery();
            ArrayList <Customer> customerArray = new ArrayList<Customer>();
            while(customer.next())
            {
                customerArray.add(new Customer(customer.getInt("ID"),
                        customer.getString("FIRSTNAME"),
                        customer.getString("LASTNAME"),
                        customer.getString("ADDRESS"),
                        customer.getString("POSTALCODE"),
                        customer.getString("PHONENUMBER"),
                        customer.getString("CUSTOMERTYPE")));
            }
            return customerArray;

        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }

    /**
     * This method should search the database table for a client matching the Customer Type parameter and return the list
     * of customers.
     * It returns an empty array if no customers matching that customer type are found and returns null if
     * the query failed.
     * @param customerType
     * @return
     */
    public ArrayList<Customer> searchCustomerType(String customerType)
    {
        String sql = "SELECT * FROM " + tableName + " WHERE CUSTOMERTYPE = ? ";
        ResultSet customer;
        try {
            preparedStatement = jdbc_connection.prepareStatement(sql);
            preparedStatement.setString(1, customerType);
            customer = preparedStatement.executeQuery();
            ArrayList <Customer> customerArray = new ArrayList<Customer>();
            while(customer.next())
            {
                customerArray.add(new Customer(customer.getInt("ID"),
                        customer.getString("FIRSTNAME"),
                        customer.getString("LASTNAME"),
                        customer.getString("ADDRESS"),
                        customer.getString("POSTALCODE"),
                        customer.getString("PHONENUMBER"),
                        customer.getString("CUSTOMERTYPE")));
            }

            return customerArray;

        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }

    /**
     * Prints all the items in the database to console
     */
    public void printTable()
    {
        try {
            String sql = "SELECT * FROM " + tableName;
            preparedStatement = jdbc_connection.prepareStatement(sql);
            ResultSet customers = preparedStatement.executeQuery();
            System.out.println("Customers:");
            while(customers.next())
            {
                System.out.println(customers.getInt("ID") + " " +
                        customers.getString("FIRSTNAME") + " " +
                        customers.getString("LASTNAME") + " " +
                        customers.getString("ADDRESS") + " " +
                        customers.getString("POSTALCODE") + " " +
                        customers.getString("PHONENUMBER") + " " +
                        customers.getString("CUSTOMERTYPE"));
            }
            customers.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
