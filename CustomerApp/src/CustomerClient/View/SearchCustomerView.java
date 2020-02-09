package CustomerClient.View;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * SearchCustomerView class and its instance methods and variables.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020 /02/08
 */
public class SearchCustomerView extends JPanel{
    /**
     * Label used to display the title of the panel.
     */
    private JLabel searchResultsTitle;
    /**
     * JScrollPane used to allow scrolling inside of the JList object.
     */
    private JScrollPane scrollPane;
    /**
     * JList used to show the results of the search.
     */
    private JList resultArea;
    /**
     * List model used to allow the JList object to change the contents of the list.
     */
    private DefaultListModel listModel;

    /**
     * Constructs the GUI elements inside of this panel.
     */
    public SearchCustomerView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createTitleAndAddToPanel();
        createList();
        setResultArea();
        addElements();
    }

    private void createTitleAndAddToPanel() {
        searchResultsTitle = new JLabel("Search Results");
        searchResultsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void setResultArea() {
        resultArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultArea.setLayoutOrientation(JList.VERTICAL);
        resultArea.setVisibleRowCount(10);
    }

    private void addElements() {
        add(searchResultsTitle);
        add(scrollPane);
    }

    private void createList() {
        listModel = new DefaultListModel();
        resultArea = new JList(listModel);
        scrollPane = new JScrollPane(resultArea);
    }

    /**
     * Adds a listener to the list to listen for list selections.
     *
     * @param listSelectionListener the list selection listener
     */
    public void addListListener(ListSelectionListener listSelectionListener){
        this.resultArea.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    /**
     * Outputs a message box with a message to the user.
     *
     * @param message the message
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Gets the list model inside of the JList object.
     *
     * @return list model
     */
    public DefaultListModel getListModel() {
        return listModel;
    }

    /**
     * Gets the JList object.
     *
     * @return result area
     */
    public JList getResultArea() {
        return resultArea;
    }

}
