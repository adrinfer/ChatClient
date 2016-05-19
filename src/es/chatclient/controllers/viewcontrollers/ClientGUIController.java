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
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author Practicas01
 */
public class ClientGUIController implements Initializable {

    @FXML
    private BorderPane borderPane;
    
    @FXML
    private Pane leftPane;
    
    @FXML
    private Button boton;
    
    @FXML
    private Pane centralPane;
    
    
    private TranslateTransition translateTransition;
    
    private boolean bol = true;
    private double with;
    
    private void init()
    {
        //split.setDividerPosition(1, 0.9);
        
        Gson gson = new Gson();
        
        leftPane.minWidthProperty().bind(borderPane.minWidthProperty().multiply(0.2));
        leftPane.maxWidthProperty().bind(borderPane.maxWidthProperty().multiply(0.2));
        leftPane.prefWidthProperty().bind(borderPane.prefWidthProperty().multiply(0.2));
       
        centralPane.minWidthProperty().bind(borderPane.minWidthProperty().subtract(leftPane.translateXProperty().add(leftPane.minWidthProperty())));
        centralPane.maxWidthProperty().bind(borderPane.maxWidthProperty().subtract(leftPane.translateXProperty().add(leftPane.maxWidthProperty())));
        centralPane.prefWidthProperty().bind(borderPane.prefWidthProperty().subtract(leftPane.translateXProperty().add(leftPane.prefWidthProperty())));
        
        centralPane.translateXProperty().bind(leftPane.translateXProperty().add(leftPane.prefWidthProperty()));
        
        
        
//          System.out.println("MIN: " + borderPane.minWidthProperty().getValue());
//          System.out.println("MAX: " + borderPane.maxWidthProperty().getValue());
//          System.out.println("PREF: " + borderPane.prefWidthProperty().getValue());

//          centralPane.minWidthProperty().bind(borderPane.minWidthProperty());
//          centralPane.maxWidthProperty().bind(borderPane.maxWidthProperty());
//          centralPane.prefWidthProperty().bind(borderPane.prefWidthProperty());
        
//          System.out.println("CMIN: " + centralPane.minWidthProperty().getValue());          
//          System.out.println("CMAX: " + centralPane.maxWidthProperty().getValue());
//          System.out.println("CPREF: " + centralPane.prefWidthProperty().getValue());
        //try 
        //{
            //Socket s = new Socket("localhost", 53013);
            //System.out.println("SOCKET CREADO");
            //DataOutputStream out = new DataOutputStream(s.getOutputStream());
            //System.out.println("OUTPUT CREADO");
            //Request r = new Request(Request.LOGIN);
            //r.setUserData("adrinfer", "adrinfer", "adrinfer", "email");
            //out.writeUTF(gson.toJson(r));
            
            
            //a.writerFor(NetworkMessage.class).writeValues(s.getOutputStream()).write(new RegisterMessage());
            //System.out.println("MENSAJE ENVIADO");
            
            //DataInputStream in = new DataInputStream(s.getInputStream());
            
            //System.out.println("ESPERANDO LECTURA");
            //String a = in.readUTF();
            
           // System.out.println("LECTURA: " + a);
            
            
            
            
            
            
       //} 
        //catch (IOException ex) 
        //{
            //Logger.getLogger(ClientGUIController.class.getName()).log(Level.SEVERE, null, ex);
        //}
        
        boton.setText("AAAA");
        
        
        with = 0;
               
        translateTransition = TranslateTransitionBuilder.create()
                .duration(Duration.seconds(4))
                .node(leftPane)
                .fromX(0)
                .toX(0)
                .cycleCount(Timeline.INDEFINITE)
                .autoReverse(true)
                .build();
        
        
        boton.setOnAction(e -> {
            System.out.println("X: " + leftPane.translateXProperty().getValue());
            System.out.println("Xt: " + centralPane.translateXProperty().getValue() );
            if(bol)
            {
                translateTransition.toXProperty().set(-leftPane.getWidth());
               translateTransition.play(); 
               bol = false;
               
               
            }
            else
            {
                translateTransition.stop();
                bol = true;
            }
            
            
            
            
        });
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        
    }    
    
}
