package model;

import model.message.MessageJsonSocket;
import model.message.MessageType;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class History {

    private Map<MessageType, MessageJsonSocket> sendMessageHistory = new LinkedHashMap<>();
    private Map<MessageType, MessageJsonSocket> receivMessageHistory = new LinkedHashMap<>();
    private File log;
    private static History instance = null;

    public static History getInstance(){
        return (instance == null)? new History() : instance;
    }

    private History() {
        //TODO find log file
    }

    public void add(MessageType type, MessageJsonSocket message){
        if (type.getCategory() == 1){
            sendMessageHistory.put(type, message);
        }else if (type.getCategory() == 2){
            receivMessageHistory.put(type, message);
        }
    }
}
