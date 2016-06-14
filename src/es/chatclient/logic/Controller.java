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
import es.chatclient.server.messages.ConversDataMessage;
import es.chatclient.server.messages.Message;
import es.chatclient.server.messages.adapters.ConversDataMessageTypeAdapter;
import es.chatclient.server.messages.adapters.NetworkMessage;
import es.chatclient.server.messages.adapters.RequestMessageTypeAdapter;
import es.chatclient.server.messages.adapters.ServerMessageTypeAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
    
    //The userNick logged
    private String userNick;
    
    public void setUserNick(String userNick)
    {
        this.userNick = userNick;
    }
    
    public String getUserNick()
    {
        return this.userNick;
    }
    
    
    
    //Instance of the Gson used in Client
    private Gson gson;
    
    private ExecutorService executorService;
    
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
        this.userNick = null;
        this.guiController = guiController;
        this.executorService = Executors.newFixedThreadPool(10);
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(NetworkMessage.class, new RequestMessageTypeAdapter());
        gsonBuilder.registerTypeAdapter(ConversDataMessage.class, new ConversDataMessageTypeAdapter());
        gsonBuilder.registerTypeAdapter(Message.class, new ServerMessageTypeAdapter());
        gsonBuilder.setPrettyPrinting();
        
        
        Message m = new Message("String1", "String2", "String3", "String4", "String5", "String6");
        
        

        this.gson = gsonBuilder.create();
        
        System.err.println("TO JSON MESSAGE");
        String json = gson.toJson(m);
        System.out.println(json);
        
        System.err.println("FROM JSON MESSAGE");
        Message m2 = gson.fromJson(json, Message.class);
        
        System.out.println(m2.getClientId());
        System.out.println(m2.getConverId());
        System.out.println(m2.getMsgText());
        System.out.println(m2.getUserNick());
        
        
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
        
        if(!executorService.isShutdown() && userNick != null)
        {
            executorService.shutdownNow();
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
    
    
    public void sumbitThread(Runnable runnable)
    {
        this.executorService.submit(runnable);
    }
    
    public Future sumbitThread(Callable callable)
    {
        return this.executorService.submit(callable);
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
