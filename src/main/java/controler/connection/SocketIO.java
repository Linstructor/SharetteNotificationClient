package controler.connection;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import controler.message.MessageJsonSocket;
import controler.message.MessageType;
import utils.ConnectionState;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class SocketIO {

    private Socket socket;
    private boolean connected = false;
    private static SocketIO instance = new SocketIO();
    private SecurityConnection securityConnection;

    //Observer connection state
    private Set<SocketStatusListener> stateListeners = new HashSet<>();
    public void addStateListener(SocketStatusListener listener){
        stateListeners.add(listener);
    }
    public void removeStateListener(SocketStatusListener listener){
        stateListeners.remove(listener);
    }

    public static SocketIO getInstance() {
        return instance;
    }

    private SocketIO() {
    }

    public void stop(){
        socket.disconnect();
    }

    public void connect(String url, int port){
        IO.Options options = new IO.Options();
        options.multiplex = true;
        options.reconnection = false;
        options.timeout = 5000;
        try {
            socket = IO.socket("http://"+url+":"+port, options);
            manageStateListener();
            socket.connect();
            securityConnection = new SecurityConnection();
            securityConnection.securiseSocket(this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return connected;
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

    private void manageStateListener(){
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
//                System.out.println("Timed out");
                stateListeners.forEach(listener -> listener.stateChange(ConnectionState.DISCONNECT));
            }
        });
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
//                System.out.println("Connected");
                stateListeners.forEach(listener -> listener.stateChange(ConnectionState.OK));
                connected = true;
            }
        });
        socket.on(Socket.EVENT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
//                System.out.println("Error socket");
                stateListeners.forEach(listener -> listener.stateChange(ConnectionState.DISCONNECT));
            }
        });
        socket.on(Socket.EVENT_CONNECTING, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
//                System.out.println("Connecting socket");
                stateListeners.forEach(listener -> listener.stateChange(ConnectionState.CONNECTING));
            }
        });
        socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
//                System.out.println("Connection erro socket");
                stateListeners.forEach(listener -> listener.stateChange(ConnectionState.DISCONNECT));
            }
        });
        socket.on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
//                System.out.println("Reconnect socket");
                stateListeners.forEach(listener -> listener.stateChange(ConnectionState.CONNECTING));
            }
        });
        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
//                System.out.println("Disconnected socket");
                stateListeners.forEach(listener -> listener.stateChange(ConnectionState.DISCONNECT));
                connected = false;
            }
        });
    };
}
