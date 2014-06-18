/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.event;

import checkmate.design.Piece;
import static checkmate.event.IEventHandler.gamePlay;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Isaac
 */
public class PieceEvtHandler implements IEventHandler {

    @Override
    public void handleMouseEvent(MouseEvent event) {
        Piece piece = (Piece) event.getSource();
        if (gamePlay.isPieceSelected()) {
            processSecondClick(piece);
        } else {
            processFirstClick(piece);
        }
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {

    }

    protected void processFirstClick(Piece piece) {
        gamePlay.setMovingPiece(piece);
    }

    protected void processSecondClick(Piece piece) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
