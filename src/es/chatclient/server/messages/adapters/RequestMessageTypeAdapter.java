/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.server.messages.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import es.chatclient.server.messages.requests.RequestMessage;
import java.io.IOException;

/**
 *
 * @author Adrián Fernández Cano
 */
public class RequestMessageTypeAdapter extends TypeAdapter<RequestMessage> {

    @Override
    public void write(JsonWriter out, final RequestMessage loginRequest) throws IOException {
        
        out.beginObject();
        
        out.name("name").value(loginRequest.getUserName());
        out.name("userNick").value(loginRequest.getUserNick());
        out.name("userPassword").value(loginRequest.getUserPassword());
        out.name("email").value(loginRequest.getUserName());        
        out.name("requestType").value(loginRequest.getRequestType());
        
        out.endObject();

    }

    @Override
    public RequestMessage read(JsonReader in) throws IOException {

        final RequestMessage loginRequest = new RequestMessage();
        
        in.beginObject();
        
        in.nextName();
        loginRequest.setUserName(in.nextString());
        
        in.nextName();
        loginRequest.setUserNick(in.nextString());
        
        in.nextName();
        loginRequest.setUserPassword(in.nextString());
        
        in.nextName();
        loginRequest.setUserEmail(in.nextString());
        
        in.nextName();
        loginRequest.setRequestType(in.nextInt());
        
        in.endObject();
        
        return loginRequest;
        

        
    }
    
}
