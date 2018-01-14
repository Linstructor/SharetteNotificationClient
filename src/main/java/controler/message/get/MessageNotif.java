package controler.message.get;

import controler.message.MessageJsonSocket;
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
            this.image = jsonObject.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSender(){
        return appSender.replaceAll(" ", "_");
    }

    public String getContent(){
        return content.replaceAll(" ", "_");
    }

    public String getImage() { return image; }

    @Override
    public String createJSON() {
        return null;
    }
}
