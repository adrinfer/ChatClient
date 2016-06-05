/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.entities;

import es.chatclient.resources.Images;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author adrinfer
 */
public class UserBox {
    
    
    
    //Deberia tener un objeto cliente, usuario o algo
    
    
    
    
    
    
    
    public BorderPane getUserBox()
    {
        BorderPane borderPane = new BorderPane();
        
        HBox mainPane = new HBox();

        
        mainPane.prefWidthProperty().bind(borderPane.prefWidthProperty());
        mainPane.minWidthProperty().bind(borderPane.minWidthProperty());
        mainPane.maxWidthProperty().bind(borderPane.maxWidthProperty());
        
        ImageView image = new ImageView(Images.CLOSE_ICON);
        
        Label nick = new Label("  Name");
        Label text = new Label("  Text ");
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(nick, text);
        
        Label time = new Label("   4.06.2016");
        
        mainPane.getChildren().addAll(image, vBox, time);
        
        borderPane.getStyleClass().add("userBox");
        
        borderPane.setCenter(mainPane);
        
        return borderPane;
    }
    
    
}
