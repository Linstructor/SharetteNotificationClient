package model.message;

public enum  MessageType {
    KEY_ASK("key_ask", 1),
    KEY_RECEIVE("key_re", 2),
    KEY_SEND("key_se", 1),
    NOTIF("notif", 2);

    private String event;

    /**
     * 1 message send
     * 2 message receive
     */
    private int cat;

    MessageType(String event, int category) {
        this.event = event;
        this.cat = category;
    }

    public String getEvent(){
        return event;
    }

    public int getCategory(){
        return cat;
    }
}
