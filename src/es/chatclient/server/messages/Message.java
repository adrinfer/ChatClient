/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.server.messages;


/**
 *
 * @author Adrián Fernández Cano
 */
public class Message {

    
    
    private String msgID;
    private String msgType;
    private String msgText;
    private String msgDate;
    
    private String clientId;
    private String converId;
    
    private String userNick;

    
    public Message()
    {
        
    }
    
    public Message(String msgID, String msgType, String msgText, String msgDate, String clientId, String conerId, String userNick)
    {
        this.msgID = msgID;
        this.msgType = msgType;
        this.msgText = msgText;
        this.msgDate = msgDate;
        this.clientId = clientId;
        this.converId = conerId;
        this.userNick = userNick;
    }
    
    public Message(String msgType, String msgText, String msgDate, String clientId, String converId, String userNick)
    {
        this.msgType = msgType;
        this.msgText = msgText;
        this.msgDate = msgDate;
        this.clientId = clientId;
        this.converId = converId;
        this.userNick = userNick;
    }
 
    
    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getConverId() {
        return converId;
    }

    public void setConverId(String converId) {
        this.converId = converId;
    }
    
    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
    
    
}
