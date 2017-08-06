package model.message.get;

import model.message.MessageJsonSocket;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class MessageNotif extends MessageJsonSocket{

    private String appSender;
    private String content;
    private String image;

    public MessageNotif(JSONObject jsonObject) {
        try {
            this.appSender = jsonObject.getString("app");
            this.content = jsonObject.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSender(){
        return appSender;
    }

    public String getContent(){
        return content;
    }

    @Override
    public String createJSON() {
        return null;
    }
}
