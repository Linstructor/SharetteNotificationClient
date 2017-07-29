package model.message;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class MessageJsonSocket {

    protected static final DateFormat format = new SimpleDateFormat("dd/MM:yyyy HH:mm:ss");
    public abstract JSONObject createJSON();
}
