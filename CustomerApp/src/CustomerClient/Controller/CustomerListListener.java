package CustomerClient.Controller;

import CustomerClient.View.MainView;
import CustomerModel.Customer;

import javax.swing.event.ListSelectionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * CustomerListListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020/02/08
 */

public class CustomerListListener extends BaseListener{
    /**
     * Boolean used to control when the list selection listener gets invoked.
     */
    private boolean isListPopulated;

    public CustomerListListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out) {
        super(mainView, in, out);
        isListPopulated = true;

        this.mainView.getSearchCustomerView().addListListener(e-> {
            populateInfoFields(e);
        });
    }

    /**
     * Populates the info fields in the CustomerInfoView object with the information in the selected list object.
     * @param e
     */
    private void populateInfoFields(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && isListPopulated == true){
            int index = mainView.getSearchCustomerView().getResultArea().getSelectedIndex();
            Customer client = (Customer) mainView.getSearchCustomerView().getListModel().getElementAt(index);
            mainView.getCustomerInfoView().getClientId().setText(String.valueOf(client.getId()));
            mainView.getCustomerInfoView().getFirstName().setText(client.getFirstName());
            mainView.getCustomerInfoView().getLastName().setText(client.getLastName());
            mainView.getCustomerInfoView().getAddress().setText(client.getAddress());
            mainView.getCustomerInfoView().getPostalCode().setText(client.getPostalCode());
            mainView.getCustomerInfoView().getPhoneNum().setText(client.getPhoneNumber());
            mainView.getCustomerInfoView().getClientType().setText(client.getCustomerType());
        }
    }

    /**
     * Sets the boolean to true or false, depending on the caller of the function.
     * @param listPopulated
     */
    public void setListPopulated(boolean listPopulated) {
        isListPopulated = listPopulated;
    }
}
