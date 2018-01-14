package view;

import controler.connection.SocketStatusListener;
import utils.ConnectionState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SystemTrayNotif implements SocketStatusListener {
    private static SystemTrayNotif systemTrayNotif = new SystemTrayNotif();

    public static SystemTrayNotif getInstance(){
        return systemTrayNotif;
    }

    private TrayIcon trayIcon ;
    private String OS = System.getProperty("os.name").toLowerCase();
    private PopupMenu popup = new PopupMenu();

    private SystemTrayNotif() {
        MenuItem quit = new MenuItem("Quitter");
        MenuItem notifs = new MenuItem();
        notifs.setActionCommand("notif_menuItem");
        notifs.setEnabled(false);
        notifs.setLabel("Aucune notification");
        popup.add(notifs);
        popup.addSeparator();
        popup.add(quit);
        trayIcon = new TrayIcon(new ImageIcon(ConnectionState.OK.getImagePath()).getImage());
        trayIcon.setImageAutoSize(true);
        trayIcon.setPopupMenu(popup);
        setStatus(ConnectionState.DISCONNECT);
        SystemTray systray = SystemTray.getSystemTray();
        trayIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("click "+e.getButton());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        try {
            systray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }



    public void displayNotification(String title, String content, TrayIcon.MessageType type){
        if (OS.contains("win")){
            trayIcon.displayMessage(title,content, type);
        }else{
            System.out.println("Not created");
            //TODO Create unix notification
        }

    }

    private void setStatus(ConnectionState status, String message){
        trayIcon.setImage(new ImageIcon(status.getImagePath().getFile()).getImage());
        trayIcon.setToolTip(message);
    }

    private void setStatus(ConnectionState status){
        trayIcon.setImage(new ImageIcon(status.getImagePath().getFile()).getImage());
        trayIcon.setToolTip(status.getDefaultMessage());
    }

    @Override
    public void stateChange(ConnectionState newState) {
        setStatus(newState);
    }
}

