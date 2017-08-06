package model;

import model.message.MessageJsonSocket;
import model.message.MessageType;
import model.message.get.MessageNotifReceiv;

public class ObserverMessage {

    private MessageNotifReceiv message;
    private MessageType event;

    public ObserverMessage(MessageNotifReceiv message, MessageType event) {
        this.message = message;
        this.event = event;
    }

    public MessageNotifReceiv getMessage(){
        return message;
    }

}
