

package es.chatclient.views;

/**
 *
 * @author Adrián Fernández Cano
 */
import chatclient.ChatClient;
import es.chatclient.controllers.viewcontrollers.ClientGUIController;
import es.chatclient.resources.Images;
import es.chatclient.utils.Utils;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author adrinfer
 */
public class Decorator extends AnchorPane {

    //Atributos
    private final Stage stage;
    private double oldWidth;
    private double oldHeight;
    private double oldX;
    private double oldY;

    private Rectangle2D visualBounds;
  
    
    //Buttons
    private final Button btnMax;
    private final Button btnClose;
    private final Button btnMinimize;
    private Button btnRes;
    
    private final ClientGUIController guiController = ClientGUIController.getInstance();

    //Constructor
    public Decorator(Stage stage, Node node) {
            
        super();
        //Pane leftPane = ClientGUIController.getInstance().getLeftPane();
        this.stage = stage;
        this.setPadding(new Insets(0, 0, 0, 0));
        this.visualBounds = Screen.getPrimary().getVisualBounds();

        //Ajustar nodo recibido a las esquinas
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);

        
        //Boón para maximizar la ventana
        btnMinimize = buildButton("Min", Images.getImage(Images.MINIMIZE_ICON), Images.getImage(Images.MINIMIZE_ICON_HOVER));
        btnMinimize.setOnAction((event) -> {
            
            
            stage.setIconified(true);
            
        });
        
        AnchorPane.setRightAnchor(btnMinimize, 115.0);
        AnchorPane.setTopAnchor(btnMinimize, 9.1);
        
        //Boón para maximizar la ventana
        btnMax = buildButton("Max", Images.getImage(Images.MAX_ICON), Images.getImage(Images.MAX_ICON_HOVER));
        btnMax.setOnAction((event) -> {
            
            
            Utils.maximize();
            
        });

        //Posición botón maximizar
        AnchorPane.setRightAnchor(btnMax, 70.0);
        AnchorPane.setTopAnchor(btnMax, 9.1);

        
        
        
        
        //Botón para cerrar la ventana
        btnClose = buildButton("Close", Images.getImage(Images.CLOSE_ICON), Images.getImage(Images.CLOSE_ICON_HOVER));

        btnClose.setOnAction((event) -> {

            //Comprobar que es primaryStage para cerrar toda la app
            if (stage.getTitle().equals(ChatClient.getPrimaryStage().getTitle())) {
                //Transición de cierre
                ScaleTransition scaleTransitionClose = ScaleTransitionBuilder.create()
                        .node(this)
                        .duration(Duration.seconds(0.3))
                        .fromX(1)
                        .fromY(1)
                        .toX(0)
                        .toY(0)
                        .build();

                //Cerrar cuando termine la animación
                scaleTransitionClose.setOnFinished((e) -> {

                    
                    stage.close();
                    System.exit(0);
                    //Platform.exit(); //Cerrar toda la aplicación

                });

                //Iniciar transicion de cierre
                scaleTransitionClose.play();

            } else {
                this.stage.hide();
            }

        });

        //Posición boton close
        AnchorPane.setRightAnchor(btnClose, 25.0);
        AnchorPane.setTopAnchor(btnClose, 9.1);

        //Botón para redimensionar la ventana
        btnRes = buildButton("Resize", Images.getImage(Images.RESIZE_ICON), Images.getImage(Images.RESIZE_ICON_HOVER));

        btnRes.setOnMousePressed((event) -> {
            btnRes.setGraphic(new ImageView(Images.getImage(Images.RESIZE_ICON_PRESSED)));
            this.oldX = event.getSceneX();
            this.oldY = event.getSceneY();
            this.oldWidth = stage.getWidth();
            this.oldHeight = stage.getHeight();

            
        });

        
        
        btnRes.setOnMouseReleased((event) -> {
            btnRes.setGraphic(new ImageView(Images.getImage(Images.RESIZE_ICON)));
        });

        
        btnRes.setOnMouseDragged((event) -> {
            btnRes.setGraphic(new ImageView(Images.getImage(Images.RESIZE_ICON_PRESSED)));
            double newWidth = this.oldWidth + (event.getSceneX() - this.oldX);
            double newHeight = this.oldHeight + (event.getSceneY() - this.oldY);

            if (newWidth >= 600) {
                stage.setWidth(newWidth);
            }

            if (newHeight >= 430) {
                stage.setHeight(newHeight);
            }
            
            
            //Move the rightPane when resize the window
            if(ClientGUIController.showRightPane)
            {
                guiController.getRightPane().setTranslateX(ChatClient.getPrimaryStage().getWidth() - guiController.getRightPane().getWidth());
            }
            else
            {
                guiController.getRightPane().setTranslateX(ChatClient.getPrimaryStage().getWidth());
            }
            
            //leftPane.translateXProperty().bind(leftPane.translateXProperty().add(leftPane.widthProperty().getValue()));
            //leftPane.setTranslateX(0);
            
        });

        btnRes.setOnMouseEntered((event) -> {
            btnRes.setCursor(Cursor.SE_RESIZE);
            btnRes.setGraphic(new ImageView(Images.getImage(Images.RESIZE_ICON_HOVER)));
        });

        //Posición botón de redimensionar
        AnchorPane.setRightAnchor(btnRes, 1.5);
        AnchorPane.setBottomAnchor(btnRes, 1.5);

        this.getChildren().addAll(node, btnMinimize, btnMax, btnClose, btnRes);

    }

    //Construir botón con una imagen y imagen de hover (pasar por encima)
    private Button buildButton(String name, Image image, Image imageHover) {

        Button btn = new Button(name, new ImageView(image));
        btn.setOnMouseEntered((event) -> {
            btn.setCursor(Cursor.HAND);
            btn.setGraphic(new ImageView(imageHover));

        });

        btn.setOnMouseExited((event) -> {

            btn.setGraphic(new ImageView(image));
        });

        btn.getStyleClass().clear();
        btn.setMinSize(32, 32);
        btn.setMaxSize(32, 32);

        return btn;
    }

    public void setResizable(boolean value) {
        btnRes.setVisible(value);
    }

    public void setMaxizable(boolean value) {
        btnMax.setVisible(value);
    }


    
}
