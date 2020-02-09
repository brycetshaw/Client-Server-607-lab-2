package CustomerClient.Controller;

import CustomerClient.View.MainView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BaseListener {

    protected MainView mainView;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    public BaseListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out) {
        this.mainView = mainView;
        this.in = in;
        this.out = out;
    }

}
