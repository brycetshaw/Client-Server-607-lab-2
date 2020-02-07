package CustomerClient.View;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * SearchClientView class and its instance methods and variables.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */

public class SearchClientView extends JPanel{
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
    public SearchClientView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        searchResultsTitle = new JLabel("Search Results");
        searchResultsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        listModel = new DefaultListModel();
        resultArea = new JList(listModel);
        scrollPane = new JScrollPane(resultArea);

        resultArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultArea.setLayoutOrientation(JList.VERTICAL);
        resultArea.setVisibleRowCount(10);

        add(searchResultsTitle);
        add(scrollPane);
    }

    /**
     * Adds a listener to the list to listen for list selections.
     * @param listSelectionListener
     */
    public void addListListener(ListSelectionListener listSelectionListener){
        this.resultArea.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    /**
     * Outputs a message box with a message to the user.
     * @param message
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Gets the list model inside of the JList object.
     * @return
     */
    public DefaultListModel getListModel() {
        return listModel;
    }

    /**
     * Gets the JList object.
     * @return
     */
    public JList getResultArea() {
        return resultArea;
    }

}
