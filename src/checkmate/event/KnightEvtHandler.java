/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.event;

import checkmate.design.IMovable;
import checkmate.design.Knight;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author bhasme
 */
public class KnightEvtHandler {
    IMovable movableInterface;

    public KnightEvtHandler(IMovable movableInterface) {
        this.movableInterface = movableInterface;
    }
    
    public void handleMouseEvent(MouseEvent event) {
        Knight knight = (Knight)event.getSource();
        System.out.println("String: "+knight.getText());
    }
    
    public void handleKeyEvent(KeyEvent event) {
        
    }
}
