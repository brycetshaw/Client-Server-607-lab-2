package controller;

import model.ClientInfoInvalidException;
import model.Model;
import view.ViewMain;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private ViewMain theView;
    private Model theModel;

    public Controller(ViewMain theView, Model theModel) {
        this.theModel = theModel;
        this.theView = theView;

        theView.searchButtonAddListener(new searchListener());
        theView.listSelectionAddListener(new searchSelelectListener());
        theView.saveButtonAddListener(new saveButtonListener());
        theView.deleteButtonAddListener(new deleteButtonListener());
        theView.deleteButtonAddListener(new searchListener());

        theModel.removeTable();
        theModel.createTable();
        theModel.fillTable();
        theView.populateSearchResults(theModel.printAll());

    }


    private class searchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String[] searchParameters = theView.getSearchParameters();
            if (searchParameters[1].equals("")) {
                theView.populateSearchResults(theModel.printAll());
                return;
            }
            switch (searchParameters[0]) {
                //{ "Client ID", "Last Name", "Client Type"};
                case "0":
                    theView.populateSearchResults(theModel.searchClient(searchParameters[1], "ID"));

                    break;
                case "1":
                    theView.populateSearchResults(theModel.searchClient(searchParameters[1], "LASTNAME"));
                    //
                    break;
                case "2":
                    theView.populateSearchResults(theModel.searchClient(searchParameters[1], "CLIENTTYPE"));
                    //
                    break;
            }
        }
    }

    public class searchSelelectListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            int index = theView.listIndex();
            if (index == -1) {
                return;
            }
            theView.setContents(theModel.returnAtIndex(index).passObjectData());
        }
    }

    public class saveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                theModel.saveEntry(theView.getContents());
            } catch (ClientInfoInvalidException e) {
                theView.setWarningMsg(e.getMessage());

                int index = theView.listIndex();
                if (index == -1) {
                    return;
                }
                theView.setContents(theModel.returnAtIndex(index).passObjectData());
            }
        }
    }

    public class deleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String[] contents = theView.getContents();
            theView.unselectResult();
            if (! contents[0].isBlank()) {
                theModel.deleteEntry(theView.getContents());
            }
            theView.clearButtonClick();
        }
    }
}
