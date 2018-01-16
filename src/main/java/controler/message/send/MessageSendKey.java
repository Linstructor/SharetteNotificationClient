package controler.message.send;

import controler.message.MessageJsonSocket;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageSendKey extends MessageJsonSocket {

    private String key;
    private String iv;

    public MessageSendKey(String symKey, String iv) {
        this.key = symKey;
        this.iv =iv;
    }

    @Override
    public String createJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key",key);
            jsonObject.put("iv",iv);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
