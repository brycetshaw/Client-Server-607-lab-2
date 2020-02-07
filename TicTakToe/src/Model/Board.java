package Model;

import java.io.Serializable;

public class Board implements Serializable, Constants {

    public static final long serialVersionUID = 4L;
    private char[][] theBoard = new char[3][3];
    private String xPlayer;
    private String yPlayer;
    private char toPlay;
    private boolean isRunning;
    private String message;
    private String gameState;



    public Board(){
        theBoard = new char[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3 ; j++){
                theBoard[i][j] = Constants.SPACE_CHAR;
            }
        }
        toPlay = Constants.LETTER_X;
        isRunning = true;
        gameState = "0Players";
        message = "waiting for players to join";
    }


    public char[][] getTheBoard() {
        return theBoard;
    }

    public void setTheBoard(char[][] theBoard) {
        this.theBoard = theBoard;
    }

    public String getxPlayer() {
        return xPlayer;
    }

    public void setxPlayer(String xPlayer) {
        this.xPlayer = xPlayer;
    }

    public String getyPlayer() {
        return yPlayer;
    }

    public void setyPlayer(String yPlayer) {
        this.yPlayer = yPlayer;
    }

    public char getToPlay() {
        return toPlay;
    }

    public void setToPlay(char toPlay) {
        this.toPlay = toPlay;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
