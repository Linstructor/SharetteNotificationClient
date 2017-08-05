package model.message;


import model.message.get.MessageKeyReceiv;
import model.message.get.MessageNotifReceiv;
import model.message.send.MessageAskKey;
import model.message.send.MessageSendKey;
import org.json.JSONObject;

import java.util.function.Function;

public enum MessageFactory {
    KEY_RECEIV(json -> new MessageKeyReceiv()),
    KEY_SEND(json -> new MessageSendKey()),
    KEY_ASK(json -> new MessageAskKey()),
    NOTIF_RECEIV(json -> new MessageNotifReceiv("a", "b"));

    Function<JSONObject,MessageJsonSocket> creator;

    MessageFactory(Function<JSONObject, MessageJsonSocket> creator){
        this.creator = creator;
    }

    public MessageJsonSocket createMessage(JSONObject json){
        return creator.apply(json);
    }


}
