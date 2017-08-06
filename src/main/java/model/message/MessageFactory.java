package model.message;


import model.message.get.MessageKeyReceiv;
import model.message.get.MessageNotifReceiv;
import model.message.send.MessageAskKey;
import model.message.send.MessageSendKey;
import org.json.JSONObject;

import java.util.function.Function;

public enum MessageFactory {
    KEY_RECEIV(json -> new MessageKeyReceiv(), MessageType.KEY_RECEIVE),
    NOTIF_RECEIV(json -> new MessageNotifReceiv(json), MessageType.NOTIF);

    private Function<JSONObject,MessageJsonSocket> creator;
    private MessageType event;

    MessageFactory(Function<JSONObject, MessageJsonSocket> creator, MessageType event){
        this.creator = creator;
        this.event = event;
    }

    public MessageJsonSocket createMessage(JSONObject json){
        return creator.apply(json);
    }


}
