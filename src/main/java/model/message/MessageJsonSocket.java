package model.message;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public abstract class MessageJsonSocket {

    private String context ;
    protected LocalDateTime now = LocalDateTime.now();
    protected static final String OS = System.getProperty("os.name");
    protected static final String javaVersion = System.getProperty("java.version");

    private final DateFormat format = new SimpleDateFormat("dd/MM:yyyy HH:mm:ss");

    public abstract String createJSON();

    public void setContext(String context){
        this.context = context;
    }

    public String getContext(){
        return context;
    }
}
