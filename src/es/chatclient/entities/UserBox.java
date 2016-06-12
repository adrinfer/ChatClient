/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.entities;

import es.chatclient.interfaces.Box;
import es.chatclient.logic.Controller;
import es.chatclient.resources.Images;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Adrián Fernández Cano
 */
public class UserBox implements Box {
    
    
    
    //Deberia tener un objeto cliente, usuario o algo
    
    private Controller logicController = Controller.getInstance();
    
    //ArraList of messages to this userBox (conver or client)
    private List<UserMessage> messageList = new ArrayList();
    
    
    private final UserBox current;
    
    public void setMessageList(List<UserMessage> messageList)
    {
        this.messageList = messageList;
    }
    
    public List<UserMessage> getMessageList()
    {
        return messageList;
    }
    
    public UserBox()
    {
        this.current = this;
        
    }
    
    
    public UserBox(List<UserMessage> messageList)
    {
        this.current = this;
        
    }
    
    
    public BorderPane getUserBox()
    {
        BorderPane borderPane = new BorderPane();
        
        
        borderPane.heightProperty().addListener(new ChangeListener<Number>() {
            

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //System.out.println("LISTENER: " + newValue);
            }
        });
        
        
        HBox mainPane = new HBox();

        
        mainPane.prefWidthProperty().bind(borderPane.prefWidthProperty());
        mainPane.minWidthProperty().bind(borderPane.minWidthProperty());
        mainPane.maxWidthProperty().bind(borderPane.maxWidthProperty());
        
        ImageView image = new ImageView(Images.CLOSE_ICON);
        
        Label nick = new Label("  Name");
        Label text = new Label("  Text ");
        
        Text time = new Text("4.06.2016");
        
        VBox vBox = new VBox();
        
        Pane fakePane = new Pane();
        

        vBox.getChildren().addAll(nick, text);
        
        HBox.setHgrow(fakePane, Priority.ALWAYS);
        
        
        //CSS
        borderPane.getStyleClass().add("userBox");
        vBox.getStyleClass().add("borderPaneVBox");
        time.getStyleClass().add("time");
        
        //Add final elements
         Platform.runLater(() -> {
            mainPane.getChildren().addAll(image, vBox, fakePane, time);
            borderPane.setCenter(mainPane);
        });
        
         borderPane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("Tile pressed ");
                logicController.setActiveChat(current);
                
            }
       });
         
        return borderPane;
    }

    @Override
    public List<UserMessage> getMessages() {
        return getMessageList();
    }

    @Override
    public void addMessage(UserMessage msg) {
        this.messageList.add(msg);
        
    }
    
    
}
