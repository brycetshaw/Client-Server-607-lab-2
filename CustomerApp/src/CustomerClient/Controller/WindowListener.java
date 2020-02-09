package CustomerClient.Controller;

import CustomerClient.View.MainView;
import CustomerModel.CustomerDto;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WindowListener extends BaseListener{
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
