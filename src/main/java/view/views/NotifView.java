package view.views;

import javax.swing.*;

public class NotifView {
    private JLabel title;
    private JLabel content;
    private JPanel image;
    private JPanel notif;

    public JPanel createNotif(String title, String content){
        this.title.setText(title);
        this.content.setText(content);
        return notif;
    }
}
