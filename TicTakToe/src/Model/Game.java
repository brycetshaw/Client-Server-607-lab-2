package Model;


public class Game implements Constants {

    public static char addPlayer(Model.Board theBoard, String readLine) {
        if (theBoard.getxPlayer().equals("")) {
            theBoard.setxPlayer(readLine);
            theBoard.setMessage(theBoard.getxPlayer() + " is playing as X.");
            return Constants.LETTER_X;
        } else {
            theBoard.setyPlayer(readLine);
            theBoard.setNeedsPlayers(false);
            theBoard.setMessage(theBoard.getyPlayer() + " is playing as O. " + theBoard.getMessage());
            return Constants.LETTER_O;
        }
    }


    public static boolean checkWinner(Board theBoard, char mark) {
        int row, col;
        int result = 0;

        char[][] gameState = theBoard.getTheBoard();

        for (row = 0; row < 3; row++) {
            int row_result = 1;
            for (col = 0; row_result == 1 && col < 3; col++)
                if (gameState[row][col] != mark)
                    row_result = 0;
            if (row_result != 0)
                return true;
        }


        for (col = 0; col < 3; col++) {
            int col_result = 1;
            for (row = 0; col_result != 0 && row < 3; row++)
                if (gameState[row][col] != mark)
                    col_result = 0;
            if (col_result != 0)
                return true;
        }

        int diag1Result = 1;
        for (row = 0; diag1Result != 0 && row < 3; row++) {
            if (gameState[row][row] != mark)
                diag1Result = 0;
        }
        if (diag1Result != 0)
            return true;

        int diag2Result = 1;
        for (row = 0; diag2Result != 0 && row < 3; row++) {
            if (gameState[row][3 - 1 - row] != mark)
                diag2Result = 0;
        }
        if (diag2Result != 0)
            return true;

        return false;
    }


    public static void makeMove(Board theBoard, char mark, int[] move) {
        char[][] gameState = theBoard.getTheBoard();
        try {
            if (gameState[move[0]][move[1]] == Constants.SPACE_CHAR) {
                gameState[move[0]][move[1]] = mark;
                theBoard.setToPlay(getOpponent(mark));
                theBoard.setTheBoard(gameState);

                if (checkWinner(theBoard, mark)||Game.isFull(theBoard)){
                    resolveWinner(theBoard, mark);
                }
            }

        } catch (Exception e) {
        }
    }

    public static char getOpponent(char mark) {
        return (mark == Constants.LETTER_X) ? Constants.LETTER_O : Constants.LETTER_X;
    }

    public static boolean isFull(Board theBoard) {
        return (theBoard.getCount() > 8);
    }


    public static void resolveWinner(Board theBoard, char mark) {
        if (checkWinner(theBoard, mark)) {
            String name = name(theBoard, mark);
            theBoard.setMessage(name + " is the winner.\n\tGood for you.\n\tI hope this makes you happy.");
        } else {
            theBoard.setMessage("the board is full, but no one won.\nI hope you're happy with yourselves");

        }
        theBoard.setRunning(false);
    }

    private static String name(Board theBoard, char mark){
        return  (mark == Constants.LETTER_X) ? theBoard.getxPlayer() : theBoard.getyPlayer();
    }



    public boolean isPlaying(Board theBoard, char mark) {
        return theBoard.getToPlay() == mark;
    }

    public static boolean moveIsValid(Board theBoard, int[] move) {
        char[][] gameState = theBoard.getTheBoard();
        try {
            if (gameState[move[0]][move[1]] == Constants.SPACE_CHAR) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static String getMessage(Board theBoard, char mark, String name) {
        String st = name + " playing as " + mark + "\n\n";
        if(mark == theBoard.getToPlay()){
            st += "It's your turn.\n\n";
        } else {
            st += "waiting for " + name(theBoard, getOpponent(mark))+ "\n\n";
        }
        st += theBoard.getMessage();
//        if (theBoard.getToPlay() == mark) {
//
//            st += "It's your turn.";
//        } else {
//            st += "Waiting for " +
//                    (getOpponent(mark));
//        }
        return st;
    }
}

