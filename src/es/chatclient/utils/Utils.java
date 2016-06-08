/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.utils;


import chatclient.ChatClient;
import es.chatclient.controllers.viewcontrollers.ClientGUIController;
import java.text.SimpleDateFormat;
import java.util.Objects;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Adrián Fernández Cano
 */
public class Utils {
    
    private static Stage stage;
    
    //Aux used to maximize or not the window
    private static Boolean isMaximized = false;
    private static Boolean canMaximize = true;
    
    
    //Movimiento y posición del stage
    static class Delta { 
         double x, y;   
    }
     
    
    static final Delta DRAG_DELTA = new Delta();

    
    //Obtener formato de fecha para los mensajes
    public static SimpleDateFormat getDfMessage()
    {
        return new SimpleDateFormat("HH:mm:ss");
    }
    
    
    //Make a node able to drag the main Window
    public static void makeDraggable(Stage stage, Node node)
    {
        
        Utils.stage = stage;
        node.setOnMouseEntered((event) -> {
            node.setCursor(Cursor.MOVE);
        });
        
        
        node.setOnMousePressed((event) -> {
            stage.setOpacity(0.9);
            node.setCursor(Cursor.CLOSED_HAND);
            DRAG_DELTA.x = stage.getX() - event.getScreenX();
            DRAG_DELTA.y = stage.getY() - event.getScreenY();
        });
        
        
        node.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() + DRAG_DELTA.x);
            stage.setY(event.getScreenY() + DRAG_DELTA.y);
                      
            
            //Maximize and unMaximize? when the window is dragged at the top of the Screen
            if(Objects.equals(stage.getTitle(), "ClientGUI"))
            {
                if(event.getScreenY() < 20 && canMaximize && !isMaximized)
                {
                    canMaximize = false;
                    Utils.maximize();
                }
                else if((!(event.getScreenY() < 20) && !canMaximize) || (!(event.getScreenY() < 20) && isMaximized))
                {
                    canMaximize = true;
                    Utils.maximize();
                }
            }
            
            
        });
        
        node.setOnMouseReleased((event) -> {
            node.setCursor(Cursor.MOVE);
            stage.setOpacity(1);
            
            if(event.getScreenY() < 20 && !canMaximize && isMaximized)
            {
                ChatClient.getPrimaryStage().setX(0);
                ChatClient.getPrimaryStage().setY(0);
            }
//            stage.setX(event.getScreenX() + dragDelta.x);
//            stage.setY(event.getScreenY() + dragDelta.y);

        });
        
        node.setOnMouseClicked((event) -> {
            if(event.getClickCount() == 2)
            {
                Utils.maximize();
            }
        });
        
    }
    
    //NOT USE I THINK
    public static void refresh() {
        
        double x = Utils.stage.getHeight();
        Utils.stage.setHeight(x + 60);
        
    }
    
    
    //Maximize and not maximize the stage (Window)
    public static void maximize()
    {
        
        if(!isMaximized)
        {
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();  
            
            //Move the window to the left-top
            ChatClient.getPrimaryStage().setX(0);
            ChatClient.getPrimaryStage().setY(0);
            ChatClient.getPrimaryStage().setWidth(visualBounds.getWidth());
            ChatClient.getPrimaryStage().setHeight(visualBounds.getHeight());
            Utils.isMaximized = true;
        }
        else
        {
            
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            
            //Move the window a little to the center of the screen
            ChatClient.getPrimaryStage().setX(250);
            ChatClient.getPrimaryStage().setY(200);
            ChatClient.getPrimaryStage().setWidth(visualBounds.getWidth()/1.7);
            ChatClient.getPrimaryStage().setHeight(visualBounds.getHeight()/1.7);
            Utils.isMaximized = false;
        }
        
        //Move the right pane to the correct position when maximize or not maximize
        if(ClientGUIController.showRightPane)
        {
            ClientGUIController.getInstance().getRightPane().setTranslateX(ChatClient.getPrimaryStage().getWidth() -  ClientGUIController.getInstance().getRightPane().getWidth());
        }
        else
        {
             ClientGUIController.getInstance().getRightPane().setTranslateX(ChatClient.getPrimaryStage().getWidth());
        }
       
       
    }
    
    
    
    
   
}
