/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.event;

import checkmate.Launcher;
import checkmate.design.Cell;
import checkmate.design.Piece;
import static checkmate.event.IEventHandler.gamePlay;
import checkmate.manager.RepetitionManager;
import checkmate.util.Address;
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
            if (isTurnToPlay(gamePlay.getMovingPiece())) {
                processSecondClick(piece);
            }
        } else if (isTurnToPlay(piece)) {
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
        if (areOponents(piece, gamePlay.getMovingPiece())) {
            processPieceAttack(piece.getCell(), gamePlay.getMovingPiece(), piece);
        }
    }

    private void processPieceAttack(Cell targetCell, Piece attackingPiece, Piece attackedPiece) {
        Address targetAddress = new Address(targetCell.getRank(), targetCell.getFile());
        Address sourceAddress = new Address(attackingPiece.getRank(), attackingPiece.getFile());
        gamePlay.resetPieceMovement();
        if (attackingPiece.getMoveHandler().isMovePermitted(targetAddress)) {
            targetCell.removePieceFromCellGroup(attackedPiece);
            Launcher.board.removeFromBoard(attackedPiece);
            attackingPiece.getMoveHandler().moveTo(targetAddress);
            gamePlay.togglePlayTurn();
            RepetitionManager.getInstance().storePieceAttackHash(attackedPiece, targetAddress, attackingPiece, sourceAddress);
        }
    }

    private boolean isTurnToPlay(Piece selectedPiece) {
        if (selectedPiece.isWhitePiece()) {
            return gamePlay.isWhitesTurnToPlay();
        } else {
            return gamePlay.isBlacksTurnToPlay();
        }
    }

    private boolean areOponents(Piece piece1, Piece piece2) {
        if (piece1.isWhitePiece() && !piece2.isWhitePiece()) {
            return true;
        } else {
            return !piece1.isWhitePiece() && piece2.isWhitePiece();
        }
    }
}
