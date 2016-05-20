/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import es.chatclient.views.Decorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Practicas01
 */
public class ChatClient extends Application {

    private Scene scene;
    private BorderPane root;

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
        scene.getStylesheets().add(getClass().getResource("/es/chatclient/styles/styles.css").toExternalForm());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setPrimaryStage(primaryStage);
        root = FXMLLoader.load(getClass().getResource("/es/chatclient/views/clientGUI.fxml"));

        Decorator decoratorRoot = new Decorator(primaryStage, root);

        scene = new Scene(decoratorRoot);
        loadStyles();
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
