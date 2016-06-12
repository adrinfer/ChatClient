/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.entities;

import es.chatclient.resources.Images;
import es.chatclient.utils.Utils;
import java.util.Date;
import javafx.application.Platform;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author adrinfer
 */
public class UserMessage {
    
    
    //private Client cliente;
    public static int a = 0;
    
    private String msgDate;
    
    private String message;
    private String msg = "Esto es un texto de prueba para ver como se va quedando la cosa esta" +
                 " alargandolo un poco a ver si llego al final del renglon para ver como quedaría visualmente";
    
    public UserMessage()
    {
        this.msgDate = Utils.getDfMessage().format(new Date());
    }
    
    public UserMessage(String message)
    {
        this.message = message;   
        this.msgDate = Utils.getDfMessage().format(new Date());
    }
    
    public BorderPane getUserMessage()
    {
        BorderPane borderPane = new BorderPane();
        
        borderPane.minWidthProperty().set(400);
        borderPane.maxWidthProperty().set(400);
        borderPane.prefWidthProperty().set(400);
        
        
        HBox mainPane = new HBox();

        
        mainPane.prefWidthProperty().bind(borderPane.prefWidthProperty());
        mainPane.minWidthProperty().bind(borderPane.minWidthProperty());
        mainPane.maxWidthProperty().bind(borderPane.maxWidthProperty());
        
        ImageView image = new ImageView(Images.CLOSE_ICON);
        image.setFitHeight(54);
        image.setFitWidth(54);
        Text date = new Text(msgDate);
        date.getStyleClass().add("timeMessage");
        
        
        
        Text text = new Text(message);
        
        
        VBox vBox = new VBox();
        
        vBox.getStyleClass().add("vBoxMessageBox");
        
        Platform.runLater(() -> {
            vBox.getChildren().addAll(image, date);
            vBox.getStyleClass().add("imageDate");
        });
        
        FlowPane flow = new FlowPane();
        
//        flow.minWidthProperty().bind(borderPane.minWidthProperty());
//        flow.maxWidthProperty().bind(borderPane.maxWidthProperty());
//        flow.prefWidthProperty().bind(borderPane.prefWidthProperty());
        
        text.wrappingWidthProperty().bind(borderPane.widthProperty().subtract(vBox.widthProperty()));
        flow.getStyleClass().add("flowText");
        flow.getChildren().add(text);
        
        DropShadow dropShadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.rgb(14, 139, 224, 1), 5, 0, 0, 0);
        flow.setEffect(dropShadow);
        
        mainPane.getChildren().addAll(vBox, flow);
        mainPane.getStyleClass().add("messageBoxMain");
        borderPane.getStyleClass().add("messageBox");
        
       
        
        borderPane.setCenter(mainPane);
         
        
        //System.out.println("PUTON: " + borderPane.heightProperty().doubleValue());
        return borderPane;
    }
    
}
