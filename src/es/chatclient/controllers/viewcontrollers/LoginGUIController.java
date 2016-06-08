
package es.chatclient.controllers.viewcontrollers;

import chatclient.ChatClient;
import es.chatclient.utils.Utils;
import es.chatclient.views.Decorator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;


/**
 *
 * @author Adrián Fernández Cano
 */
public class LoginGUIController implements Initializable{
    
    
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Pane topPane;
    
    @FXML
    private Button buttonClose;
    
    //Instancia de la clase (Singleton)
    private static LoginGUIController instance = null;
    private final static Lock INSTANCIATION_LOCK = new ReentrantLock();

    
    //Constructor por defecto
    private LoginGUIController() {
        
        
    }

    //Singleton para la instancia
    public static LoginGUIController getInstance() {

        if (instance == null) {
            INSTANCIATION_LOCK.lock();

            try {
                if (instance == null) //Comprobamos que no se haya inicializado mientras se esperaba al cerrojo
                {
                    instance = new LoginGUIController();
                }
            } finally {
                INSTANCIATION_LOCK.unlock();
            }
        }
        return instance;

    }

    
    
    private void init() 
    {
        
        Utils.makeDraggable(ChatClient.getPrimaryStage(), topPane);
        
        buttonClose.setOnAction((event) -> {

            //Comprobar que es primaryStage para cerrar toda la app
            if (ChatClient.getPrimaryStage().getTitle().equals(ChatClient.getPrimaryStage().getTitle())) {
                //Transición de cierre
//                ScaleTransition scaleTransitionClose = ScaleTransitionBuilder.create()
//                        .node(ChatClient.getPrimaryStage().getScene().getRoot().getParent())
//                        .duration(Duration.seconds(0.3))
//                        .fromX(1)
//                        .fromY(1)
//                        .toX(0)
//                        .toY(0)
//                        .build();
//
//                //Cerrar cuando termine la animación
//                scaleTransitionClose.setOnFinished((e) -> {
//
//                    Platform.exit(); //Cerrar toda la aplicación
//
//                });

                //Iniciar transicion de cierre
                //scaleTransitionClose.play();
                
                
                Platform.exit(); //Cerrar toda la aplicación

            } else {
                ChatClient.getPrimaryStage().hide();
            }

        });
        
        
        //Login button action
        loginButton.setOnAction((Event) -> {
            
            //COMPROBAR TAL TAL Y USER LOGIN
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/chatclient/views/clientGUI.fxml"));
            
            //Send the GuiController to the logicController
            loader.setController(ClientGUIController.getInstance());
            
            AnchorPane root = new AnchorPane();
            
            try {
                 root = loader.load();
            }
            catch (IOException ex) {
                System.err.println("Error al cargar clientGUI.fxml en loginGUIController.java");
            }
            
            Decorator decoratorRoot = new Decorator(ChatClient.getPrimaryStage(), root);

            
            Scene clientGUIScene = new Scene(decoratorRoot);
            
            //Load CSS 
            clientGUIScene.getStylesheets().add(getClass().getResource("/es/chatclient/styles/cssgenerated/styles.css").toExternalForm());
            
            
            
            clientGUIScene.setFill(Color.TRANSPARENT);
            
            System.out.println("ROOT -> " + clientGUIScene.heightProperty().doubleValue());
            
            
//            ChatClient.getPrimaryStage().hide();
//            ChatClient.getPrimaryStage().setWidth(1000);
//            ChatClient.getPrimaryStage().setHeight(500);
            
            //Change de Stage title
            ChatClient.getPrimaryStage().setTitle("ClientGUI");

            //Set the new Scene
            ChatClient.getPrimaryStage().setScene(clientGUIScene);
            
            Utils.maximize();
            ChatClient.getPrimaryStage().show();
            
            //Bind Width
            root.minWidthProperty().bind(clientGUIScene.widthProperty().subtract(1));
            root.maxWidthProperty().bind(clientGUIScene.widthProperty().subtract(1));
            root.prefWidthProperty().bind(clientGUIScene.widthProperty().subtract(1));

            //Bind Height
            root.minHeightProperty().bind(clientGUIScene.heightProperty().subtract(1));
            root.maxHeightProperty().bind(clientGUIScene.heightProperty().subtract(1));
            root.prefHeightProperty().bind(clientGUIScene.heightProperty().subtract(1));
            
        });
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }
    
    
    
    
}
