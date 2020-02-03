import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The type Palendromer.
 */
public class Palendromer implements Runnable {

    private PrintWriter socketOut;

    private BufferedReader socketIn;

    /**
     * Instantiates a new Palendromer.
     *
     * @param in  the in
     * @param out the out
     */
    protected Palendromer(BufferedReader in, PrintWriter out) {
        socketIn = in;
        socketOut = out;
    }

    @Override
    public void run() {
        String line = "";
        while (true) {
            try {
                line = socketIn.readLine();
                if (line.equals("QUIT")) {
                    line = "Good Bye!";
                    socketOut.println(line);
                    break;
                }
                socketOut.println(line + (isPalindrome(line) ? " is" : " is NOT") + " a palindrome");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private boolean isPalindrome(String line) {
        for (int i = 0; i < line.length() / 2; i++) {
            if (line.charAt(i) != line.charAt(line.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

}

