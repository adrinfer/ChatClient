/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.server.messages.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import es.chatclient.server.messages.ConverData;
import es.chatclient.server.messages.ConversDataMessage;
import es.chatclient.server.messages.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrinfer
 */
public class ConversDataMessageTypeAdapter extends TypeAdapter<ConversDataMessage> {

    @Override
    public void write(JsonWriter out, ConversDataMessage conversData) throws IOException {

        out.beginObject();
        out.name("converDataArray").beginArray();
        for(ConverData converData: conversData.getConverDataArray())
        {
            out.beginObject();
            
            out.name("converID").value(converData.getConverID());
            out.name("converName").value(converData.getConverName());
            out.name("arrayMessages").beginArray();
            for(Message msg: converData.getConverMessages())
            {
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
            out.endArray();
            out.endObject();
        }
        out.endArray();
        out.endObject();
        
        
        

    }

    @Override
    public ConversDataMessage read(JsonReader in) throws IOException {

        
        final ConversDataMessage conversDate = new ConversDataMessage();
        final List<ConverData> converDataList = new ArrayList();
        
        in.beginObject();
        
        in.nextName();

        in.beginArray();

        while(in.hasNext())
        {
            in.beginObject();
            final ConverData converData = new ConverData();
           
            while(in.hasNext())
            {
                switch(in.nextName())
                {

                    case "converID":
                        converData.setConverID(in.nextString());

                        break;

                    case "converName":
                        converData.setConverName(in.nextString());
                        break;

                    case "arrayMessages":

                        final List<Message> msgList = new ArrayList();

                        in.beginArray();
                        while(in.hasNext())
                        {
                            in.beginObject();
                            final Message msg = new Message();
                            
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
                            
                            msgList.add(msg);
                                in.endObject();
                            
                        }
                        

                        in.endArray();

                        converData.setConverMessages(msgList);

                        break;

                    default:

                        break;

                }
                
            }
       

            converDataList.add(converData);
            in.endObject();
            

        }

        in.endArray();
        conversDate.setConverDataArray(converDataList);
        
        in.endObject();
        
        return conversDate;

    }
    
}
