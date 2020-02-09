package CustomerClient.Controller;

import CustomerClient.View.MainView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * BaseListener class and its instance methods and variables.
 * All controllers derive from this class.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020/02/08
 */
public class BaseListener {

    /**
     * The Main view.
     */
    protected MainView mainView;
    /**
     * The In.
     */
    protected ObjectInputStream in;
    /**
     * The Out.
     */
    protected ObjectOutputStream out;

    /**
     * Instantiates a new Base listener.
     *
     * @param mainView the main view
     * @param in       the in
     * @param out      the out
     */
    public BaseListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out) {
        this.mainView = mainView;
        this.in = in;
        this.out = out;
    }

}
