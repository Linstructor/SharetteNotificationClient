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

    private SocketIO socket;
    public Controler() {
        socket = SocketIO.connect("localhost",8081);
    }


}
