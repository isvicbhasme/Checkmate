/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.event;

import checkmate.design.Cell;
import checkmate.design.Piece;
import checkmate.manager.RepetitionManager;
import checkmate.util.CellInfo;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Isaac
 */
public class CellEvtHandler implements IEventHandler {

    /**
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
            }
        }
    }

    /**
     *
     * @param event KeyEvent object
     */
    @Override
    public void handleKeyEvent(KeyEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void processPieceMovement(Cell clickedCell, Piece selectedPiece) {
        CellInfo.Rank newRank = clickedCell.getRank();
        CellInfo.File newFile = clickedCell.getFile();
        CellInfo.Rank oldRank = selectedPiece.getRank();
        CellInfo.File oldFile = selectedPiece.getFile();
        gamePlay.resetPieceMovement();
        if (selectedPiece.getMoveHandler().moveIfPermitted(newRank, newFile)) {
            gamePlay.togglePlayTurn();
            RepetitionManager.getInstance().hash(selectedPiece.getPieceType(), oldRank, oldFile);
            RepetitionManager.getInstance().hash(selectedPiece.getPieceType(), newRank, newFile);
            RepetitionManager.getInstance().storeHash();
        }
    }

    private boolean isTurnToPlay(Piece selectedPiece) {
        if (selectedPiece.isWhitePiece()) {
            return gamePlay.isWhitesTurnToPlay();
        } else {
            return gamePlay.isBlacksTurnToPlay();
        }
    }
}
