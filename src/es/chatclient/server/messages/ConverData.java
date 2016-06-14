/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.server.messages;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Contiene Name, ID de una conversación
 * Contiene el array de mensajes de esta conversación.
 * 
 * @author Adrián Fernández
 */
public class ConverData {
    
    private String converID;
    private String converName;
    private List<Message> arrayMessages;
    
    public ConverData()
    {
        this.converID = null;
        this.converName = null;
        this.arrayMessages = new ArrayList();
    }
    
    public ConverData(String converID, String converName, List<Message> arrayMessages)
    {
        this.converID = converID;
        this.converName = converName;
        this.arrayMessages = arrayMessages;
    }
    
    public void setConverID(String id)
    {
        this.converID = id;
    }
    
    public String getConverID()
    {
        return converID;
    }
    
    public void setConverName(String name)
    {
        this.converName = name;
    }
    
    public String getConverName()
    {
        return converName;
    }
    
    public void setConverMessages(List<Message> arraymessages)
    {
        this.arrayMessages = arraymessages;
    }
    
    public List<Message> getConverMessages()
    {
        return  this.arrayMessages;
    }
    
}
