

package es.chatclient.controllers.viewcontrollers;

import chatclient.ChatClient;
import com.google.gson.Gson;
import es.chatclient.entities.UserBox;
import es.chatclient.entities.UserMessage;
import es.chatclient.interfaces.Box;
import es.chatclient.logic.Controller;
import es.chatclient.server.messages.ConverData;
import es.chatclient.server.messages.ConversDataMessage;
import es.chatclient.server.messages.Message;
import es.chatclient.server.messages.requests.RequestMessage;
import es.chatclient.utils.Status;
import es.chatclient.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.animation.RotateTransitionBuilder;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author Adrián Fernández Cano
 */
public class ClientGUIController implements Initializable {

    
    @FXML
    private AnchorPane mainAnchorPane;
    
    @FXML
    private BorderPane borderPane;

    @FXML
    private BorderPane borderPaneCenter;
    
    @FXML
    private Pane topPane;
    
    @FXML
    private Pane rightPane;
    
    @FXML
    private ImageView imgViewLogoTopPane;
    
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
    private StackPane stackPaneCenter;
    
    @FXML
    private HBox hBoxTextField;
    
    @FXML
    private TextField textFieldTextToSend;
    
    @FXML
    private Button botonPrueba;
    
    @FXML
    private ImageView imgViewBg;
    
    @FXML
    private ListView listCenter;
    
    @FXML
    private Button boton;

    @FXML
    private Pane centralPane;

    @FXML
    private HBox hbox;

    final BorderPane loadingBorderPane;
    final RotateTransition loadingRotateTransition;
    private long startLoading;
    
    private TranslateTransition translateTransition;

    public static Boolean showRightPane = false;
    private Boolean unlock = true;
    
    
    private Callable<Boolean> loadDataThread;
    
    private ObservableList<UserMessage> currentMessages;
    
    private final Controller logicController;
    
    //Instancia de la clase (Singleton)
    private static ClientGUIController instance = null;
    private final static Lock INSTANCIATION_LOCK = new ReentrantLock();

