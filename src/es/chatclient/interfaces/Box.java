/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.interfaces;

import es.chatclient.entities.UserMessage;
import java.util.List;

/**
 *
 * @author adrinfer
 */
public interface Box {
    
    public List<UserMessage> getMessages();
    
    public void addMessage(UserMessage msg);
    
}
