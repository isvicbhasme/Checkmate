/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.event;

import checkmate.GamePlay;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Isaac
 */
public interface IEventHandler {

    public GamePlay gamePlay = GamePlay.getInstance();

    public void handleMouseEvent(MouseEvent event);

    public void handleKeyEvent(KeyEvent event);
}
