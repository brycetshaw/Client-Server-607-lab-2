package CustomerClient.Controller;

import CustomerClient.View.MainView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ClearListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020/02/08
 */
public class ClearListener extends BaseListener {

    /**
     * Controller related to dealing with actions on the list element.
     */
    private CustomerListListener customerListListener;

    /**
     * Instantiates a new Clear listener.
     *
     * @param mainView             the main view
     * @param in                   the in
     * @param out                  the out
     * @param customerListListener the customer list listener
     */
    public ClearListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out, CustomerListListener customerListListener) {
        super(mainView, in, out);
        this.customerListListener = customerListListener;

        this.mainView.getCustomerInfoView().addClearListener(e -> {
            clearFields();
        });
    }

    /**
     * Clears all the fields in the CustomerInfoView object.  The clearListListener boolean set to false during the
     * clearing of fields in order to prevent the listSelection listener from getting invoked.
     */
    private void clearFields() {
        customerListListener.setListPopulated(false);
        mainView.getSearchCustomerView().getResultArea().clearSelection();
        mainView.getCustomerInfoView().clearFields();
        customerListListener.setListPopulated(true);
    }
}
