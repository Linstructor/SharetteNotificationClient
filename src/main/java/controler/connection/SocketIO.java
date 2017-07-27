package controler.connection;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;

public class SocketIO {

    private static Socket socket;
    private static SocketIO instance = null;

    public static SocketIO getInstance() {
        if (instance == null){
            throw new NullPointerException("Le serveur n'a pas été instancié");
        }
        return instance;
    }

    private SocketIO(String url, int port) throws URISyntaxException {
        socket = IO.socket("http://localhost:8081");
    }

    public static void connect(String url, int port){
        try {
            instance = new SocketIO(url, port);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Emitter on(String event, Emitter.Listener listener){
        return socket.on(event, listener);
    }
}
