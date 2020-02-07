package CustomerClient.Controller;

import CustomerModel.Customer;
import CustomerClient.View.ClientInfoView;
import CustomerClient.View.SearchClientView;

import javax.swing.event.ListSelectionEvent;

/**
 * ClientListListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 2019/11/13
 */

public class ClientListListener {
    /**
     * Panel containing GUI elements related to the form fields for a client.
     */
    private ClientInfoView clientInfoView;
    /**
     * Panel containing GUI elements related to displaying the search results to the user.
     */
    private SearchClientView searchClientView;
    /**
     * Boolean used to control when the list selection listener gets invoked.
     */
    private boolean isListPopulated;

    /**
     * This constructs the ClientListListener object and adds a listener to listen to a mouse click on a list object.
     * @param clientInfoView
     * @param searchClientView
     */
    public ClientListListener(ClientInfoView clientInfoView, SearchClientView searchClientView) {
        this.clientInfoView = clientInfoView;
        this.searchClientView = searchClientView;
        isListPopulated = true;

        this.searchClientView.addListListener(e-> {
            populateInfoFields(e);
        });
    }

    /**
     * Populates the info fields in the ClientInfoView object with the information in the selected list object.
     * @param e
     */
    private void populateInfoFields(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && isListPopulated == true){
            int index = this.searchClientView.getResultArea().getSelectedIndex();
            Customer client = (Customer) this.searchClientView.getListModel().getElementAt(index);
            clientInfoView.getClientId().setText(String.valueOf(client.getId()));
            clientInfoView.getFirstName().setText(client.getFirstName());
            clientInfoView.getLastName().setText(client.getLastName());
            clientInfoView.getAddress().setText(client.getAddress());
            clientInfoView.getPostalCode().setText(client.getPostalCode());
            clientInfoView.getPhoneNum().setText(client.getPhoneNumber());
            clientInfoView.getClientType().setText(client.getCustomerType());
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
