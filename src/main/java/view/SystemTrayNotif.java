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

    private static TrayIcon trayIcon ;
    private PopupMenu popup = new PopupMenu();
    private TrayActionListener listener;
    public void setListener(TrayActionListener listener){
        this.listener = listener;
    }

    private SystemTrayNotif() {
        System.out.println("SystemTrayNotif Création");
        configurePopup();
        trayIcon = new TrayIcon(new ImageIcon(ConnectionState.DISCONNECT.getImagePath()).getImage());
        trayIcon.setImageAutoSize(true);
        trayIcon.setPopupMenu(popup);
        setStatus(ConnectionState.DISCONNECT);
        SystemTray systray = SystemTray.getSystemTray();
        try {
            systray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("Erreur lors de l'ajout de la trayicon");
            e.printStackTrace();
        }
    }

    private void configurePopup() {
        MenuItem quit = new MenuItem("Quitter");
        quit.addActionListener(arg -> listener.quit());
        MenuItem notifs = new MenuItem();
        MenuItem connection = new MenuItem();
        connection.addActionListener(arg -> listener.changeState(Boolean.getBoolean(connection.getActionCommand())));
        notifs.setActionCommand("notif_menuItem");
        notifs.setEnabled(false);
        notifs.setLabel("Aucune notification");
        popup.add(notifs);
        popup.addSeparator();
        popup.add(connection);
        popup.addSeparator();
        popup.add(quit);
    }

    private void setStatus(ConnectionState status, String message){
        trayIcon.setImage(new ImageIcon(status.getImagePath().getFile()).getImage());
        trayIcon.setToolTip(message);
    }

    private void setStatus(ConnectionState status){
//        System.out.println("Set tray icon "+status.toString());
        trayIcon.setImage(new ImageIcon(status.getImagePath().getFile()).getImage());
        trayIcon.setToolTip(status.getDefaultMessage());
    }

    @Override
    public void stateChange(ConnectionState newState) {
        setStatus(newState);
        MenuItem connection = popup.getItem(2);
        if (newState == ConnectionState.DISCONNECT){
            connection.setEnabled(false);
            connection.setLabel("Se reconnecter");
            connection.setActionCommand("false");
        }else if(newState == ConnectionState.OK){
            connection.setEnabled(false);
            connection.setActionCommand("true");
            connection.setLabel("Se déconnecter");
        }else{
            connection.setEnabled(false);
        }
    }
}

