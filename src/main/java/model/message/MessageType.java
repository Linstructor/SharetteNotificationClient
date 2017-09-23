package model.message;

import model.message.get.MessageKeyReceiv;
import model.message.get.MessageNotifReceiv;
import model.message.send.MessageSendInfo;
import model.message.send.MessageSendKey;

public enum  MessageType {
    KEY_ASK("key_ask", 1, null),
    SEND_INFO("user_info", 1, MessageSendInfo.class),
    KEY_RECEIVE("key_re", 2, MessageKeyReceiv.class),
    KEY_SEND("key_send", 1, MessageSendKey.class),
    NOTIF("notif", 2, MessageNotifReceiv.class);

    private String event;

    /**
     * 1 message send
     * 2 message receive
     */
    private int cat;
    private Class messageClass;

    MessageType(String event, int category, Class messageClass) {
        this.event = event;
        this.cat = category;
        this.messageClass = messageClass;
    }

    public String getEvent(){
        return event;
    }

    public int getCategory(){
        return cat;
    }

    public Class getMessageClass(){
        return messageClass;
    }
}
