package CustomerClient.Controller;

import CustomerClient.View.MainView;
import CustomerModel.CustomerDto;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * WindowListener class and its instance methods and variables.
 * This class is a controller for this app.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020/02/08
 */
public class WindowListener extends BaseListener{
    /**
     * Instantiates a new Window listener.
     *
     * @param mainView the main view
     * @param in       the in
     * @param out      the out
     */
    public WindowListener(MainView mainView, ObjectInputStream in, ObjectOutputStream out) {
        super(mainView, in, out);

        this.mainView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CustomerDto customerDto = new CustomerDto();
                customerDto.setCommand("QUIT");
                try {
                    out.writeObject(customerDto);
                    out.reset();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
    }
}
