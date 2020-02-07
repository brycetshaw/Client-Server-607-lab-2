package CustomerClient.View;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * ClientInfoView class and its instance methods and variables.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */

public class ClientInfoView extends JPanel{
    /**
     * Form fields used to allow users to add/edit client info.
     */
    private JTextField clientId, firstName, lastName, address, postalCode, phoneNum, clientType;
    /**
     * Labels used to designate what each form field is for, as well as label the name of this panel.
     */
    private JLabel clientInfoTitle, clientIdLabel, firstNameLabel, lastNameLabel, addressLabel, postalCodeLabel, phoneNumLabel, clientTypeLabel;
    /**
     * Buttons used to invoke listeners.
     */
    private JButton saveButton, deleteButton, clearButton;

    /**
     * Constructs the GUI elements inside of this panel.
     */
    public ClientInfoView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        clientInfoTitle = new JLabel("Client Information");
        add(clientInfoTitle);

        clientId = new JTextField(4);
        firstName = new JTextField(20);
        lastName = new JTextField(20);
        address = new JTextField(50);
        postalCode = new JTextField(7);
        phoneNum = new JTextField(12);
        clientType = new JTextField(1);

        clientIdLabel = new JLabel("Client ID:");
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        addressLabel = new JLabel("Address:");
        postalCodeLabel = new JLabel("Postal Code:");
        phoneNumLabel = new JLabel("Phone Number:");
        clientTypeLabel = new JLabel("Client Type:");

        JTextField tempFormArray[] = {clientId, firstName, lastName, address, postalCode, phoneNum, clientType};
        JLabel tempLabelArray[] = {clientIdLabel, firstNameLabel, lastNameLabel, addressLabel, postalCodeLabel, phoneNumLabel, clientTypeLabel};

        for (int i = 0; i < tempFormArray.length; i++) {
            JPanel tempPane = new JPanel();
            tempPane.setLayout(new BoxLayout(tempPane, BoxLayout.LINE_AXIS));
            tempPane.add(tempLabelArray[i]);
            tempPane.add(tempFormArray[i]);
            add(tempPane);
        }

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        buttonPane.add(saveButton);
        buttonPane.add(deleteButton);
        buttonPane.add(clearButton);
        add(buttonPane);
    }

    /**
     * Adds an action listener to the save button.
     * @param actionListener
     */
    public void addSaveListener(ActionListener actionListener){
        saveButton.addActionListener(actionListener);
    }

    /**
     * Adds an action listener to the delete button.
     * @param actionListener
     */
    public void addDeleteListener(ActionListener actionListener){
        deleteButton.addActionListener(actionListener);
    }

    /**
     * Adds an action listener to the clear button.
     * @param actionListener
     */
    public void addClearListener(ActionListener actionListener){
        clearButton.addActionListener(actionListener);
    }

    /**
     * Shows a message to the user.
     * @param message
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Gets the client Id text field.
     * @return
     */
    public JTextField getClientId() {
        return clientId;
    }

    /**
     * Gets the first name text field.
     * @return
     */
    public JTextField getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name text field.
     * @return
     */
    public JTextField getLastName() {
        return lastName;
    }

    /**
     * Gets the address text field.
     * @return
     */
    public JTextField getAddress() {
        return address;
    }

    /**
     * Gets the postal code text field.
     * @return
     */
    public JTextField getPostalCode() {
        return postalCode;
    }

    /**
     * Gets the phone number text field.
     * @return
     */
    public JTextField getPhoneNum() {
        return phoneNum;
    }

    /**
     * Gets the client type text field.
     * @return
     */
    public JTextField getClientType() {
        return clientType;
    }

    /**
     * Clears all form fields inside of the panel.
     */
    public void clearFields() {
        this.getClientId().setText("");
        this.getFirstName().setText("");
        this.getLastName().setText("");
        this.getAddress().setText("");
        this.getPostalCode().setText("");
        this.getPhoneNum().setText("");
        this.getClientType().setText("");
    }

}
