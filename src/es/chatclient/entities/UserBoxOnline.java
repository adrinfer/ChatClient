/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.entities;

import es.chatclient.resources.Images;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author adrinfer
 */
public class UserBoxOnline {
    
    
    
    
    
    
    
    
    
    
    
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
      
        
        VBox vBox = new VBox();
              

        vBox.getChildren().addAll(nick);
        
        vBox.setAlignment(Pos.CENTER);
        
        
        //CSS
        borderPane.getStyleClass().add("userBox");
        vBox.getStyleClass().add("borderPaneVBox");
        
        
        //Add final elements
         Platform.runLater(() -> {
            mainPane.getChildren().addAll(image, vBox);
            borderPane.setCenter(mainPane);
        });
        
//         borderPane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println("Tile pressed ");
//                logicController.setActiveChat(current);
//                
//            }
//       });
         
        return borderPane;
    }
    
    
}
