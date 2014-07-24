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
import checkmate.util.ProjectInfo;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Captures keyboard and mouse events on a chess piece from the chess board
 *
 * @author Isaac
 */
public class PieceEvtHandler implements IEventHandler {

    /**
     * Handles mouse events triggered from a chess cell
     *
     * @param event MouseEvent object
     */
    @Override
    public void handleMouseEvent(MouseEvent event) {
        Piece piece = (Piece) event.getSource();
        if (gamePlay.isPieceSelected()) {
            if (isTurnToPlay(gamePlay.getMovingPiece())) {
                processSecondClick(piece);
                event.consume();
                if (gamePlay.getPlayType() == ProjectInfo.PlayType.SINGLE_PLAYER && gamePlay.isAiTurnToPlay()) {
                    gamePlay.triggerAiMove();
                }
            }
        } else if (isTurnToPlay(piece)) {
            processFirstClick(piece);
            event.consume();
        }
    }

    /**
     * Handles keyboard events triggered from a chess cell
     *
     * @param event KeyEvent object
     */
    @Override
    public void handleKeyEvent(KeyEvent event) {

    }

    /**
     * Selects the clicked piece to make a move or attack
     *
     * @param piece
     */
    protected void processFirstClick(Piece piece) {
        gamePlay.setMovingPiece(piece);
    }

    /**
     * Indicates the opponents piece to be attacked by the selected piece.
     *
     * @param piece
     */
    protected void processSecondClick(Piece piece) {
        if (areOponents(piece, gamePlay.getMovingPiece())) {
            processPieceAttack(piece.getCell(), gamePlay.getMovingPiece(), piece);
        }
    }

    /**
     * Performs validations on the current attack on a piece and performs the
     * attack only if valid
     *
     * @param targetCell cell at which the attacked piece is located
     * @param attackingPiece piece performing the attack
     * @param attackedPiece opponent's piece which is being attacked
     */
    private void processPieceAttack(Cell targetCell, Piece attackingPiece, Piece attackedPiece) {
        Address targetAddress = new Address(targetCell.getRank(), targetCell.getFile());
        gamePlay.resetPieceMovement();
        if (attackingPiece.getMoveHandler().isMovePermitted(targetAddress)) {
            attackingPiece.getMoveHandler().moveTo(targetAddress);
            Launcher.board.removeFromBoard(attackedPiece);
            gamePlay.togglePlayTurn();
            RepetitionManager.getInstance().hashTogglePlay();
            RepetitionManager.getInstance().storeHash();
        }
    }

    /**
     * Indicates whether it is the selected piece's (white or black) turn to
     * make a move on the chess board
     *
     * @param selectedPiece selected piece
     * @return true if it is a valid turn, false otherwise
     */
    private boolean isTurnToPlay(Piece selectedPiece) {
        if (selectedPiece.isWhitePiece()) {
            return gamePlay.isWhitesTurnToPlay();
        } else {
            return gamePlay.isBlacksTurnToPlay();
        }
    }

    /**
     * Indicates whether the given two pieces are opponents to each other
     *
     * @param piece1 Piece 1
     * @param piece2 Piece 2
     * @return true if the pieces are opponents to each other, false otherwise
     */
    private boolean areOponents(Piece piece1, Piece piece2) {
        if (piece1.isWhitePiece() && !piece2.isWhitePiece()) {
            return true;
        } else {
            return !piece1.isWhitePiece() && piece2.isWhitePiece();
        }
    }
}
