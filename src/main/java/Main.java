import controler.Controler;
import model.Model;
import view.SystemTrayNotif;
import view.View;

import javax.management.NotificationBroadcasterSupport;
import java.awt.*;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) {
//        Model model = new Model();
//        View view = new View();
//        Controler controler = new Controler(model, view);
        try {
            SystemTrayNotif notif = new SystemTrayNotif();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}