    //Constructor por defecto
    private ClientGUIController() {
        
        this.logicController = Controller.getInstance(this); //Send this class 

        this.currentMessages = FXCollections.observableArrayList();
        
        
        loadingBorderPane= new BorderPane();
        
        Rectangle rect = new Rectangle(100, 100, Color.rgb(14, 139, 224, 0.9));
            rect.setArcHeight(20);
            rect.setArcWidth(20);
            
        loadingBorderPane.setCenter(rect);
        loadingRotateTransition = RotateTransitionBuilder.create()
                    .node(rect)
                    .duration(Duration.seconds(1))
                    .fromAngle(0)
                    .toAngle(720)
                    .cycleCount(Timeline.INDEFINITE)
                    .autoReverse(true)
                    .build();
        
        
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
        
        //Loading pane
        
        
        borderPane.minWidthProperty().bind(mainAnchorPane.minWidthProperty());
        borderPane.maxWidthProperty().bind(mainAnchorPane.maxWidthProperty());
        borderPane.prefWidthProperty().bind(mainAnchorPane.prefWidthProperty());
        
        borderPane.minHeightProperty().bind(mainAnchorPane.minHeightProperty());
        borderPane.maxHeightProperty().bind(mainAnchorPane.maxHeightProperty());
        borderPane.prefHeightProperty().bind(mainAnchorPane.prefHeightProperty());
        
        //LeftPane width
        leftPane.minWidthProperty().bind(borderPane.minWidthProperty().multiply(0.25));
        leftPane.maxWidthProperty().bind(borderPane.maxWidthProperty().multiply(0.25));
        leftPane.prefWidthProperty().bind(borderPane.prefWidthProperty().multiply(0.25));
        
        //TopPane final Height
        topPane.minHeightProperty().set(50);
        topPane.maxHeightProperty().set(50);
        topPane.prefHeightProperty().set(50);
        
        //imgViewLogoTopPane.fitHeightProperty().bind(topPane.heightProperty().multiply(2.8));
        
        
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
    
        
        
        borderPaneCenter.minWidthProperty().bind(borderPane.minWidthProperty().subtract(leftPane.minWidthProperty()));
        borderPaneCenter.maxWidthProperty().bind(borderPane.maxWidthProperty().subtract(leftPane.maxWidthProperty()));
        borderPaneCenter.prefWidthProperty().bind(borderPane.prefWidthProperty().subtract(leftPane.prefWidthProperty()));
        
        borderPaneCenter.minHeightProperty().bind(borderPane.minHeightProperty()
                                            .subtract(topPane.minHeightProperty())
                                            .subtract(bottomPane.minHeightProperty()));
        
        borderPaneCenter.maxHeightProperty().bind(borderPane.maxHeightProperty()
                                            .subtract(topPane.minHeightProperty())
                                            .subtract(bottomPane.maxHeightProperty()));
                                            
        borderPaneCenter.prefHeightProperty().bind(borderPane.prefHeightProperty()
                                             .subtract(topPane.minHeightProperty())
                                             .subtract(bottomPane.prefHeightProperty()));

        
        System.out.println("borderPaneCenter " + borderPaneCenter.minWidthProperty().doubleValue());
        
        stackPaneCenter.minWidthProperty().bind(borderPaneCenter.minWidthProperty());
        stackPaneCenter.maxWidthProperty().bind(borderPaneCenter.maxWidthProperty());
        stackPaneCenter.prefWidthProperty().bind(borderPaneCenter.prefWidthProperty());
        
        userTitleHBox.minHeightProperty().set(50);
        userTitleHBox.maxHeightProperty().set(50);
        userTitleHBox.prefHeightProperty().set(50);
        
        hBoxTextField.minWidthProperty().bind(borderPaneCenter.minWidthProperty());
        hBoxTextField.maxWidthProperty().bind(borderPaneCenter.maxWidthProperty());
        hBoxTextField.prefWidthProperty().bind(borderPaneCenter.prefWidthProperty());
        
        hBoxTextField.minHeightProperty().set(40);
        hBoxTextField.maxHeightProperty().set(40);
        hBoxTextField.prefHeightProperty().set(40);
        
        textFieldTextToSend.minHeightProperty().bind(hBoxTextField.minHeightProperty());
        textFieldTextToSend.maxHeightProperty().bind(hBoxTextField.maxHeightProperty());
        textFieldTextToSend.prefHeightProperty().bind(hBoxTextField.prefHeightProperty());
        
        textFieldTextToSend.minWidthProperty().bind(hBoxTextField.minWidthProperty());
        textFieldTextToSend.maxWidthProperty().bind(hBoxTextField.maxWidthProperty());
        textFieldTextToSend.prefWidthProperty().bind(hBoxTextField.prefWidthProperty());
        
//        stackPaneCenter.minHeightProperty().bind(borderPaneCenter.minHeightProperty()
//                                           .subtract(userTitleHBox.minHeightProperty())
//                                           .subtract(hBoxTextField.minWidthProperty()));
//        
//        stackPaneCenter.maxHeightProperty().bind(borderPaneCenter.maxHeightProperty()
//                                           .subtract(userTitleHBox.maxHeightProperty())
//                                           .subtract(hBoxTextField.maxWidthProperty()));
//        
//        stackPaneCenter.prefHeightProperty().bind(borderPaneCenter.prefHeightProperty()
//                                            .subtract(userTitleHBox.prefHeightProperty())
//                                            .subtract(hBoxTextField.prefWidthProperty()));
        
        
        
//        imgViewBg.fitHeightProperty().bind(stackPaneCenter.heightProperty());
//        imgViewBg.fitWidthProperty().bind(stackPaneCenter.widthProperty());
//        
        
        System.out.println("STACK " + stackPaneCenter.heightProperty().doubleValue());        

        listCenter.minWidthProperty().bind(stackPaneCenter.minWidthProperty());
        listCenter.maxWidthProperty().bind(stackPaneCenter.maxWidthProperty());
        listCenter.prefWidthProperty().bind(stackPaneCenter.prefWidthProperty());
        
        
        
        listCenter.minHeightProperty().bind(stackPaneCenter.minHeightProperty());
        listCenter.maxHeightProperty().bind(stackPaneCenter.maxHeightProperty());
        listCenter.prefHeightProperty().bind(stackPaneCenter.prefHeightProperty());
        
        //StackPane.setAlignment(listCenter, Pos.BOTTOM_CENTER);
        
        

        //rightPane height
        rightPane.minHeightProperty().bind(mainAnchorPane.minHeightProperty().subtract(topPane.minHeightProperty()).subtract(bottomPane.minHeightProperty()).add(10));
        rightPane.maxHeightProperty().bind(mainAnchorPane.maxHeightProperty().subtract(topPane.maxHeightProperty()).subtract(bottomPane.maxHeightProperty()).add(10));
        rightPane.prefHeightProperty().bind(mainAnchorPane.prefHeightProperty().subtract(topPane.prefHeightProperty()).subtract(bottomPane.prefHeightProperty()).add(10));
        
        
 
    }
    
    public ListView getList()
    {
        return listCenter;
    }
    
    public Pane getRightPane()
    {
        return rightPane;
    }
    
