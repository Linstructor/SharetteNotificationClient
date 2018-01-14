package controler.connection;

import utils.ConnectionState;

public interface SocketStatusListener {
    void stateChange(ConnectionState newState);
}
