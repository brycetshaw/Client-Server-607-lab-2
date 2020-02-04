import controller.Controller;
import model.Model;
import view.ViewMain;

public class Main {

    public static void main(String[] args) {
        Model theModel = new Model();
        ViewMain theView = new ViewMain();
        Controller theController = new Controller(theView, theModel);
    }
}
