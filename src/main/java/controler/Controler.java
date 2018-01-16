package controler;

import controler.connection.SocketIO;
import controler.connection.SocketStatusListener;
import controler.message.MessageType;
import model.Model;
import utils.ConnectionState;

public class Controler implements SocketStatusListener{
     private Model model;

     private SocketIO socket;
     private String ip = "192.168.0.39";

    public Controler(Model model) {
        this.model = model;
        initConnection();
        socket.on(MessageType.NOTIF.getEvent(), objects -> {
//            System.out.println("Notif received");
            model.addNewNotif(MessageType.NOTIF, socket.decrypt((String)objects[0]));
        });
    }

    public void reconnect(){
        if (socket.isConnected()){
            socket.connect(ip,8081);
        }
    }

    public void quit(){
        socket.stop();
    }

    private void initConnection(){
        socket = SocketIO.getInstance();
        socket.addStateListener(this);
        socket.connect(ip,8081);
    }

    @Override
    public void stateChange(ConnectionState newState) {
        model.setConnectionState(newState);
    }
}
