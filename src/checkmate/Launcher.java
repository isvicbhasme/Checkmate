/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate;

import checkmate.design.Board;
import checkmate.util.ResourceManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author bhasme
 */
public class Launcher extends Application {

    public static ResourceManager resource;
    public static Board board;
    public static Stage primaryWindow;

    @Override
    public void start(Stage primaryStage) {
        resource = new ResourceManager();
        board = new Board();
        board.createPieces();
        int sceneWidth = resource.getIntConfig("Cell.length") * 8;
        int sceneHeight = resource.getIntConfig("Cell.length") * 10;
        Scene scene = new Scene(board, sceneWidth, sceneHeight);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryWindow = primaryStage;
    }
    
    public static void endGameWithMsg(String message) {
        Stage endGameDialog = new Stage();
        StackPane pane = new StackPane(new Text(message));
        Scene endScene = new Scene(pane, 200, 100);
        endGameDialog.setTitle("Checkmate - message");
        endGameDialog.initOwner(primaryWindow);
        endGameDialog.initModality(Modality.APPLICATION_MODAL);
        endGameDialog.setScene(endScene);
        endGameDialog.show();
        endGameDialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                primaryWindow.close();
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
