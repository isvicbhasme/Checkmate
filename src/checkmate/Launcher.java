/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate;

import checkmate.design.Board;
import checkmate.util.ResourceManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author bhasme
 */
public class Launcher extends Application {

    public static ResourceManager resource;
    public static Board board;

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
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
