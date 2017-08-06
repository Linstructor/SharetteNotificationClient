package model.connection;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import model.History;
import model.message.MessageJsonSocket;
import model.message.MessageType;

import java.net.URISyntaxException;

public class SocketIO {

    //TODO manage timeout

    private Socket socket;
    private static SocketIO instance = null;
    private SecurityConnection securityConnection;

    static SocketIO getInstance() {
        if (instance == null){
            throw new NullPointerException("Connection has not been instanciate");
        }
        return instance;
    }

    private SocketIO(String url, int port) throws URISyntaxException {
        IO.Options options = new IO.Options();
        options.multiplex = true;
        socket = IO.socket("http://"+url+":"+port, options);
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
        return socket.emit(event.getEvent(), encrypt(message));
    }

    public Emitter emitAck(MessageType event, MessageJsonSocket message, Ack ack){
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
            return securityConnection.encryptMessage(message.createJSON());
        return message.createJSON();
    }
}
