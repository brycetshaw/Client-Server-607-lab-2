package Server.Model;

import Server.Server;

import java.io.Serializable;

public class Model implements Serializable {
    private static final long serialVersionUID = 1;

    private char[][] theBoard;
    private String xPlayer;
    private String yPlayer;
    private char toPlay;
    private char winner;

    public Model() {
        theBoard = new char[3][3];

        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                theBoard[i][j] = Constants.SPACE_CHAR;

            }
        }
        this.xPlayer = null;
        this.yPlayer = null;
        toPlay = Constants.LETTER_X;
        winner = Constants.SPACE_CHAR;
    }



    public Model(String xPlayer, String yPlayer) {
        theBoard = new char[3][3];
        this.xPlayer = xPlayer;
        this.yPlayer = yPlayer;
        toPlay = Constants.LETTER_X;
        winner = Constants.SPACE_CHAR;
    }


    //PlayerID = Player name string with player mark appended to the end.
    private boolean checkIfPlayerValid(String playerID) {
        return (playerID.equals((toPlay == Constants.LETTER_X ? xPlayer : yPlayer) + toPlay));
    }

    public synchronized char addPlayer(String name){
        if(xPlayer == null){
            xPlayer = name;
            return Constants.LETTER_X;
        } else {
            yPlayer = name;
            return Constants.LETTER_O;
        }
    }

    public boolean awaitingPlayers() {
        return (xPlayer == null | yPlayer == null);
    }


    /**
     * renders board.
     */
    public void display() {
        //       System.out.println("1");
        displayColumnHeaders();
        //      System.out.println("2");
        addHyphens();
        for (int row = 0; row < 3; row++) {
            addSpaces();
            System.out.print("    row " + row + ' ');
            for (int col = 0; col < 3; col++)
                System.out.print("|  " + getMark(row, col) + "  ");
            System.out.println("|");
            addSpaces();
            addHyphens();
        }
    }

    /**
     * Renders column headers
     */
    void displayColumnHeaders() {
        System.out.print("          ");
        for (int j = 0; j < 3; j++)
            System.out.print("|col " + j);
        System.out.println();
    }

    /**
     * renders hyphens
     */
    void addHyphens() {
        System.out.print("          ");
        for (int j = 0; j < 3; j++)
            System.out.print("+-----");
        System.out.println("+");
    }

    /**
     * renders extra spaces
     */
    void addSpaces() {
        System.out.print("          ");
        for (int j = 0; j < 3; j++)
            System.out.print("|     ");
        System.out.println("|");
    }
    public char getMark(int row, int col) {
        return theBoard[row][col];
    }

    public boolean play(int[] move, char aMark) {
        if(theBoard[move[0]][move[1]] == Constants.SPACE_CHAR){
            theBoard[move[0]][move[1]] = aMark;
            toPlay = (toPlay == Constants.LETTER_X) ? Constants.LETTER_O : Constants.LETTER_X;

            return true;
        }
        System.out.println("invalid move");
        return false;
    }

    public char getToPlay(){
        return toPlay;
    }
}



