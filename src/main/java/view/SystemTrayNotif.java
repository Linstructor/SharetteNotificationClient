package view;

import javax.swing.*;
import java.awt.*;

public class SystemTrayNotif {
    private TrayIcon trayIcon ;

    public SystemTrayNotif() throws AWTException {
        Image icon = new ImageIcon(this.getClass().getResource("/icon.png").getFile()).getImage();
        trayIcon = new TrayIcon(icon);
        trayIcon.setImageAutoSize(true);
        SystemTray systray = SystemTray.getSystemTray();
        systray.add(trayIcon);
        trayIcon.setActionCommand("Je ne sais pas");
        trayIcon.displayMessage("idk", "idk2", TrayIcon.MessageType.WARNING);
    }

    public void setStatus(TrayStatus status, String message){
        trayIcon.setToolTip(message);
    }


}

enum TrayStatus{
    OK(""),
    SEND(""),
    DISCONNECT(""),
    ERROR("");

    private String imagePath;
    TrayStatus(String image){
        this.imagePath = image;
    }

    public String getImagePath(){
        return imagePath;
    }
}