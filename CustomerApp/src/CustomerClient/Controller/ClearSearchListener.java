package CustomerClient.Controller;

import CustomerClient.View.MainView;
import CustomerClient.View.SearchClientView;
import CustomerClient.View.SearchCriteriaView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ClearSearchListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */

public class ClearSearchListener extends BaseListener {
    /**
     * Controller related to dealing with actions on the list element.
     */
    private ClientListListener clientListListener;

    public ClearSearchListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out, ClientListListener clientListListener) {
        super(mainView, in, out);
        this.clientListListener = clientListListener;

        this.mainView.getSearchCriteriaView().addClearSearchListener(e->{
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
        mainView.getSearchClientView().getListModel().removeAllElements();
        mainView.getSearchCriteriaView().getSearchField().setText("");
        mainView.getSearchCriteriaView().getButtonGroup().clearSelection();
    }
}
