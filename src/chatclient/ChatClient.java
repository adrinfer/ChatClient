/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import es.chatclient.controllers.viewcontrollers.LoginGUIController;
import es.chatclient.logic.Controller;
import es.chatclient.resources.Images;
import es.chatclient.server.messages.requests.RequestMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Practicas01
 */
public class ChatClient extends Application {

    private Scene scene;
    private BorderPane root;
    private FXMLLoader loader;
    
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    private void setBindings() {
        //Width
        root.minWidthProperty().bind(scene.widthProperty().subtract(1));
        root.maxWidthProperty().bind(scene.widthProperty().subtract(1));
        root.prefWidthProperty().bind(scene.widthProperty().subtract(1));

        //Height
        root.minHeightProperty().bind(scene.heightProperty().subtract(1));
        root.maxHeightProperty().bind(scene.heightProperty().subtract(1));
        root.prefHeightProperty().bind(scene.heightProperty().subtract(1));
    }

    //Cargar css utilizados - Debe ser llamado despues de crear la escena.
    private void loadStyles() {
        
        scene.getStylesheets().add(getClass().getResource("/es/chatclient/styles/cssgenerated/styles.css").toExternalForm());
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setPrimaryStage(primaryStage);
        primaryStage.setTitle("LoginStage");
        primaryStage.getIcons().add(Images.getImage(Images.APP_ICON));
        
        
        
//        loader = new FXMLLoader(getClass().getResource("/es/chatclient/views/clientGUI.fxml"));
//        loader.setController(ClientGUIController.getInstance());
//        root = loader.load();
        
        loader = new FXMLLoader(getClass().getResource("/es/chatclient/views/loginGUI.fxml"));
        
        //The login controller instance initialize the Controller too, and the
        //Controller initialize the gui controller.
        LoginGUIController loginController = LoginGUIController.getInstance();
        
        loader.setController(loginController);
        loginController.setThisStage(primaryStage);
        
        root = loader.load();
        
        //On close stage request.
        //The controller are initialized in the loader.setController
        primaryStage.setOnCloseRequest((event) -> {
            try 
            {
                //Send to the server the logout message
                Controller.getInstance().getOutputStream().writeUTF(String.valueOf(RequestMessage.LOGOUT));
                Controller.getInstance().getOutputStream().flush();
                Controller.getInstance().closeConexion();
            }
            catch (IOException ex) 
            {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        });
        
          
        //Decorator decoratorRoot = new Decorator(primaryStage, root);

        scene = new Scene(root);
        
        loadStyles();
        
        
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        
        
        Pane panel = new Pane();
        
        panel.getStyleClass().add("panel");
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
        
        setBindings();
               
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
