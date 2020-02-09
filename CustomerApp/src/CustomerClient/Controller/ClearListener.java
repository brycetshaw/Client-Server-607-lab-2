package CustomerClient.Controller;


import CustomerClient.View.MainView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ClearListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */

public class ClearListener extends BaseListener {

    /**
     * Controller related to dealing with actions on the list element.
     */
    private ClientListListener clientListListener;


    public ClearListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out, ClientListListener clientListListener) {
        super(mainView, in, out);
        this.clientListListener = clientListListener;

        this.mainView.getClientInfoView().addClearListener(e -> {
            clearFields();
        });
    }

    /**
     * Clears all the fields in the ClientInfoView object.  The clearListListener boolean set to false during the
     * clearing of fields in order to prevent the listSelection listener from getting invoked.
     */
    private void clearFields() {
        clientListListener.setListPopulated(false);
        mainView.getSearchClientView().getResultArea().clearSelection();
        mainView.getClientInfoView().clearFields();
        clientListListener.setListPopulated(true);
    }
}
