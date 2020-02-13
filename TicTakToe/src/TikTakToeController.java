//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class TikTakToeController {
//    private TikTakToeModel theModel;
//    private TikTakToeView theView;
//    private NewGame newGame;
//
//    public TikTakToeController(TikTakToeModel theModel, TikTakToeView theView) {
//        setNewGame();
//        this.theModel = theModel;
//        this.theView = theView;
//        addButtonListeners();
//        theView.addNewGameListener(new anotherGameListener());
//
//
//    }
//
//    private void setNewGame() {
//        this.newGame = new NewGame();
//        newGame.addGoListener(new newGameListener());
//    }
//
//    private void addButtonListeners() {
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                boardListener thisListener = new boardListener();
//                theView.addButtonListeners(i, j, thisListener);
//
//            }
//        }
//    }
//
//    private class boardListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//
//            String response = actionEvent.getActionCommand();
//            System.out.println(response);
//            int i = Integer.parseInt(response.substring(0,1));
//            int j = Integer.parseInt(response.substring(2));
//            char mark = theModel.getCurrentPlayer().mark;
//
//            if(((JButton) actionEvent.getSource()).getText().equals(Character.toString(Constants.SPACE_CHAR))) {
//                theModel.markBoard(i,j,mark);
//                theModel.getCurrentPlayer().play();
//
//            }
//            System.out.println(actionEvent.getActionCommand());
//        }
//    }
//
//    private class newGameListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            theView.setVisible(true);
//            int[] gameType = newGame.getGameType();
//            theModel.setGame(gameType);
//            newGame.dispose();
//
//        }
//    }
//
//    private void startNewGame(){
//
//    }
//
//    private class anotherGameListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            setNewGame();
//            theView.setVisible(false);
//            theView = new TikTakToeView();
//            theModel = new TikTakToeModel(theView);
//            addButtonListeners();
//            theView.addNewGameListener(new anotherGameListener());
//
//
//        }
//    }
//
//}
