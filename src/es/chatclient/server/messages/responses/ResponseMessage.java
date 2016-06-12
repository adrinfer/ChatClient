/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.server.messages.responses;

/**
 *
 * @author Adrián Fernández Cano
 */
public class ResponseMessage {
    
    //Use Status class for the responseType
    private int responseType;
    
    public ResponseMessage(int responseType)
    {
        this.responseType = responseType;
    }
    
    
    
    public void setResponseType(int responseType)
    {
        this.responseType = responseType;
    }
    
    public int getResponseType()
    {
        return this.responseType;
    }
    
      
}