    private void initializeListUserBox()
    {
        
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
        
        //AQUI HABRA QUE USAR JPA, CONTROLADOR PRINCIPAL ETC
//        List<ConverData> converList = new ArrayList();
//        List<Message> msgList = new ArrayList();
//        
//        for(int x = 0; x < 10; x++)
//        {
//            msgList.add(new Message("1", "2" , "Contenido del mensaje", Utils.getDfMessage().format(new Date()), "29", "11"));
//        }
//        
//        for(int x = 0; x < 3; x++)
//        {
//            converList.add(new ConverData("11", "Nombre conversacion " + x + "", msgList));
//        }
//        
//        ConversDataMessage conversData = new ConversDataMessage(converList);
        
        
        
        //Thread that manage the initial load data
        loadDataThread = (Callable) new Callable() {
            
            
            @Override
            public Boolean call() throws Exception {
                
                showLoading();
                RequestMessage dataRequest = new RequestMessage(logicController.getUserNick(), RequestMessage.GET_DATA);
                
                String dataRequestJson = logicController.getGson().toJson(dataRequest);
                
                try {
                    
                    //Send the GET_DATA request to the server
                    logicController.getOutputStream().writeUTF(dataRequestJson);
                    
                    //Wait the server response
                    String jsonResponse = logicController.getInputStream().readUTF();
                    System.out.println("RESPUESTA DEL FUTURO -> " + jsonResponse);
                    
                    if(!Objects.equals(jsonResponse, String.valueOf(Status.ERROR))) 
                    {
                        ConversDataMessage conversData = logicController.getGson().fromJson(jsonResponse, ConversDataMessage.class);
                        ObservableList<UserBox> items = FXCollections.observableArrayList();
                        for(ConverData converData: conversData.getConverDataArray())
                        {
                            
                             

                            List<UserMessage> userMsgList = new ArrayList();
                            
                            for(Message msg: converData.getConverMessages())
                            {
                                userMsgList.add(new UserMessage(msg.getUserNick(), msg.getMsgText(), msg.getMsgDate()));
                               
                            }
                            
                            UserBox userBox = new UserBox(converData.getConverID(), converData.getConverName(), userMsgList);
                            items.add(userBox);
   
                            
                        }
                        //Out of loop
                        listUsersBox.setItems(items);
                        
                    }
                    else // ¿ERROR?
                    {
                        
                    }
                            
             
                    stopLoading();
                }
                catch (IOException ex) {
                    Logger.getLogger(ClientGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                return Boolean.TRUE;
            }
        };
        
        //Thread that run the thread that manage de initial data
        //Is necessary for wait to the future response of the server
        Runnable futureThread = () -> {
            
            //Run the thread that realice the loading of the data
            Future futureResult = logicController.sumbitThread(loadDataThread);
            
            try {
                System.err.println("ESPERANDO FUTURO");
                System.err.println("FUTUROOOOOOOOO ->> " + futureResult.get().toString());
            }
            catch (InterruptedException ex) {
                Logger.getLogger(ClientGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (ExecutionException ex) {
                Logger.getLogger(ClientGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
               
        try
        {
            logicController.sumbitThread(futureThread);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
       
        
        
        
        
        
        
        
//        String j = "";
//        try
//        {
//             j = logicController.getGson().toJson(conversData);
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        
//        //System.out.println("GSON CONVERSACIONES ----> \n " + j);
//        
//        ConversDataMessage data = null;
//        try
//        {
//             data = logicController.getGson().fromJson(j, ConversDataMessage.class);
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
        
//        System.out.println("OBJETO DATA -> ");
//        for(ConverData data2: data.getConverDataArray())
//        {
//            System.out.println("CONVER ID: " + data2.getConverID());
//            System.out.println("CONVER NAME: " + data2.getConverName());
//            
//            for(Message m: data2.getConverMessages())
//            {
//                System.out.println("MSG ID: " + m.getMsgID());
//                System.out.println("MSG CONVER ID: " + m.getConverId());
//                System.out.println("MSG DATE: " + m.getMsgDate());
//                System.out.println("MSG TEXT: " + m.getMsgText());
//            }
//        }
        
        
        
        
    }
    
    
    private void setNodesCss()
    {
        borderPane.getStyleClass().add("borderPane");
        topPane.getStyleClass().add("topPane");
        bottomPane.getStyleClass().add("topPane");
        leftPane.getStyleClass().add("leftPane");
        userTitleHBox.getStyleClass().add("userTitleHBox");
        stackPaneCenter.getStyleClass().add("stackPaneCenter");
        listCenter.getStyleClass().add("listCenter");
        textFieldTextToSend.getStyleClass().add("textField");
        listUsersBox.getStyleClass().add("listUserBox");
        rightPane.getStyleClass().add("rightPane");
        
        
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
        this.setNodesCss();
        //topPane and bottomPane draggables
        Utils.makeDraggable(ChatClient.getPrimaryStage(), this.topPane);
        Utils.makeDraggable(ChatClient.getPrimaryStage(), this.bottomPane);
        
        this.initializeListUserBox();
        
        Gson gson = new Gson();

//        hbox.minWidthProperty().bind(borderPane.minWidthProperty());
//        hbox.maxWidthProperty().bind(borderPane.maxWidthProperty());
//        hbox.prefWidthProperty().bind(borderPane.prefWidthProperty());

        
        
        

        
       textFieldTextToSend.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    logicController.addMessage(textFieldTextToSend.getText());
                    
                    Message outputMessage = new Message();
                    outputMessage.setMsgID("");
                    outputMessage.setUserNick(logicController.getUserNick());
                    outputMessage.setConverId(logicController.getActiveChat().getConverId());
                    outputMessage.setClientId(""); //El servidor lo completa usando el nick
                    outputMessage.setMsgDate(Utils.getDfMessage().format(new Date()));
                    outputMessage.setMsgType("0");
                    outputMessage.setMsgText(textFieldTextToSend.getText());
                    
                    String outputJson = logicController.getGson().toJson(outputMessage);
                    
                    try 
                    {
                        logicController.getOutputStream().writeUTF(outputJson);
                    }
                    catch (IOException ex) 
                    {
                        Logger.getLogger(ClientGUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("ENTER");
                }
            }
        });

        
       
        //The rightPane starts hidden
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        rightPane.setTranslateX(visualBounds.getMaxX());
       
        
        //Right pane animation
        translateTransition = TranslateTransitionBuilder.create()
                .duration(Duration.seconds(0.4))
                .node(rightPane)
                .cycleCount(1)
                .autoReverse(true)
                .build();
        
        translateTransition.setOnFinished((ActionEvent event) -> {
                    System.out.println("UNLOCK -> " + "" );
                    unlock = true;
        });
        
        
        botonPrueba.setOnAction((Event) -> {
        
            
            if(!showRightPane && unlock)
            {
                //rightPane.setTranslateX(visualBounds.getMaxX() - rightPane.getWidth());
                translateTransition.setFromX(ChatClient.getPrimaryStage().getWidth());
                translateTransition.setToX(ChatClient.getPrimaryStage().getWidth() - rightPane.getWidth());
                unlock = false;
                showRightPane = true;
                translateTransition.play();
                
                 
            }
            else
            {
                translateTransition.setFromX(ChatClient.getPrimaryStage().getWidth() - rightPane.getWidth());
                translateTransition.setToX(ChatClient.getPrimaryStage().getWidth());
                //rightPane.setTranslateX(visualBounds.getMaxX());
                showRightPane = false;
                unlock = false;
                translateTransition.play();
            }
            
            
        });
        
        
        
        
        
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
    
    public void showLoading()
    {
        showLoading(true);
    }
    
    private void showLoading(boolean show)
    {
        
        
        
        if(show)
        {


            
            loadingBorderPane.minWidthProperty().bind(mainAnchorPane.minWidthProperty());
            loadingBorderPane.maxWidthProperty().bind(mainAnchorPane.maxWidthProperty());
            loadingBorderPane.prefWidthProperty().bind(mainAnchorPane.prefWidthProperty());

            loadingBorderPane.minHeightProperty().bind(mainAnchorPane.minHeightProperty());
            loadingBorderPane.maxHeightProperty().bind(mainAnchorPane.maxHeightProperty());
            loadingBorderPane.prefHeightProperty().bind(mainAnchorPane.prefHeightProperty());

            mainAnchorPane.getChildren().add(loadingBorderPane);

            

            loadingBorderPane.getStyleClass().add("loadingBorderPane");
            loadingBorderPane.setVisible(true);

            loadingRotateTransition.play(); 
            
            //The animation duration is at least 2 seconds
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException ex) {
                Logger.getLogger(ClientGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else
        {
            loadingBorderPane.setVisible(false);
            loadingRotateTransition.stop();
        }
        
        
    }
    
    private void stopLoading()
    {
        showLoading(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();

    }
    
    
    
    
    public void changeConversation(Box conversation)
    {

        inflateMessages(conversation.getMessages());
   
    }
    
    private void inflateMessages(List<UserMessage> messageList)
    {
        
        listCenter.getItems().clear();
        
        ObservableList<BorderPane> messagePane = FXCollections.observableArrayList();
        
        for(UserMessage msgObj: messageList)
        {
            //listCenter.getItems().add(msgObj.getUserMessage());
            //messagePane.add(msgObj.getUserMessage());
            listCenter.getItems().add(msgObj.getUserMessage());
        }
        //listCenter.setItems(messagePane);
        
        
    }
    
    public void addMessageAndInflate(UserMessage newMessage)
    {
        
        listCenter.getItems().add(newMessage.getUserMessage());
        
    }




}
