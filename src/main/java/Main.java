import controler.Controler;
import model.Model;
import view.SystemTrayNotif;
import view.View;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        model.addConnectionListener(SystemTrayNotif.getInstance());
        Controler controler = new Controler(model);
        View view = new View(controler);
        SystemTrayNotif.getInstance().setListener(view);
        model.addNotifListener(view);
    }
}