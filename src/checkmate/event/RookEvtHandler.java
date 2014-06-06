/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.event;

import checkmate.design.Rook;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author bhasme
 */
public class RookEvtHandler implements IEventHandler {

    @Override
    public void handleMouseEvent(MouseEvent event) {
        Rook rook = (Rook) event.getSource();
        if (!gamePlay.isPieceSelected()) {
            processFirstClick(rook);
        } else {
            processSecondClick(rook);
        }
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {

    }

    private void processFirstClick(Rook piece) {
        gamePlay.setIsPieceSelected(true);
        gamePlay.setMovingPiece(piece);
    }

    private void processSecondClick(Rook piece) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
