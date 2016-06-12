
package es.chatclient.controllers.viewcontrollers;

import chatclient.ChatClient;
import com.google.gson.Gson;
import es.chatclient.logic.Controller;
import es.chatclient.resources.Images;
import es.chatclient.utils.Utils;
import es.chatclient.views.Decorator;
import es.chatclient.server.messages.requests.RequestMessage;
import es.chatclient.utils.Status;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;


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
    
    @FXML
    private Circle circleStatus;
    
    @FXML
    private TextField nickField;
    
    @FXML
    private PasswordField passField;
    
    @FXML
    private ImageView reloadButton;
    
    @FXML
    private Label labelRegistro;
    
    @FXML
    private Label lblError;
    
    
    private Stage registerStage;
    private Scene registerScene;
    private BorderPane registerRootPane;
    
    //Gson instance - get from the controller
    private final Gson gson;
    
    //logicController instance
    private final Controller logicController;
    
    //Instancia de la clase (Singleton)
    private static LoginGUIController instance = null;
    private final static Lock INSTANCIATION_LOCK = new ReentrantLock();

    
    //Constructor por defecto
    //Al llamar al Controller instance también se llama al guiController instance
    //Y se inicializan los dos al abrir el login.
    private LoginGUIController() {
        this.logicController = Controller.getInstance(ClientGUIController.getInstance());
        this.gson = logicController.getGson();
        
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

    
    //Initialize Login stage
    private void init() 
    {
        initializeRegisterStage();
        
        labelRegistro.getStyleClass().add("labelRegistro");
        
        //The register label action
        labelRegistro.setOnMousePressed((MouseEvent) -> openRegister());
        
        
        //Mouse enter of reload button
        RotateTransition rotateEntered = new RotateTransition(Duration.millis(500), reloadButton);
        rotateEntered.setByAngle(110);

        //Mouse exit of reload button
        RotateTransition rotateExited = new RotateTransition(Duration.millis(500), reloadButton);
        rotateExited.setFromAngle(110);
        rotateExited.setToAngle(0);       
        
        
        // RELOAD BUTTON LISTENERS
            reloadButton.setOnMousePressed((MouseEvent) -> {

                checkServerStatus(Controller.SERVER_ADDRESS, Controller.SERVER_PORT);

            });

            reloadButton.setOnMouseEntered((MouseEvent) -> {
                reloadButton.setScaleX(1.5);
                reloadButton.setScaleY(1.5);
                //reloadButton.setRotate(100);
                rotateEntered.play();
            });

            reloadButton.setOnMouseExited((MouseEvent) -> {
                reloadButton.setScaleX(1);
                reloadButton.setScaleY(1);
                reloadButton.setRotate(0);
                rotateExited.play();
            });
        
        
        //Check the initial server status
        checkServerStatus(Controller.SERVER_ADDRESS, Controller.SERVER_PORT);
        
        //Make the top pane able to move the window
        Utils.makeDraggable(ChatClient.getPrimaryStage(), topPane);
        
        //Button close click action
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
        
        
        
        
        //Login button click action - send a request type LOGIN
        loginButton.setOnAction((Event) -> {
            
            //Future response of the server
            int response = Status.LOGIN_BAD;

            try 
            {
                Socket socket = new Socket(Controller.SERVER_ADDRESS, Controller.SERVER_PORT);
                logicController.setSocket(socket);
                
                RequestMessage request = new RequestMessage(nickField.getText(), passField.getText(), RequestMessage.LOGIN);
                
                logicController.getOutputStream().writeUTF(gson.toJson(request));
                
                //Read the server response
                response = logicController.getInputStream().readInt();
                
                  
            }
            catch (IOException ex) 
            {
                Logger.getLogger(LoginGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }


            
            System.out.println("RESPUESTA -> " + response);
            if(response == Status.LOGIN_OK)
            {
                this.showGui();
            }
            
            if(response == Status.LOGIN_BAD)
            {
                lblError.setText("Login error"); 
            }
            
            
            
            
        });
        
        //Enter action in nick field
        nickField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    loginButton.fire();
                }
            }
        });
        
        //Enter action in password field
        passField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    loginButton.fire();
                }
            }
        });
        
    }
    
    

    private void checkServerStatus(String address, int port)
    {
        
        if(logicController.getServerStatus(address, port))
        {
            circleStatus.setFill(Color.GREEN);
            loginButton.setDisable(false);
            nickField.setDisable(false);
            passField.setDisable(false);
            reloadButton.setVisible(false);
            
        }
        else
        {
            circleStatus.setFill(Color.RED);
            //loginButton.setDisable(true); //DESCOMENTAR
            nickField.setDisable(true);
            passField.setDisable(true);
            reloadButton.setVisible(true);
            
        }
        
    }
    
    
    private void initializeRegisterStage()
    {
        this.registerStage = new Stage(StageStyle.UNDECORATED);
        
        registerStage.getIcons().add(Images.getImage(Images.APP_ICON));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/chatclient/views/registerGUI.fxml"));
        
        loader.setController(new RegisterGUIController(registerStage));
        
        this.registerRootPane = null;
        
        try 
        {
            this.registerRootPane = loader.load();
        }
        catch (IOException ex)
        {
            Logger.getLogger(LoginGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.registerScene = new Scene(registerRootPane);
        registerScene.getStylesheets().add(getClass().getResource("/es/chatclient/styles/cssgenerated/styles.css").toExternalForm());
        registerStage.setScene(registerScene);
    }
    
    public void openRegister()
    {
     

        registerStage.show();
        
    }
    
    
    private void showGui()
    {
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
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }
    
    
    
    
}
