package controler;

import io.socket.emitter.Emitter;
import model.Model;
import model.message.MessageType;
import view.View;

public class Controler {
     private Model model;
     private View view;

    public Controler(Model model, View view) {
        this.model = model;
        this.view = view;
        model.addObserver(view);
        listenNotif();
    }

    private void listenNotif(){
        model.listenSocket(MessageType.NOTIF, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Notif received");
                model.addNewNotif(MessageType.NOTIF, (String)args[0]);
            }
        });
    }


}
