package model;

import io.socket.client.Ack;
import io.socket.emitter.Emitter;
import model.connection.SocketIO;
import model.message.MessageFactory;
import model.message.MessageType;
import model.message.get.MessageNotifReceiv;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

public class Model extends Observable {

    private History history;
    private SocketIO socket;

    public Model() {
        history = History.getInstance();
        initConnection();
    }

    private void initConnection(){
        socket = SocketIO.connect("localhost",8081);
    }

    public void listenSocket(MessageType event, Emitter.Listener listener){
        socket.on(event.getEvent(), listener);
    }

    public void addNewNotif(MessageType event, String notif){
        notif = socket.decrypt(notif);
        try {
            MessageNotifReceiv messageNotif = (MessageNotifReceiv) MessageFactory.NOTIF_RECEIV.createMessage(new JSONObject(notif));
            ObserverMessage observerMessage = new ObserverMessage(messageNotif, MessageType.NOTIF);
            setChanged();
            notifyObservers(observerMessage);
            history.add(event, messageNotif);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
