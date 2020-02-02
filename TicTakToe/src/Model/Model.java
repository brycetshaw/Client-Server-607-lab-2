package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class Model implements Serializable {
    private char[][] theBoard;
    private String xPlayer;
    private String yPlayer;
    private char toPlay;
    private char winner;


    public Model(String xPlayer, String yPlayer) {
        theBoard = new char[3][3];
        this.xPlayer = xPlayer;
        this.yPlayer = yPlayer;
        toPlay = Constants.LETTER_X;
        winner = Constants.SPACE_CHAR;
    }

    public boolean play(String playerID, int x, int y) {
        if (!checkIfPlayerValid(playerID)) {
            return false;
        }

        if (theBoard[x][y] != Constants.SPACE_CHAR) {
            return false;
        }

        theBoard[x][y] = toPlay;
        toPlay = (toPlay == Constants.LETTER_X) ? Constants.LETTER_O : Constants.LETTER_X;
        return true;
    }

    //PlayerID = Player name string with player mark appended to the end.
    private boolean checkIfPlayerValid(String playerID) {
        return (playerID.equals((toPlay == Constants.LETTER_X ? xPlayer : yPlayer) + toPlay));
    }
}



