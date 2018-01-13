package view;

import model.ModelListener;
import model.ObserverMessage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Observable;
import java.util.Observer;

public class View implements ModelListener {

    public void update(ObserverMessage message) {
        File script = new File(this.getClass().getResource("/notify.sh").getFile());
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
        }
    }
    private void decryptImage(){

    }

    //TODO system to create an systray icon
    //TODO create command executor for notify-send ubuntu and windows10 notification OR create a full stack notification system

}
