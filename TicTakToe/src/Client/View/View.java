package Client.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class View {
    BufferedReader stdIn;

    public View(){
         stdIn = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getResponse(){
        System.out.println();
        try {
            return stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendRequest(String s) {
        System.out.println(s);
    }
}
