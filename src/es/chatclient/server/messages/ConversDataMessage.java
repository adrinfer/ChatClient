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
 * Objeto que se envia al cliente con
 * la información de sus conversaciones
 * 
 * @author adrinfer
 */
public class ConversDataMessage {
    
    private List<ConverData> converDataArray;
    
    
    public ConversDataMessage()
    {
        this.converDataArray = new ArrayList();
    }
    
    public ConversDataMessage(List<ConverData> converDataArray)
    {
        this.converDataArray = converDataArray;
    }
    
    public void setConverDataArray(List<ConverData> converDataArray)
    {
        this.converDataArray = converDataArray;
    }
    
    public List<ConverData> getConverDataArray()
    {
        return this.converDataArray;
    }
}
