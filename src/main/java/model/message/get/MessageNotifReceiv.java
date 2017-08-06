package model.message.get;

import model.message.MessageJsonSocket;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageNotifReceiv extends MessageNotif {

    public MessageNotifReceiv(JSONObject jsonObject) {
        super(jsonObject);
    }
}
