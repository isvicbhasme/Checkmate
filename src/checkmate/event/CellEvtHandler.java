/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.event;

import checkmate.design.Cell;
import checkmate.design.Piece;
import checkmate.util.CellInfo;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Isaac
 */
public class CellEvtHandler implements IEventHandler {

    @Override
    public void handleMouseEvent(MouseEvent event) {
        Cell clickedCell = (Cell) event.getSource();
        if (gamePlay.isPieceSelected() && !clickedCell.isOccupied()) {
            processPieceMovement(clickedCell);
        }
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void processPieceMovement(Cell clickedCell) {
        CellInfo.Rank newRank = clickedCell.getRank();
        CellInfo.File newFile = clickedCell.getFile();
        Piece selectedPiece = gamePlay.getMovingPiece();
        selectedPiece.moveTo(newRank, newFile);
        gamePlay.resetPieceMovement();
    }

}
