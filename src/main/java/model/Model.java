package model;

import controler.connection.SocketStatusListener;
import controler.message.MessageFactory;
import controler.message.MessageType;
import controler.message.get.MessageNotifReceiv;
import org.json.JSONException;
import org.json.JSONObject;
import utils.ConnectionState;

import java.util.HashSet;
import java.util.Set;

public class Model {

    //Observer notif
    private Set<ModelListener> notifListeners = new HashSet<>();
    public void addNotifListener(ModelListener listener){
        notifListeners.add(listener);
    }
    public void removeNotifListener(ModelListener listener){
        notifListeners.remove(listener);
    }

    //Observer connection status
    private Set<SocketStatusListener> connectionListeners = new HashSet<>();
    public void addConnectionListener(SocketStatusListener listener){
        connectionListeners.add(listener);
    }
    public void removeConnectionListener(SocketStatusListener listener){
        connectionListeners.remove(listener);
    }

    public Model() {

    }

    private ConnectionState socketStatus = ConnectionState.DISCONNECT;

    public void setSocketStatus(ConnectionState socketStatus) {
        this.socketStatus = socketStatus;
        connectionListeners.forEach(socketStatusListener -> socketStatusListener.stateChange(socketStatus));
    }

    public void addNewNotif(MessageType event, String notif){
        try {
            MessageNotifReceiv messageNotif = (MessageNotifReceiv) MessageFactory.NOTIF_RECEIV.createMessage(new JSONObject(notif));
            ObserverMessage observerMessage = new ObserverMessage(messageNotif, MessageType.NOTIF);
            notifListeners.forEach(listener -> listener.update(observerMessage));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
