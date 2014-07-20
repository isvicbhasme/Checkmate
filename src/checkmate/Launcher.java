/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate;

import checkmate.design.Board;
import checkmate.manager.GamePlay;
import checkmate.util.PieceInfo;
import checkmate.util.ProjectInfo;
import checkmate.util.ResourceManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
        startGameDialog(primaryStage);
    }

    private void startGameDialog(Stage primaryStage) {
        Stage startDialog = new Stage();
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        Scene startScene = new Scene(box, 200, 150);
        ToggleGroup group = new ToggleGroup();
        RadioButton radio1 = new RadioButton("Double Player");
        radio1.setToggleGroup(group);
        radio1.setSelected(true);
        radio1.setUserData("Double");
        RadioButton radio2 = new RadioButton("Single Player - White");
        radio2.setToggleGroup(group);
        radio2.setUserData("SingleWhite");
        RadioButton radio3 = new RadioButton("Single Player - Black");
        radio3.setToggleGroup(group);
        radio3.setUserData("SingleBlack");
        Button submit = new Button("Start");
        box.getChildren().add(radio1);
        box.getChildren().add(radio2);
        box.getChildren().add(radio3);
        box.getChildren().add(submit);
        startDialog.setScene(startScene);
        startDialog.show();

        submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                initPlayType((String) group.getSelectedToggle().getUserData());
                createApplication(primaryStage);
                startDialog.close();
            }
        });
    }

    private void createApplication(Stage primaryStage) {
        resource = new ResourceManager();
        board = new Board();
        board.createBoardElements();
        int sceneWidth = resource.getIntConfig("Cell.length") * 8;
        int sceneHeight = resource.getIntConfig("Cell.length") * 10;
        Scene scene = new Scene(board, sceneWidth, sceneHeight);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryWindow = primaryStage;
    }

    private void initPlayType(String playTypeString) throws IllegalArgumentException {
        ProjectInfo.PlayType playType;
        switch (playTypeString) {
            case "Double":
                playType = ProjectInfo.PlayType.DOUBLE_PLAYER;
                break;
            case "SingleWhite":
                playType = ProjectInfo.PlayType.SINGLE_PLAYER;
                playType.setPlayerColor(PieceInfo.Color.WHITE);
                break;
            case "SingleBlack":
                playType = ProjectInfo.PlayType.SINGLE_PLAYER;
                playType.setPlayerColor(PieceInfo.Color.BLACK);
                break;
            default:
                throw new IllegalArgumentException("Unknown Play Type...");
        }
        GamePlay.getInstance().setPlayType(playType);
    }

    /**
     * Displays a modal dialog box with the given message, and closes the game
     * window when the dialog box is closed.
     *
     * @param message Message to be displayed in dialog box
     */
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
     * Main method
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
