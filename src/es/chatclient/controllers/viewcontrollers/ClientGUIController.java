/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.controllers.viewcontrollers;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Practicas01
 */
public class ClientGUIController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    private void init()
    {
        
        Gson gson = new Gson();
        
        try 
        {
            Socket s = new Socket("localhost", 53013);
            System.out.println("SOCKET CREADO");
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            System.out.println("OUTPUT CREADO");
            //Request r = new Request(Request.LOGIN);
            //r.setUserData("adrinfer", "adrinfer", "adrinfer", "email");
            //out.writeUTF(gson.toJson(r));
            
            
            //a.writerFor(NetworkMessage.class).writeValues(s.getOutputStream()).write(new RegisterMessage());
            System.out.println("MENSAJE ENVIADO");
            
            DataInputStream in = new DataInputStream(s.getInputStream());
            
            System.out.println("ESPERANDO LECTURA");
            String a = in.readUTF();
            
            System.out.println("LECTURA: " + a);
            
            
            
            
            
            
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ClientGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        
    }    
    
}
