package CustomerClient.Controller;

import CustomerClient.View.ClientInfoView;
import CustomerClient.View.SearchClientView;

/**
 * ClearListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */

public class ClearListener {
    /**
     * Panel containing GUI elements related to displaying the search results to the user.
     */
    private SearchClientView searchClientView;
    /**
     * Panel containing GUI elements related to the form fields for a client.
     */
    private ClientInfoView clientInfoView;
    /**
     * Controller related to dealing with actions on the list element.
     */
    private ClientListListener clientListListener;

    /**
     * This constructs the ClearListener object and adds a listener to listen to a mouse click on the clear button.
     * @param searchClientView
     * @param clientInfoView
     * @param clientListListener
     */
    public ClearListener(SearchClientView searchClientView, ClientInfoView clientInfoView, ClientListListener clientListListener) {
        this.searchClientView = searchClientView;
        this.clientInfoView = clientInfoView;
        this.clientListListener = clientListListener;

        this.clientInfoView.addClearListener(e -> {
            clearFields();
        });
    }

    /**
     * Clears all the fields in the ClientInfoView object.  The clearListListener boolean set to false during the
     * clearing of fields in order to prevent the listSelection listener from getting invoked.
     */
    private void clearFields() {
        clientListListener.setListPopulated(false);
        searchClientView.getResultArea().clearSelection();
        clientInfoView.clearFields();
        clientListListener.setListPopulated(true);
    }
}
