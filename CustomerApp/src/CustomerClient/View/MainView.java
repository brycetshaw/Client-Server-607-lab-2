package CustomerClient.View;

import javax.swing.*;

/**
 * MainView class and its instance methods and variables.
 * This class houses all the other panels in the GUI.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020 /02/08
 */
public class MainView extends JFrame {
    /**
     * Panel containing GUI elements related to displaying the search results to the user.
     */
    private SearchCustomerView searchCustomerView;
    /**
     * Panel containing GUI elements related to the form fields for a client.
     */
    private CustomerInfoView customerInfoView;
    /**
     * Panel containing GUI elements related to search criteria for clients.
     */
    private SearchCriteriaView searchCriteriaView;

    /**
     * Constructs the frame and assign panels that house all elements of the GUI.
     *
     * @param searchCustomerView the search customer view
     * @param customerInfoView   the customer info view
     * @param searchCriteriaView the search criteria view
     */
    public MainView(SearchCustomerView searchCustomerView, CustomerInfoView customerInfoView, SearchCriteriaView searchCriteriaView) {
        super("Client Management App");
        this.searchCustomerView = searchCustomerView;
        this.customerInfoView = customerInfoView;
        this.searchCriteriaView = searchCriteriaView;

        JPanel leftPanel = addLeftPanel(searchCustomerView, searchCriteriaView);
        JPanel rightPanel = addRightPanel(customerInfoView);
        addSplitPane(leftPanel, rightPanel);
    }

    private void addSplitPane(JPanel leftPanel, JPanel rightPanel) {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        add(splitPane);
    }

    private JPanel addRightPanel(CustomerInfoView customerInfoView) {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(customerInfoView);
        return rightPanel;
    }

    private JPanel addLeftPanel(SearchCustomerView searchCustomerView, SearchCriteriaView searchCriteriaView) {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(searchCustomerView);
        leftPanel.add(searchCriteriaView);
        return leftPanel;
    }

    /**
     * Gets search customer view.
     *
     * @return the search customer view
     */
    public SearchCustomerView getSearchCustomerView() {
        return searchCustomerView;
    }

    /**
     * Gets customer info view.
     *
     * @return the customer info view
     */
    public CustomerInfoView getCustomerInfoView() {
        return customerInfoView;
    }

    /**
     * Gets search criteria view.
     *
     * @return the search criteria view
     */
    public SearchCriteriaView getSearchCriteriaView() {
        return searchCriteriaView;
    }
}
