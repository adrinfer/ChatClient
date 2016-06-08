/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.logic;

import es.chatclient.controllers.viewcontrollers.ClientGUIController;
import es.chatclient.entities.UserBox;
import es.chatclient.entities.UserMessage;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Adrián Fernández Cano
 */
public class Controller {
    
    
    private final ClientGUIController guiController;
    
    //private ClientGUIController guiController;
    
    //Entity that references de active chat or active conversation with a person
    private UserBox activeChat;
    
    public void setActiveChat(UserBox userBox)
    {
        this.activeChat = userBox;
        changeChat(); //Uses the activeChat
    }
    
    public UserBox getActiveChat()
    {
        return activeChat;
    }
    
    
    
    private Controller(ClientGUIController guiController)
    {
        this.guiController = guiController;
    }
    
    private Controller()
    {
        this.guiController = null;
    }
    
    
    //Instancia de la clase (Singleton)
    private static Controller instance = null;
    private final static Lock INSTANCIATION_LOCK = new ReentrantLock();
    
      
    
    public static Controller getInstance(ClientGUIController guicontroller) {

        if (instance == null) {
            INSTANCIATION_LOCK.lock();

            try {
                if (instance == null) //Comprobamos que no se haya inicializado mientras se esperaba al cerrojo
                {
                    instance = new Controller(guicontroller);
                }
            } finally {
                INSTANCIATION_LOCK.unlock();
            }
        }
        return instance;

    }
    
    public static Controller getInstance() {

        if (instance == null) {
            INSTANCIATION_LOCK.lock();

            try {
                if (instance == null) //Comprobamos que no se haya inicializado mientras se esperaba al cerrojo
                {
                    instance = new Controller();
                }
            } finally {
                INSTANCIATION_LOCK.unlock();
            }
        }
        return instance;

    }
    
    
    
    private void changeChat()
    {
        guiController.changeConversation(activeChat);
          
    }
    
    public void addMessage(String msg)
    {
        
        UserMessage message = new UserMessage(msg);
        message.getUserMessage();
        activeChat.addMessage(message);
        guiController.addMessageAndInflate(message);
        
    }

 
    
  
    
    
}
