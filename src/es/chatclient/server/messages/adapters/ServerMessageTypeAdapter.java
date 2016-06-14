/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.server.messages.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import es.chatclient.server.messages.Message;
import java.io.IOException;

/**
 *
 * @author adrinfer
 */
public class ServerMessageTypeAdapter extends TypeAdapter<Message> {

    @Override
    public void write(JsonWriter out, Message msg) throws IOException {
        out.beginObject();
        out.name("msgID").value(msg.getMsgID());
        out.name("msgType").value(msg.getMsgType());
        out.name("msgText").value(msg.getMsgText());
        out.name("msgDate").value(msg.getMsgDate());
        out.name("clientId").value(msg.getClientId());
        out.name("converId").value(msg.getConverId());
        out.name("userNick").value(msg.getUserNick());
        out.endObject();

    }

    @Override
    public Message read(JsonReader in) throws IOException {
        
        final Message msg = new Message();
        
        in.beginObject();     
        
        while(in.hasNext())
        {
            switch(in.nextName())
            {
                case "msgID":
                    msg.setMsgID(in.nextString());
                    break;
                    
                case "msgType":
                    msg.setMsgType(in.nextString());
                    break;
                    
                case "msgText":
                    msg.setMsgText(in.nextString());
                    break;
                    
                case "msgDate":
                    msg.setMsgDate(in.nextString());
                    break;
                    
                case "clientId":
                    msg.setClientId(in.nextString());
                    break;
                    
                case "converId":
                    msg.setConverId(in.nextString());
                    break;
                    
                case "userNick":
                    msg.setUserNick(in.nextString());
                    break;
                    
            } //fin switch
            
        }
        
        in.endObject();
        
        return msg;

    }
    
}
