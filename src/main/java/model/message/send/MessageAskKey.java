package model.message.send;

import model.message.MessageJsonSocket;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageAskKey extends MessageJsonSocket {

    private String userId = "abcdefg";
    

    @Override
    public String createJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userid", userId);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
