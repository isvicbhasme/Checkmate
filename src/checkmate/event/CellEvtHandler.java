/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.event;

import checkmate.design.Cell;
import checkmate.design.Piece;
import checkmate.manager.RepetitionManager;
import checkmate.util.Address;
import checkmate.util.ProjectInfo;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Captures keyboard and mouse events on a cell of the chess board
 *
 * @author Isaac
 */
public class CellEvtHandler implements IEventHandler {

    /**
     * Handles mouse events triggered from a chess cell
     *
     * @param event MouseEvent object
     */
    @Override
    public void handleMouseEvent(MouseEvent event) {
        Cell clickedCell = (Cell) event.getSource();
        if (gamePlay.isPieceSelected() && !clickedCell.isOccupied()) {
            Piece selectedPiece = gamePlay.getMovingPiece();
            if (isTurnToPlay(selectedPiece)) {
                processPieceMovement(clickedCell, selectedPiece);
                event.consume();
                if(gamePlay.getPlayType() == ProjectInfo.PlayType.SINGLE_PLAYER && gamePlay.isAiTurnToPlay()) {
                    gamePlay.triggerAiMove();
                }
            }
        }
    }

    /**
     * Handles keyboard events triggered from a chess cell
     *
     * @param event KeyEvent object
     */
    @Override
    public void handleKeyEvent(KeyEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Performs a piece move to an empty cell, if the move is valid.
     *
     * @param clickedCell is the target cell
     * @param selectedPiece is the piece that needs to be moved
     */
    private void processPieceMovement(Cell clickedCell, Piece selectedPiece) {
        Address targetAddress = new Address(clickedCell.getRank(), clickedCell.getFile());
        gamePlay.resetPieceMovement();
        if (selectedPiece.getMoveHandler().moveIfPermitted(targetAddress)) {
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
}
