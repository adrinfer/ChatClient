/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.controllers.viewcontrollers;

import com.google.gson.Gson;
import es.chatclient.entities.UserBox;
import es.chatclient.resources.Images;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


/**
 *
 * @author Practicas01
 */
public class ClientGUIController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Pane topPane;
    
    @FXML
    private Pane bottomPane;
    
    @FXML
    private HBox hBoxBottom;
    
    @FXML
    private Pane leftPane;
    
    @FXML
    private HBox userTitleHBox;
    
    @FXML
    private ListView listUsersBox;
    
    @FXML
    private Button boton;

    @FXML
    private Pane centralPane;

    @FXML
    private HBox hbox;

    private TranslateTransition translateTransition;


    //Instancia de la clase (Singleton)
    private static ClientGUIController instance = null;
    private final static Lock INSTANCIATION_LOCK = new ReentrantLock();

    //Constructor por defecto
    private ClientGUIController() {
        //logicController = Controller.getInstance();
        //perController = PersistenceController.getInstance();

        //Añadir al controlador el observador de esta clase
        //(Para atender cambios)
        //logicController.addObserver(this);
    }

    //Singleton para la instancia
    public static ClientGUIController getInstance() {

        if (instance == null) {
            INSTANCIATION_LOCK.lock();

            try {
                if (instance == null) //Comprobamos que no se haya inicializado mientras se esperaba al cerrojo
                {
                    instance = new ClientGUIController();
                }
            } finally {
                INSTANCIATION_LOCK.unlock();
            }
        }
        return instance;

    }

    
    
    //Initialize all bindings
    private void bindings()
    {
        
        //LeftPane width
        leftPane.minWidthProperty().bind(borderPane.minWidthProperty().multiply(0.25));
        leftPane.maxWidthProperty().bind(borderPane.maxWidthProperty().multiply(0.25));
        leftPane.prefWidthProperty().bind(borderPane.prefWidthProperty().multiply(0.25));
        
        //TopPane final Height
        topPane.minHeightProperty().set(50);
        topPane.maxHeightProperty().set(50);
        topPane.prefHeightProperty().set(50);
        
        //bottomPane final Height
        bottomPane.minHeightProperty().set(50);
        bottomPane.maxHeightProperty().set(50);
        bottomPane.prefHeightProperty().set(50);
        
        bottomPane.minWidthProperty().bind(borderPane.minWidthProperty());
        bottomPane.minWidthProperty().bind(borderPane.minWidthProperty());
        bottomPane.minWidthProperty().bind(borderPane.minWidthProperty());
        
        hBoxBottom.minWidthProperty().bind(bottomPane.minWidthProperty());
        hBoxBottom.maxWidthProperty().bind(bottomPane.maxWidthProperty());
        hBoxBottom.prefWidthProperty().bind(bottomPane.prefWidthProperty());
        
        hBoxBottom.minHeightProperty().bind(bottomPane.minHeightProperty());
        hBoxBottom.maxHeightProperty().bind(bottomPane.maxHeightProperty());
        hBoxBottom.prefHeightProperty().bind(bottomPane.prefHeightProperty());
        
        
        
        //LeftPane Height
        leftPane.minHeightProperty().bind(borderPane.minHeightProperty().subtract(topPane.minHeightProperty()).subtract(bottomPane.minHeightProperty()));
        leftPane.maxHeightProperty().bind(borderPane.maxHeightProperty().subtract(topPane.minHeightProperty()).subtract(bottomPane.maxHeightProperty()));
        leftPane.prefHeightProperty().bind(borderPane.prefHeightProperty().subtract(topPane.minHeightProperty()).subtract(bottomPane.prefHeightProperty()));


        //ListUsersBox Width - in leftPane
        listUsersBox.minWidthProperty().bind(leftPane.minWidthProperty());
        listUsersBox.maxWidthProperty().bind(leftPane.maxWidthProperty());
        listUsersBox.prefWidthProperty().bind(leftPane.prefWidthProperty());
        
        //ListUsersBox Height - in leftPane
        listUsersBox.minHeightProperty().bind(leftPane.minHeightProperty());
        listUsersBox.maxHeightProperty().bind(leftPane.maxHeightProperty());
        listUsersBox.prefHeightProperty().bind(leftPane.prefHeightProperty());
    

    }
    
    
    private void initializeListUserBox()
    {
        
        UserBox userBox = new UserBox();
               ObservableList<UserBox> items =FXCollections.observableArrayList();

        for(int x = 0; x< 30; x++)
        {
            items.add(userBox);
        }
        
        
        listUsersBox.setItems(items);
        
        
        
        listUsersBox.setCellFactory(new Callback<ListView<UserBox>, ListCell<UserBox>>() {

                @Override
                public ListCell<UserBox> call(ListView<UserBox> param) {
                    return new ListCell<UserBox>(){

                        @Override
                        protected void updateItem(UserBox item, boolean empty) {

                            super.updateItem(item, empty);
                            if(item != null){

                                setGraphic(item.getUserBox());

                            }
                        }

                    };
                }
            });
        
        
    }
    
    private void init() {
        //split.setDividerPosition(1, 0.9);
        
        Pane pane = new Pane();
        HBox.setHgrow(pane, Priority.ALWAYS);
        
        
        Button boton2 = new Button("Boton");
        HBox.setMargin(boton2, new Insets(0, 100, 0, 0));
        
        hBoxBottom.getChildren().addAll(pane, boton2);
        
        
        
        //Inicializar bindings 
        this.bindings();
        
        this.initializeListUserBox();
        
        Gson gson = new Gson();

//        hbox.minWidthProperty().bind(borderPane.minWidthProperty());
//        hbox.maxWidthProperty().bind(borderPane.maxWidthProperty());
//        hbox.prefWidthProperty().bind(borderPane.prefWidthProperty());

        borderPane.getStyleClass().add("borderPane");
        topPane.getStyleClass().add("topPane");
        bottomPane.getStyleClass().add("topPane");
        leftPane.getStyleClass().add("leftPane");
        userTitleHBox.getStyleClass().add("userTitleHBox");
        
        

        
       

        
        
        
        
        
        
        
       //leftPane.minWidthProperty().bind(hbox.minWidthProperty().multiply(0.2));
        //leftPane.maxWidthProperty().bind(hbox.maxWidthProperty().multiply(0.2));
        //leftPane.prefWidthProperty().bind(hbox.prefWidthProperty().multiply(0.2));

//        centralPane.minWidthProperty().bind(hbox.minWidthProperty().subtract(leftPane.translateXProperty().add(leftPane.minWidthProperty())));
//        centralPane.maxWidthProperty().bind(hbox.maxWidthProperty().subtract(leftPane.translateXProperty().add(leftPane.maxWidthProperty())));
//        centralPane.prefWidthProperty().bind(hbox.prefWidthProperty().subtract(leftPane.translateXProperty().add(leftPane.prefWidthProperty())));

        //centralPane.translateXProperty().bind(leftPane.translateXProperty());

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


//        translateTransition = TranslateTransitionBuilder.create()
//                .duration(Duration.seconds(1))
//                .node(leftPane)
//                .fromX(0)
//                .toX(0)
//                .cycleCount(Timeline.INDEFINITE)
//                .autoReverse(true)
//                .build();

//        boton.setOnAction(e -> {
//            System.out.println("leftX: " + leftPane.translateXProperty().getValue());
//            System.out.println("leftWidth: " + leftPane.widthProperty().getValue());
//            System.out.println("centralX: " + centralPane.translateXProperty().getValue());
//            System.out.println("\nborderWidth: " + borderPane.widthProperty().getValue());
//            System.out.println("centralWidth: " + centralPane.widthProperty().getValue());
//            System.out.println("RESTA: " + hbox.minWidthProperty().subtract(leftPane.translateXProperty().add(leftPane.minWidthProperty())).getValue());
//            if (bol) {
//                translateTransition.toXProperty().set(-leftPane.getWidth());
//                translateTransition.play();
//                bol = false;
//
//            } else {
//                translateTransition.stop();
//                bol = true;
//            }
//
//        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();

    }



}
