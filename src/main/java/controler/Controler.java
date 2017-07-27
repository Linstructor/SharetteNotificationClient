package controler;

import controler.connection.SecurityConnection;
import controler.connection.SocketIO;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class Controler {
//    private SocketIO socket;
    private Socket socket;
    private SecurityConnection securityConnection;
    public Controler() {
//        socket.connect("127.0.0.1", 8081);
//        socket = SocketIO.getInstance();
//        securityConnection = SecurityConnection.getInstance();
//        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                System.out.printf((String) args[0]);
//            }
//        }).on("key_p", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                System.out.printf(String.valueOf(args[0]));
//                securityConnection.setSecureServKey((JSONObject)args[0]);
//            }
//        });
        IO.Options options = new IO.Options();
        options.reconnection = true;
        options.multiplex = true;

        securityConnection = SecurityConnection.getInstance();

        try {
            socket = IO.socket("http://localhost:8081", options);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, args -> {
            System.out.println("Connected");
            socket.emit("foo", "hi");
        }).on("key", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                System.out.printf((String) args[0]);
                try {
                    securityConnection.setSecureServKey(new JSONObject((String)args[0]));
                    socket.emit("message", securityConnection.encryptMessage("Ma bite est elle est grande"));
                } catch (JSONException e) {
                    System.out.println("mdr trop des barres");
                    e.printStackTrace();
                }
            }

        });
        socket.connect();
    }


}
