package model.message.send;

import model.message.MessageJsonSocket;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageSendInfo extends MessageJsonSocket {

    private String userId = "abcdefg";
    private String name = "YHubbert";
    private String token = "token";

    @Override
    public String createJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userid", userId);
            jsonObject.put("name", name);
            jsonObject.put("token", token);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}