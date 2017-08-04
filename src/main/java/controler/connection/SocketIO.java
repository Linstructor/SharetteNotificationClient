package controler.connection;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import model.History;
import model.message.MessageJsonSocket;
import model.message.MessageType;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SocketIO {

    private Socket socket;
    private static SocketIO instance = null;
    private SecurityConnection securityConnection;
    private History history;

    public static SocketIO getInstance() {
        if (instance == null){
            throw new NullPointerException("Le serveur n'a pas été instancié");
        }
        return instance;
    }

    private SocketIO(String url, int port) throws URISyntaxException {
        IO.Options options = new IO.Options();
        options.reconnection = true;
        options.multiplex = true;
        socket = IO.socket("http://localhost:8081", options);
        socket.connect();
        securityConnection = new SecurityConnection();
        securityConnection.securiseSocket(this);
    }

    public static SocketIO connect(String url, int port){
        try {
            instance = new SocketIO(url, port);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return getInstance();
    }

    public Emitter on(String event, Emitter.Listener listener){
        return socket.on(event, listener);
    }

    public Emitter emit(MessageType event, MessageJsonSocket message){
        addInHistory(event, message);
        return socket.emit(event.getEvent(), encrypt(message));
    }

    public Emitter emitAck(MessageType event, MessageJsonSocket message, Ack ack){
        addInHistory(event, message);
        return socket.emit(event.getEvent(), encrypt(message), ack);
    }

    public Emitter once(String event, Emitter.Listener fn) {
        return socket.once(event, fn);
    }

    public Emitter off(String event) {
        return socket.off(event);
    }

    private String encrypt(MessageJsonSocket message){
        if (securityConnection.isComplete())
            return securityConnection.encryptMessage(message.createJSON().toString());
        return message.createJSON().toString();
    }

    private void addInHistory(MessageType event, MessageJsonSocket message){
        history.add(event, message);
    }
}
