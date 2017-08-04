package model;

import controler.connection.SocketIO;

public class Model {

    private History history;
    private SocketIO socket;

    public Model() {
        socket = SocketIO.connect("localhost",8081);
    }
}
