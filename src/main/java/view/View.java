package view;

import controler.Controler;
import model.ModelListener;
import model.ObserverMessage;
import utils.CommandExecutor;
import view.views.NotifView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class View implements ModelListener, TrayActionListener {

    private Controler controler;

    public View(Controler controler) {
        this.controler = controler;

    }

    public void update(ObserverMessage message) {
        createNotif(message.getMessage().getSender(), message.getMessage().getContext());
        /*File script = new File(this.getClass().getResource("/notify.sh").getFile());
        try {
            BufferedImage image = null;
            byte[] imageByte;
            if (!message.getMessage().getImage().equals("null")){
                imageByte = Base64.getDecoder().decode(message.getMessage().getImage());
                ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                image = ImageIO.read(bis);
                bis.close();
                File outputfile = new File("image.png");
                ImageIO.write(image, "png", outputfile);
                CommandExecutor.getInstance().exec("bash "+script.getAbsolutePath(), message.getMessage().getSender(), message.getMessage().getContent(), outputfile.getAbsolutePath());
            }
            CommandExecutor.getInstance().exec("bash "+script.getAbsolutePath(), message.getMessage().getSender(), message.getMessage().getContent());
        } catch (IOException e) {
            e.printStackTrace();
            CommandExecutor.getInstance().exec("bash "+script.getAbsolutePath(), message.getMessage().getSender(), message.getMessage().getContent());
        }*/
    }
    private void decryptImage(){

    }

    private void createNotif(String title, String content){
        JFrame frame = new JFrame("notif");
        frame.setContentPane(new NotifView().createNotif(title, content));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void quit() {
        System.out.println("quit input");
        //controler.quit();
    }

    @Override
    public void changeState(Boolean newState) {
        System.out.println("turn: "+newState.toString());
        if (newState) {
            controler.reconnect();
        } else {
            controler.quit();
        }
    }
}
