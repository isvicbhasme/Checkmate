/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.event;

import checkmate.manager.GamePlay;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Interface for all event handlers
 * @author Isaac
 */
public interface IEventHandler {

    public GamePlay gamePlay = GamePlay.getInstance();

    /**
     * Handles mouse events triggered from a chess cell
     * @param event MouseEvent object
     */
    public void handleMouseEvent(MouseEvent event);

    /**
     * Handles keyboard events triggered from a chess cell
     * @param event KeyEvent object
     */
    public void handleKeyEvent(KeyEvent event);
}
