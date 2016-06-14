/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.server.messages.requests;

import es.chatclient.server.messages.adapters.NetworkMessage;

/**
 *
 * @author Adrián Fernández Cano
 */
public class RequestMessage implements NetworkMessage {
    
    //Atributos
    private String name;
    private String userNick;
    private String userPassword;
    private String email;
    private int requestType;
    
    
    //REQUEST TYPES
    public final static int LOGIN = 0;
    public final static int REGISTER = 1;
    public final static int LOGOUT = 2;
    public final static int GET_DATA = 3;
    
    
    
    //Constructor
    public RequestMessage()
    {
        this.name = "";
        this.userNick = "";
        this.userPassword = "";
        this.email = "";
        //this.loginDate = null;
    }
    
    public RequestMessage(String userNick, int requestType)
    {
        this.name = "";
        this.userNick = userNick;
        this.userPassword = "";
        this.email = "";
        this.requestType = requestType;
        //this.loginDate = null;
    }
    
    
    //Constructor
    public RequestMessage(String userNick, String userPassword, int requestType)
    {
        this.name = "";
        this.userNick = userNick;
        this.userPassword = userPassword;
        this.email = "";
        this.requestType = requestType;
    }
    
    //Constructor
    public RequestMessage(String name, String userNick, String userPassword, String email, int requestType)
    {
        this.name = name;
        this.userNick = userNick;
        this.userPassword = userPassword;
        this.email = email;
        this.requestType = requestType;
        
    }
    
    
    public void setUserName(String name)
    {
        this.name = name;
    }
    
    public String getUserName()
    {
        return name;
    }
    
    public void setUserNick(String userNick)
    {
        this.userNick = userNick;
    }
    
    public String getUserNick()
    {
        return userNick;
    }
    
    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }
    
    public String getUserPassword()
    {
        return userPassword;
    }
    
    public void setUserEmail(String email)
    {
        this.email = email;
    }
    
    public String getUserEmail()
    {
        return email;
    }
    
    public void setRequestType(int requestType)
    {
        this.requestType = requestType;
    }
    
    public int getRequestType()
    {
        return requestType;
    }
    
}