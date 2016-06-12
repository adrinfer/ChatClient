/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.controllers.viewcontrollers;

import com.google.gson.Gson;
import es.chatclient.logic.Controller;
import es.chatclient.server.messages.requests.RequestMessage;
import es.chatclient.utils.Status;
import es.chatclient.utils.Utils;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Adrián Fernández Cano
 */
public class RegisterGUIController implements Initializable {

    
    private final Stage stage;
    
    @FXML
    private Pane topPane;
    
    @FXML
    private Button buttonClose;
    
    @FXML
    private TextField txtName;
    
    @FXML
    private TextField txtNick;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private Button btnRegister;
    
    @FXML
    private Button btnReset;
    
    @FXML
    private HBox hBoxEmail;
    
    @FXML
    private Label lblErrorInfo;
   
    
    //Gson instance
    private final Gson gson;
    
    private final Controller logicController;
    
    public RegisterGUIController(Stage stage)
    {
        this.stage = stage;
        this.logicController = Controller.getInstance();
        this.gson = logicController.getGson();
    }
    
    
    //Init method of Register GUI
    private void init()
    {
        
        Utils.makeDraggable(this.stage, topPane);
        
        
        initizaliveValidators();
        
        
        //Action of close button
        buttonClose.setOnAction((MouseEvent) -> stage.close());
        
        
        //Action of register button
        btnRegister.setOnAction((MouseEvent) -> {
        
            RequestMessage registerRequest = new RequestMessage(txtName.getText(), txtNick.getText(), 
                                                            txtPassword.getText(), txtEmail.getText(),
                                                              RequestMessage.REGISTER);
            
            
            if(validateFields())
            {
                
                
                        
                Socket socket = null;
                try
                {
                    socket = new Socket(Controller.SERVER_ADDRESS, Controller.SERVER_PORT);
                    logicController.setSocket(socket);
                    logicController.getOutputStream().writeUTF(gson.toJson(registerRequest));
                    
                    
                    //Wait response
                    int response = logicController.getInputStream().readInt();
                    
                    //TODO comprobar respuesta (registro OK, BAD, NICK EN USO)
                    
                    System.out.println("RESPUESTA REGISTRO -> " + response);
                    processResponse(response);
                    
                    logicController.closeConexion();
                    
                }
                catch (IOException ex)
                {
                    Logger.getLogger(RegisterGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
                
                
                
           
                    
            
  
        
        });


        //Action of reset button
        btnReset.setOnAction((MouseEvent) -> {
        
            txtName.clear();
            txtNick.clear();
            txtPassword.clear();
            txtEmail.clear();
        
            //Reset email field
            btnRegister.setDisable(false);
            hBoxEmail.setStyle("-fx-background-color: transparent; -fx-spacing: 10; -fx-padding: 0 25 0 0;");
            lblErrorInfo.setText("");
            
        });
        
    }
    
    
    //Validate form fields
    private boolean validateFields()
    {
        
      boolean resultado = false;
        
        if( !Objects.equals(txtName.getText(), "") 
                && !Objects.equals(txtNick.getText(), "")
                && !Objects.equals(txtPassword.getText(), "")
                && validatePassword()
                && !Objects.equals(txtEmail.getText(), "")
                && validateEmail())
        {
            resultado = true;
        }
        
        return resultado;
    }
    
    
    private boolean validateEmail()
    {
        String patternEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        
        Pattern pattern = Pattern.compile(patternEmail);
        Matcher matcher = pattern.matcher(txtEmail.getText());

        return matcher.matches();
    }
    
    private boolean validatePassword()
    {
        return true;
    }
    
    
    private void initizaliveValidators()
    {
        
        txtEmail.setOnKeyReleased((event) -> {
            
            if(validateEmail())
            {
                btnRegister.setDisable(false);
                hBoxEmail.setStyle("-fx-background-color: transparent; -fx-spacing: 10; -fx-padding: 0 25 0 0;");
                lblErrorInfo.setText("");
            }
            else
            {
                btnRegister.setDisable(true);
                hBoxEmail.setStyle("-fx-background-color: red; -fx-spacing: 10; -fx-padding: 0 25 0 0;");
                lblErrorInfo.setText("Email incorrecto");
            }
            
        });
        
    }
    
    //Process the server response 
    private void processResponse(int response)
    {
        
        if(response == Status.REGISTER_OK)
        {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Registro realizado");
            a.show();
        }
        
        if(response == Status.USER_NICK_USED)
        {
            lblErrorInfo.setText("Nick en uso");
        }
        
        if(response == Status.EMAIL_ALREADY_USED)
        {
            lblErrorInfo.setText("Email en uso");
        }
        
        if(response == Status.USER_EMAIL_USED)
        {
            lblErrorInfo.setText("Nick y email en uso.");
        }
        
        
        if(response == Status.REGISTER_BAD)
        {
            lblErrorInfo.setText("Error inesperado");
        }
        
        
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        
    }
    
}
