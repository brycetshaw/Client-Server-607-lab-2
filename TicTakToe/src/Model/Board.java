package Model;

import java.io.Serializable;

public class Board implements Serializable, Constants {

    public static final long serialVersionUID = 4L;
    private char[][] theBoard = new char[3][3];
    private int count;
    private String xPlayer;
    private String yPlayer;
    private char toPlay;
    private boolean isRunning;
    private String message;
    private String gameState;
    private boolean isShutdown;
    private boolean needsPlayers;


    public boolean isShutdown() {
        return isShutdown;
    }

    public void setShutdown(boolean isShutdown) {
        this.isShutdown = isShutdown;
    }

    public Board(){
        theBoard = new char[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3 ; j++){
                theBoard[i][j] = Constants.SPACE_CHAR;
            }
        }
        toPlay = Constants.LETTER_X;
        isRunning = true;
        message = "waiting for players to join";
        xPlayer = "";
        yPlayer = "";
        count = 0;
        isShutdown = false;
        needsPlayers = true;
    }

    public String toString(){
        String st = "";

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                st += theBoard[i][j];
            }
            st += "\n";
        }
        st += "----";
        st += "\nx player: " + xPlayer;
        st += "\no player: " +yPlayer;
        st += "\nto play: " + toPlay;
        st += "\nrunning: " + isRunning;
        st += "\n count: "+ count;
        st += "\n message: " + message;
        return st;
    }


    public char[][] getTheBoard() {
        return theBoard;
    }

    public void setTheBoard(char[][] theBoard) {
        count++;
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

    public int getCount(){return count;}



    public boolean getNeedsPlayers() {
        return needsPlayers;
    }

    public void setNeedsPlayers(boolean b) {
        needsPlayers = b;
    }
}
