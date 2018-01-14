package utils;

import java.net.URL;

public enum ConnectionState {
    OK(ConnectionState.class.getResource("/icon_ok.png"), "Connecté au serveur"),
    CONNECTING(ConnectionState.class.getResource("/icon_connect.gif"), "Connection en cours"),
    DISCONNECT(ConnectionState.class.getResource("/icon_disconnect.png"), "Déconnecté"),
    ERROR(ConnectionState.class.getResource("/icon_error.png"), "Une erreur est survenue");

    private URL imagePath;
    private String defaultMessage;
    ConnectionState(URL image, String dftMessage){
        this.imagePath = image;
        this.defaultMessage = dftMessage;
    }

    public URL getImagePath(){
        return imagePath;
    }
    public String getDefaultMessage(){return defaultMessage;}
}
