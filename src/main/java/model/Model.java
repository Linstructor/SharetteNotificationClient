package model;

import io.socket.emitter.Emitter;
import model.connection.SocketIO;
import model.message.MessageFactory;
import model.message.MessageType;
import org.json.JSONException;
import org.json.JSONObject;

public class Model {

    private History history;
    private SocketIO socket;

    public Model() {
        initConnection();
    }

    private void initConnection(){
        socket = SocketIO.connect("localhost",8081);
    }

    public void listenSocket(MessageType event, Emitter.Listener listener){
        socket.on(event.getEvent(), listener);
    }

    public void addNewNotif(MessageType event, String notif){
        try {
            JSONObject jsonObject = new JSONObject(notif);
            history.add(event, MessageFactory.NOTIF_RECEIV.createMessage(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
