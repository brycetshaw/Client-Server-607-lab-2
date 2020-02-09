package CustomerClient.Controller;

import CustomerClient.View.MainView;
import CustomerModel.Customer;
import CustomerClient.View.ClientInfoView;
import CustomerClient.View.SearchClientView;

import javax.swing.event.ListSelectionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ClientListListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */

public class ClientListListener extends BaseListener{
    /**
     * Boolean used to control when the list selection listener gets invoked.
     */
    private boolean isListPopulated;


    public ClientListListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out) {
        super(mainView, in, out);
        isListPopulated = true;

        this.mainView.getSearchClientView().addListListener(e-> {
            populateInfoFields(e);
        });
    }

    /**
     * Populates the info fields in the ClientInfoView object with the information in the selected list object.
     * @param e
     */
    private void populateInfoFields(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && isListPopulated == true){
            int index = mainView.getSearchClientView().getResultArea().getSelectedIndex();
            Customer client = (Customer) mainView.getSearchClientView().getListModel().getElementAt(index);
            mainView.getClientInfoView().getClientId().setText(String.valueOf(client.getId()));
            mainView.getClientInfoView().getFirstName().setText(client.getFirstName());
            mainView.getClientInfoView().getLastName().setText(client.getLastName());
            mainView.getClientInfoView().getAddress().setText(client.getAddress());
            mainView.getClientInfoView().getPostalCode().setText(client.getPostalCode());
            mainView.getClientInfoView().getPhoneNum().setText(client.getPhoneNumber());
            mainView.getClientInfoView().getClientType().setText(client.getCustomerType());
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
