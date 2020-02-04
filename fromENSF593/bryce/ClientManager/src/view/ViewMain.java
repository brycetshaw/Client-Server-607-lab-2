package view;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * The type View main.
 */
public class ViewMain extends JFrame {
    private JTextPane clientID;
    private JTextPane firstName;
    private JTextPane lastName;
    private JTextPane address;
    private JTextPane phoneNumber;
    private JComboBox<Character> clientType;

    private JButton saveButton;
    private JButton clearButton;
    private JButton deleteButton;

    private JComboBox<String> searchParameter;
    private JTextField searchField;
    private JButton searchButton;
    private JScrollPane results;
    private JList<String> resultsContent;

    /**
     * Instantiates a new View main.
     */
    public ViewMain() {
        super("So you've got some clients to manage..");
//        setSize(600,500);
        setLayout(new GridLayout(1,2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeEverything();


        this.clearButton.addActionListener(e -> clearButtonClick());

        JButton clearSearchButton = new JButton("Clear Search");
        clearSearchButton.addActionListener(e -> searchClearButtonClick());

        JPanel rightSide = new JPanel(new GridLayout(8,2));
        rightSide.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel leftSide = new JPanel(new GridLayout(2,1));
        JPanel searchClients = new JPanel(new GridLayout(5,1));
        JPanel searchButtons = new JPanel(new FlowLayout());
        JPanel searchResults = new JPanel(new GridLayout(2,1));
        searchResults.add(new JLabel("Search Results:"));
        searchResults.add(results);
        searchClients.add(new JLabel("Search Clients"), JFrame.CENTER_ALIGNMENT);
        searchClients.add(new JLabel("Select type of search to be performed: "));
        searchClients.add(searchParameter);
        searchClients.add(new JLabel("Enter the search parameter below:"));
        searchButtons.add(searchField);
        searchButtons.add(searchButton);
        searchButtons.add(clearSearchButton);
        searchClients.add(searchButtons);
        searchField.setPreferredSize(new Dimension(90,20));

        leftSide.add(searchClients);
        leftSide.add(searchResults);


        rightSide.add(new JLabel("Client Information ", SwingConstants.RIGHT));
        rightSide.add(new JLabel("- Leave id blank to create new entry"));
        rightSide.add(new JLabel("Client ID"));
        rightSide.add(clientID);
        rightSide.add(new JLabel("First Name"));
        rightSide.add(firstName);
        rightSide.add(new JLabel("Last Name"));
        rightSide.add(lastName);
        rightSide.add(new JLabel("Address"));
        rightSide.add(address);
        rightSide.add(new JLabel("Phone number"));
        rightSide.add(phoneNumber);
        rightSide.add(new JLabel("Client Type"));
        rightSide.add(clientType);
        JPanel rightSideButtons = new JPanel(new GridLayout(1,3));
        rightSide.add(saveButton);
        rightSideButtons.add(deleteButton);
        rightSideButtons.add(clearButton);
        rightSide.add(rightSideButtons);
        add(leftSide);
        add(rightSide);
        pack();
        setVisible(true);
    }

    private void initializeEverything() {
        Character[] clientTypes = { 'C', 'R' };
        String[] searchTypes = { "Client ID", "Last Name", "Client Type"};

        this.clientType = new JComboBox<>(clientTypes);
        this.searchParameter = new JComboBox<>(searchTypes);
        this.clientID = new JTextPane();
        this.firstName = new JTextPane();
        this.lastName = new JTextPane();
        this.address = new JTextPane();
        this.phoneNumber = new JTextPane();
        this.deleteButton = new JButton("Delete");
        this.searchField = new JTextField();
        this.searchButton = new JButton("Search");
        this.saveButton = new JButton("Save");
        this.clearButton = new JButton("Clear");
        this.resultsContent = new JList<String>();
        this.results = new JScrollPane(this.resultsContent);
        results.setLayout( new ScrollPaneLayout());
        resultsContent.setPreferredSize(new Dimension(500,300));
        results.setPreferredSize(new Dimension(500,300));

    }


    public void clearButtonClick() {
        clientID.setText("");
        firstName.setText("");
        lastName.setText("");
        address.setText("");
        phoneNumber.setText("");

    }

    private void searchClearButtonClick() {
        searchField.setText("");
    }

    /**
     * Search button add listener.
     *
     * @param actionListener the action listener
     */
    public void searchButtonAddListener(ActionListener actionListener) {
        searchButton.addActionListener(actionListener);
    }

    /**
     * Save button add listener.
     *
     * @param actionListener the action listener
     */
    public void saveButtonAddListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    /**
     * Delete button add listener.
     *
     * @param actionListener the action listener
     */
    public void deleteButtonAddListener(ActionListener actionListener) {
        deleteButton.addActionListener(actionListener);
    }

    /**
     * List selection add listener.
     *
     * @param listSelectionListener the list selection listener
     */
    public void listSelectionAddListener(ListSelectionListener listSelectionListener) {
        resultsContent.addListSelectionListener(listSelectionListener);
    }


    /**
     * Get search parameters string [ ].
     *
     * @return the string [ ]
     */
    public String[] getSearchParameters(){
        String[] res = new String[2];
        res[0] = Integer.toString(searchParameter.getSelectedIndex());
        res[1] = searchField.getText();
        return res;
    }

    /**
     * Populate search results.
     *
     * @param searchResults the search results
     */
    public void populateSearchResults(String[] searchResults) {
        this.resultsContent.setListData(searchResults);
    }

    /**
     * List index int.
     *
     * @return the int
     */
    public int listIndex() {
        return resultsContent.getSelectedIndex();
    }

    /**
     * Sets contents.
     *
     * @param data the data
     */
    public void setContents(String[] data) {
        clientID.setText(data[0]);
        firstName.setText(data[1]);
        lastName.setText(data[2]);
        address.setText(data[3]);
        phoneNumber.setText(data[4]);
        clientType.setSelectedIndex((data[5].equals("C")) ? 0 : 1);
    }

    public void unselectResult() {
        this.resultsContent.clearSelection();
    }

    /**
     * Get contents string [ ].
     *
     * @return the string [ ]
     */
    public String[] getContents() {
        String[] results = new String[6];
        results[0] = clientID.getText();
        results[1] = firstName.getText();
        results[2] = lastName.getText();
        results[3] = address.getText();
        results[4] = phoneNumber.getText();
        results[5] = (clientType.getSelectedIndex() == 0) ? "C" :"R";
        return results;
    }


    public void setWarningMsg(String text){
        Toolkit.getDefaultToolkit().beep();
        JOptionPane optionPane = new JOptionPane(text,JOptionPane.WARNING_MESSAGE);
        JDialog dialog = optionPane.createDialog("Warning!");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
}
