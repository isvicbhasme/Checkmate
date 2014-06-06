/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.event;

import checkmate.design.Knight;
import static checkmate.event.IEventHandler.gamePlay;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author bhasme
 */
public class KnightEvtHandler implements IEventHandler {

    @Override
    public void handleMouseEvent(MouseEvent event) {
        Knight knight = (Knight) event.getSource();
        if (!gamePlay.isPieceSelected()) {
            processFirstClick(knight);
        } else {
            processSecondClick(knight);
        }
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {

    }

    private void processFirstClick(Knight piece) {
        gamePlay.setIsPieceSelected(true);
        gamePlay.setMovingPiece(piece);
    }

    private void processSecondClick(Knight piece) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
