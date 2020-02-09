package CustomerClient.View;

import javax.swing.*;

/**
 * MainView class and its instance methods and variables.
 * This class houses all the other panels in the GUI.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */

public class MainView extends JFrame {
    /**
     * Panel containing GUI elements related to displaying the search results to the user.
     */
    private SearchClientView searchClientView;
    /**
     * Panel containing GUI elements related to the form fields for a client.
     */
    private ClientInfoView clientInfoView;
    /**
     * Panel containing GUI elements related to search criteria for clients.
     */
    private SearchCriteriaView searchCriteriaView;

    /**
     * Constructs the frame and assign panels that house all elements of the GUI.
     *
     * @param searchClientView
     * @param clientInfoView
     * @param searchCriteriaView
     */
    public MainView(SearchClientView searchClientView, ClientInfoView clientInfoView, SearchCriteriaView searchCriteriaView) {
        super("Client Management App");
        this.searchClientView = searchClientView;
        this.clientInfoView = clientInfoView;
        this.searchCriteriaView = searchCriteriaView;

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(searchClientView);
        leftPanel.add(searchCriteriaView);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(clientInfoView);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        add(splitPane);
    }

    public SearchClientView getSearchClientView() {
        return searchClientView;
    }

    public ClientInfoView getClientInfoView() {
        return clientInfoView;
    }

    public SearchCriteriaView getSearchCriteriaView() {
        return searchCriteriaView;
    }
}
