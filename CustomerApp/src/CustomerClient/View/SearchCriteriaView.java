package CustomerClient.View;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * SearchCriteriaView class and its instance methods and variables.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */


public class SearchCriteriaView extends JPanel {
    /**
     * Radio buttons used to allow the user to select a search criteria option.
     */
    private JRadioButton clientId, lastName, clientType;
    /**
     * Labels used to show the names of forms and the name of this panel.
     */
    private JLabel selectType, enterSearch, searchClientsTitle;
    /**
     * Text field to allow users to enter search criteria.
     */
    private JTextField searchField;
    /**
     * Buttons used to invoke listeners.
     */
    private JButton searchButton, clearSearchButton;
    /**
     * ButtonGroup object to house radio buttons and allow only one button at most to be selected at a time.
     */
    private ButtonGroup buttonGroup;

    /**
     * Constructs the GUI elements inside of this panel.
     */
    public SearchCriteriaView () {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        searchClientsTitle = new JLabel("Search Clients");
        add(searchClientsTitle);
        selectType = new JLabel("Select type of search to be performed:");
        add(selectType);

        buttonGroup = new ButtonGroup();

        clientId = new JRadioButton("Client ID");
        lastName = new JRadioButton("Last Name");
        clientType = new JRadioButton("Client Type");
        buttonGroup.add(clientId);
        buttonGroup.add(lastName);
        buttonGroup.add(clientType);
        add(clientId);
        add(lastName);
        add(clientType);

        enterSearch = new JLabel("Enter the search parameter below:");
        add(enterSearch);

        searchField = new JTextField();
        add(searchField);

        searchButton = new JButton("Search");
        clearSearchButton = new JButton("Clear Search");
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(searchButton);
        buttonPane.add(clearSearchButton);
        add(buttonPane);
    }

    /**
     * Adds an action listener to the search button.
     * @param actionListener
     */
    public void addSearchListener(ActionListener actionListener) {
        searchButton.addActionListener(actionListener);
    }

    /**
     * Adds an action listener to the clear search button.
     * @param actionListener
     */
    public void addClearSearchListener(ActionListener actionListener) {
        clearSearchButton.addActionListener(actionListener);
    }

    /**
     * Gets the clientId radio button.
     * @return
     */
    public JRadioButton getClientId() {
        return clientId;
    }

    /**
     * Gets the lastName radio button.
     * @return
     */
    public JRadioButton getLastName() {
        return lastName;
    }

    /**
     * Gets the clientType radio button.
     * @return
     */
    public JRadioButton getClientType() {
        return clientType;
    }

    /**
     * Gets the search field object.
     * @return
     */
    public JTextField getSearchField() {
        return searchField;
    }

    /**
     * Gets the radio button group.
     * @return
     */
    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }


}
