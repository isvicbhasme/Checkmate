/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.event;

import checkmate.design.IMovable;
import checkmate.design.Rook;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author bhasme
 */
public class RookEvtHandler {
    IMovable movableInterface;

    public RookEvtHandler(IMovable movableInterface) {
        this.movableInterface = movableInterface;
    }
    
    public void handleMouseEvent(MouseEvent event) {
        Rook rook = (Rook)event.getSource();
        System.out.println("String: "+rook.getText());
    }
    
    public void handleKeyEvent(KeyEvent event) {
        
    }
}
