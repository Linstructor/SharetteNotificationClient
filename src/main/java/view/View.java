package view;

import model.ObserverMessage;

import java.util.Observable;
import java.util.Observer;

public class View implements Observer{
    @Override
    public void update(Observable observable, Object o) {
        ObserverMessage message = (ObserverMessage)o;
        System.out.println("I display a notification : WHO: "+message.getMessage().getSender()+" TEXT: "+message.getMessage().getContent());
        CommandExecutor.getInstance().exec("notify-send \""+message.getMessage().getSender()+"\" \"def\"");
        this.getClass().getResource("notify.sh");
    }

    //TODO system to create an systray icon
    //TODO create a pattern observer on Model History
    //TODO create command executor for notify-send ubuntu and windows10 notification OR create a full stack notification system

}
