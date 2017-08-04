package model.message;


import model.message.get.MessageKeyReceiv;
import model.message.get.MessageNotifReceiv;
import model.message.send.MessageAskKey;
import model.message.send.MessageSendKey;

import java.util.function.Function;

public enum MessageFactory {
    KEY_RECEIV(options -> new MessageKeyReceiv()),
    KEY_SEND(options -> new MessageSendKey()),
    KEY_ASK(options -> new MessageAskKey()),
    NOTIF_RECEIV(options -> new MessageNotifReceiv("a", "b"));

    Function<String[],MessageJsonSocket> creator;

    MessageFactory(Function<String[],MessageJsonSocket> creator){
        this.creator = creator;
    }

    public MessageJsonSocket createMessage(){
        return creator.apply(null);
    }


}
