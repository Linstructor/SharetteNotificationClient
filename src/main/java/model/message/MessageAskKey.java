package model.message;

import org.json.JSONObject;
import sun.misc.OSEnvironment;

import java.time.LocalDateTime;
import java.util.Date;

public class MessageAskKey extends MessageJsonSocket {

    private static final String CONTEXT = "I need your public key my lord";
    private LocalDateTime now;
    private static final String OS = System.getProperty("os.name");
    public static final String javaVersion = System.getProperty("java.version");

    public MessageAskKey() {
        now = LocalDateTime.now();
    }


    @Override
    public JSONObject createJSON() {
        return null;
    }
}
