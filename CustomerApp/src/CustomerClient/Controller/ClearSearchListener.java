package CustomerClient.Controller;

import CustomerClient.View.SearchClientView;
import CustomerClient.View.SearchCriteriaView;

/**
 * ClearSearchListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */

public class ClearSearchListener {
    /**
     * Controller related to dealing with actions on the list element.
     */
    private ClientListListener clientListListener;
    /**
     * Panel containing GUI elements related to search criteria for clients.
     */
    private SearchCriteriaView searchCriteriaView;
    /**
     * Panel containing GUI elements related to the form fields for a client.
     */
    private SearchClientView clientView;

    /**
     * This constructs the ClearSearchListener object and adds a listener to listen to a mouse click on the clear button.
     * @param searchCriteriaView
     * @param clientView
     * @param clientListListener
     */
    public ClearSearchListener(SearchCriteriaView searchCriteriaView, SearchClientView clientView, ClientListListener clientListListener) {
        this.searchCriteriaView = searchCriteriaView;
        this.clientView = clientView;
        this.clientListListener = clientListListener;

        this.searchCriteriaView.addClearSearchListener(e->{
            clearSearch();
        });
    }

    /**
     * Clears all the fields in the SearchCriteriaView and the list in SearchClientView object.
     * The clearListListener boolean set to false during the
     * clearing of fields in order to prevent the listSelection listener from getting invoked.
     */
    public void clearSearch(){
        clientListListener.setListPopulated(false);
        clientView.getListModel().removeAllElements();
        searchCriteriaView.getSearchField().setText("");
        searchCriteriaView.getButtonGroup().clearSelection();
    }
}
