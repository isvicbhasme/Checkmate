/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.event;

import checkmate.design.Rook;
import static checkmate.event.IEventHandler.gamePlay;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Isaac
 */
public abstract class PieceEvtHandler implements IEventHandler {

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

    protected void processFirstClick(Rook piece) {
        gamePlay.setIsPieceSelected(true);
        gamePlay.setMovingPiece(piece);
    }

    protected abstract void processSecondClick(Rook piece);
}
