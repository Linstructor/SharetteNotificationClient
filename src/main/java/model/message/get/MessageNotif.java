package model.message.get;

import model.message.MessageJsonSocket;
import org.json.JSONObject;

public abstract class MessageNotif extends MessageJsonSocket{

    private String appSender;
    private String contents;
    private String image;

    public MessageNotif(String appSender, String contents) {

        this.appSender = appSender;
        this.contents = contents;
    }

    @Override
    public JSONObject createJSON() {
        return null;
    }
}