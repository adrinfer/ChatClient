/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.chatclient.controllers.viewcontrollers.ClientGUIController;
import es.chatclient.entities.UserBox;
import es.chatclient.entities.UserMessage;
import es.chatclient.server.messages.adapters.NetworkMessage;
import es.chatclient.server.messages.adapters.RequestMessageTypeAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrián Fernández Cano
 */
public class Controller {
    
    
    public static final int SERVER_PORT = 30000;
    public static final String SERVER_ADDRESS = "localhost";
    
    //Instance of the Gson used in Client
    private Gson gson;
    
    //Socket 
    private Socket socket;
    
    //Output and Input streams in the client
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    
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
        
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(NetworkMessage.class, new RequestMessageTypeAdapter());
        gsonBuilder.setPrettyPrinting();
        

        this.gson = gsonBuilder.create();
        
        
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
                    System.out.println("INSTANCIA UNO");
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
                    System.out.println("INSTANCIA DOS");
                    
                }
            } finally {
                INSTANCIATION_LOCK.unlock();
            }
        }
        return instance;

    }
    
    
    public Gson getGson()
    {
        return this.gson;
    }
    
    public void setSocket(Socket socket)
    {
        try 
        {
            this.socket = socket;
            this.dataInput = new DataInputStream(socket.getInputStream());
            this.dataOutput = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex) 
        {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Socket getSocket()
    {
        return this.socket;
    }
    
    
    //Close the socket conection with the server
    public void closeConexion() throws IOException
    {
        if(this.socket != null)
        {
            this.socket.close();
        }
    }
    
    
    public DataInputStream getInputStream()
    {
        return this.dataInput;
    }
    
    
    public DataOutputStream getOutputStream()
    {
        return this.dataOutput;
    }
    
    
    
    //Change the active chat
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

 
    
    //Check the server status
    public boolean getServerStatus(String address, int port)
    {
        
        try (Socket s = new Socket(address, port)) 
        {           
            
            try (DataOutputStream out = new DataOutputStream(s.getOutputStream())) {
                out.writeUTF("checkStatus");
            }
            
            return s.isConnected();
        } 
        catch (IOException ex) 
        {
            
        }
        return false;
        
    }
  
    
    
}
