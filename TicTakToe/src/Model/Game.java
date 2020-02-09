package Model;


public class Game implements Constants{

    public static char addPlayer(Model.Board theBoard, String readLine) {
        if (theBoard.getxPlayer().equals("")) {
            theBoard.setxPlayer(readLine);
            theBoard.setMessage(theBoard.getxPlayer() + " is playing as X.");
            return Constants.LETTER_X;
        } else {
            theBoard.setyPlayer(readLine);
            theBoard.setRunning(true);
            theBoard.setMessage(theBoard.getMessage() +" "+ theBoard.getyPlayer() + " is playing as O.");
            return Constants.LETTER_O;
        }
    }


    public static boolean checkWinner(Board theBoard, char mark) {
        int row, col;
        int result = 0;
        mark = (mark == Constants.LETTER_X) ? Constants.LETTER_O : Constants.LETTER_X;

        char[][] gameState = theBoard.getTheBoard();

        for (row = 0; result == 0 && row < 3; row++) {
            int row_result = 1;
            for (col = 0; row_result == 1 && col < 3; col++)
                if (gameState[row][col] != mark)
                    row_result = 0;
            if (row_result != 0)
                result = 1;
        }


        for (col = 0; result == 0 && col < 3; col++) {
            int col_result = 1;
            for (row = 0; col_result != 0 && row < 3; row++)
                if (gameState[row][col] != mark)
                    col_result = 0;
            if (col_result != 0)
                result = 1;
        }

        if (result == 0) {
            int diag1Result = 1;
            for (row = 0; diag1Result != 0 && row < 3; row++)
                if (gameState[row][row] != mark)
                    diag1Result = 0;
            if (diag1Result != 0)
                result = 1;
        }
        if (result == 0) {
            int diag2Result = 1;
            for (row = 0; diag2Result != 0 && row < 3; row++)
                if (gameState[row][3 - 1 - row] != mark)
                    diag2Result = 0;
            if (diag2Result != 0)
                result = 1;
        }
        return result == 1;
    }


    public static void makeMove(Board theBoard, char mark, int[] move) {
        char[][] gameState = theBoard.getTheBoard();
        try{
//            System.out.println(Character.getNumericValue( gameState[move[0]][move[1]] )+ " == " + Character.getNumericValue(gameState[move[0]][move[1]]) );
            if (gameState[move[0]][move[1]] == Constants.SPACE_CHAR){
                gameState[move[0]][move[1]] = mark;
                theBoard.setTheBoard(gameState);
            }

        } catch (Exception e){ }
    }


    /**
     * renders board.
     */
    public static String display(Board theBoard) {

        char[][] gameState = theBoard.getTheBoard();
        String st = theBoard.getMessage()+"\n";
        //       System.out.println("1");
        st += displayColumnHeaders();
        //      System.out.println("2");
        st += addHyphens();
        for (int row = 0; row < 3; row++) {
            st += addSpaces();
            st += ("    row " + row + ' ');
            for (int col = 0; col < 3; col++)
                st +=("|  " + gameState[row][ col] + "  ");
            st += ("\n|");
            addSpaces();
            addHyphens();
        }
        return st;
    }

    /**
     * Renders column headers
     */
    private static String displayColumnHeaders() {
        String st = ("          ");
        for (int j = 0; j < 3; j++)
            st += ("|col " + j);
        st += "\n";
        return st;
    }

    /**
     * renders hyphens
     */
    private static String addHyphens() {
        String st =("          ");
        for (int j = 0; j < 3; j++)
            st +=("+-----");
        st +=("\n+");
        return st;
    }

    /**
     * renders extra spaces
     */
     private static String addSpaces() {
        String st =("          ");
        for (int j = 0; j < 3; j++)
            st +=("|     ");
        st +=("\n|");
        return st;
    }

    public boolean isPlaying(Board theBoard, char mark) {
         return theBoard.getToPlay() == mark;
    }

    public boolean moveIsValid(Board theBoard, int[] move) {
        char[][] gameState = theBoard.getTheBoard();
        try{
            if (gameState[move[0]][move[1]] == Constants.SPACE_CHAR){

                return true;
            }
        } catch (Exception e){ }
        return false;
    }
}

