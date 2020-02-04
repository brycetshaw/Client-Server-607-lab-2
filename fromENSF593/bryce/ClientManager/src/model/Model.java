package model;

import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * The type Model.
 */
public class Model {

    /**
     * The Jdbc connection.
     */
    public Connection jdbc_connection;
    /**
     * The Prepared statement.
     */
    public PreparedStatement preparedStatement;
    /**
     * The Directory.
     */
    public String directory = System.getProperty("user.dir") + "/../";
    /**
     * The Database name.
     */
    public String databaseName = "ClientDB",
    /**
     * The Table name.
     */
    tableName = "ClientTable",
    /**
     * The Data file.
     */
    dataFile = directory + "clients.txt";

    /**
     * The Connection info.
     */
// Students should configure these variables for their own MySQL environment
    // If you have not cre3ated your first database in mySQL yet, you can leave the
    // "[DATABASE NAME]" blank to get a connection and create one with the createDB() method.
    public String connectionInfo = "jdbc:mysql://localhost:3306/ClientDB",
    /**
     * The Login.
     */
    login = "bryce",
    /**
     * The Password.
     */
    password = "Mysql4231!";
    private Integer[] localResults;

    /**
     * Instantiates a new Model.
     */
    public Model() {
        try {
            // If this throws an error, make sure you have added the mySQL connector JAR to the project
            Class.forName("com.mysql.cj.jdbc.Driver");

            // If this fails make sure your connectionInfo and login/password are correct
            jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
            System.out.println("Connected to: " + connectionInfo + "\n");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Create table.
     */
// Create a data table inside of the database to hold tools
    public void createTable() {

        String sql = "CREATE TABLE " + tableName + "(" +
                "ID INT(4) AUTO_INCREMENT, " +
                "FIRSTNAME VARCHAR(20) NOT NULL, " +
                "LASTNAME VARCHAR(20) NOT NULL, " +
                "ADDRESS VARCHAR(50) NOT NULL, " +
                "PHONENUMBER CHAR(12) NOT NULL, " +
                "CLIENTTYPE CHAR(1) NOT NULL, " +
                "PRIMARY KEY ( id ))";
        try {
            preparedStatement = jdbc_connection.prepareStatement(sql);
            preparedStatement.execute();
            System.out.println("Created Table " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove table.
     */
// Removes the data table from the database (and all the data held within it!)
    public void removeTable() {
        String sql = "DROP TABLE " + tableName;
        try {
            preparedStatement = jdbc_connection.prepareStatement(sql);
            preparedStatement.execute();
            System.out.println("Removed Table " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fill table.
     */
// Fills the data table with all the tools from the text file 'items.txt' if found
    public void fillTable() {
        try {
            Scanner sc = new Scanner(new FileReader(dataFile));
            while (sc.hasNext()) {
                String[] clientInfo = sc.nextLine().split(";");
                if (clientInfo[4].length() != 12)
                    throw new ClientInfoInvalidException("Phone number has the wrong number of digits! " + clientInfo[3]);
                if (clientInfo[5].length() != 1)
                    throw new ClientInfoInvalidException("Client type should be length 1.");

                addItem(new Client(
                        clientInfo[0],
                        clientInfo[1],
                        clientInfo[2] + " " + clientInfo[3],
                        clientInfo[4],
                        clientInfo[5].charAt(0)));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("File " + dataFile + " Not Found!");
        } catch (ClientInfoInvalidException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add item.
     *
     * @param client the client
     */
    public void addItem(Client client) {
        try {
            String query = " insert into " + tableName + " (FIRSTNAME, LASTNAME, ADDRESS, PHONENUMBER, CLIENTTYPE)"
                    + " values (?, ?, ?, ?, ?)";

            preparedStatement = jdbc_connection.prepareStatement(query);
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.setString(4, client.getPhoneNumber());
            preparedStatement.setString(5, client.getClientType().toString());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private String[] parseDBResults(ResultSet client) throws SQLException {
        ArrayList<String> results = new ArrayList<>();
        ArrayList<Integer> localResults = new ArrayList<>();
        while (client.next()) {
            Client newClient = new Client(client.getInt("ID"),
                    client.getString("FIRSTNAME"),
                    client.getString("LASTNAME"),
                    client.getString("ADDRESS"),
                    client.getString("PHONENUMBER"),
                    client.getString("CLIENTTYPE").charAt(0));
            localResults.add(newClient.getId());
            results.add(newClient.toString());
        }
        client.close();
        this.localResults = localResults.toArray(Integer[]::new);
        return results.toArray(String[]::new);
    }


    /**
     * Search client string [ ].
     *
     * @param detail    the detail
     * @param specifier the specifier
     * @return the string [ ]
     */
    public String[] searchClient(String detail, String specifier) {
        ResultSet client;
        try {
            String query = "SELECT ID, FIRSTNAME, LASTNAME, ADDRESS, PHONENUMBER,CLIENTTYPE FROM " + tableName + " WHERE " + specifier + " = ?";
            preparedStatement = jdbc_connection.prepareStatement(query);
            preparedStatement.setString(1, detail);
            client = preparedStatement.executeQuery();
            return parseDBResults(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Print all string [ ].
     *
     * @return the string [ ]
     */
// Prints all the items in the database to console
    public String[] printAll() {
        try {
            String sql = "SELECT ID, FIRSTNAME, LASTNAME, ADDRESS, PHONENUMBER, CLIENTTYPE FROM " + tableName;
            preparedStatement = jdbc_connection.prepareStatement(sql);
            ResultSet clients = preparedStatement.executeQuery();
            return parseDBResults(clients);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return at index client.
     *
     * @param index the index
     * @return the client
     */
    public Client returnAtIndex(int index) {

        if(index == -1) {
            return null;
        }
        int id = this.localResults[index];

        ResultSet client;
        try {
            String query = "SELECT ID, FIRSTNAME, LASTNAME, ADDRESS, PHONENUMBER,CLIENTTYPE FROM " + tableName + " WHERE ID = ?";

            preparedStatement = jdbc_connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            client = preparedStatement.executeQuery();

            if (client.next()) {
                Client newClient = new Client(client.getInt("ID"),
                        client.getString("FIRSTNAME"),
                        client.getString("LASTNAME"),
                        client.getString("ADDRESS"),
                        client.getString("PHONENUMBER"),
                        client.getString("CLIENTTYPE").charAt(0));
                client.close();
                return newClient;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save entry.
     *
     * @param contents the contents
     */
    public void saveEntry(String[] contents) throws ClientInfoInvalidException {

        for (int i = 1; i < contents.length; i++) {
            if (contents[i].length() == 0) {
                throw new ClientInfoInvalidException("Client Fields cannot be empty.");
            }
        }


        if ((contents[4].length() != 12) || ! contents[4].matches("^(\\+0?1\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$")) {
            throw new ClientInfoInvalidException("Invalid Phone number");
        }

        if(!((contents[5].charAt(0) == 'R')||(contents[5].charAt(0) == 'C')) || (contents[5].length() != 1)) {
            throw new ClientInfoInvalidException("ClientTypeInvalid");
        }

        if (contents[0].equals("")) {
            Client newClient = new Client(contents[1], contents[2], contents[3], contents[4], contents[5].charAt(0));
            addItem(newClient);
        } else {
            Client newClient = new Client(Integer.parseInt(contents[0]), contents[1], contents[2], contents[3], contents[4], contents[5].charAt(0));
            editDB(newClient);
        }
    }

    private void editDB(Client client) {
        try {
            String query = "UPDATE " + tableName + " SET FIRSTNAME=?, LASTNAME =?, ADDRESS=?, PHONENUMBER=?, CLIENTTYPE=? WHERE ID=?";
            preparedStatement = jdbc_connection.prepareStatement(query);
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.setString(4, client.getPhoneNumber());
            preparedStatement.setString(5, client.getClientType().toString());
            preparedStatement.setInt(6, client.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete entry.
     *
     * @param client the client
     */
    public void deleteEntry(String[] client) {
        try {
            String query = "DELETE FROM " + tableName + " WHERE ID=?";
            preparedStatement = jdbc_connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(client[0]));
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
