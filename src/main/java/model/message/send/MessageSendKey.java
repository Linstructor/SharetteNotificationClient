package model.message.send;

import model.message.MessageJsonSocket;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageSendKey extends MessageJsonSocket {

    private String key;

    public MessageSendKey(String symKey) {
        this.key = symKey;
    }

    @Override
    public String createJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key",key);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
