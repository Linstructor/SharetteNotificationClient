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

    private Socket socket;
    private static SocketIO instance = null;
    private SecurityConnection securityConnection;

    static SocketIO getInstance() {
        if (instance == null){
            throw new NullPointerException("Connection has not been instantiate");
        }
        return instance;
    }

    private SocketIO(String url, int port) throws URISyntaxException {
        IO.Options options = new IO.Options();
        options.multiplex = true;
        options.reconnection = false;
        options.timeout = 5000;
        socket = IO.socket("http://"+url+":"+port, options);
        socket.connect();
        securityConnection = new SecurityConnection();
        securityConnection.securiseSocket(this);
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Timed out");
            }
        });
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

    public Emitter emitNonSecure(MessageType event, MessageJsonSocket message){
        if (message == null){
            return socket.emit(event.getEvent(), "");
        }else
            return socket.emit(event.getEvent(), message.createJSON());
    }

    public Emitter once(String event, Emitter.Listener fn) {
        return socket.once(event, fn);
    }

    public Emitter off(String event) {
        return socket.off(event);
    }

    private byte[] encrypt(MessageJsonSocket message){
        return securityConnection.encryptMessage(message.createJSON());
    }

    public String decrypt(String message){
        return securityConnection.decrypt(message);
    }
}
