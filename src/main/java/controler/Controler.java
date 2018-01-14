package controler;

import controler.connection.SocketIO;
import controler.connection.SocketStatusListener;
import controler.message.MessageType;
import model.Model;
import utils.ConnectionState;
import view.SystemTrayNotif;
import view.View;

public class Controler implements SocketStatusListener{
     private Model model;
     private View view;

     private SocketIO socket;

    public Controler(Model model, View view) {
        this.model = model;
        this.view = view;
        model.addNotifListener(view);
        model.addConnectionListener(SystemTrayNotif.getInstance());
        initConnection();
        socket.on(MessageType.NOTIF.getEvent(), objects -> {
            System.out.println("Notif received");
            model.addNewNotif(MessageType.NOTIF, socket.decrypt((String)objects[0]));
        });
    }

    private void initConnection(){
        socket = SocketIO.getInstance();
        socket.addStateListener(this);
        socket.connect("192.168.0.19",8081);
    }

    @Override
    public void stateChange(ConnectionState newState) {
        model.setSocketStatus(newState);
    }
}
