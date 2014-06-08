/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.move;

import checkmate.Launcher;
import checkmate.design.Cell;
import checkmate.design.Piece;
import checkmate.util.CellInfo;

/**
 *
 * @author Isaac
 */
public class RookMoves implements IMovable{
    
    /**
     * This class is responsible for calculations and validations of straight movements
     */
    protected Straight straightMove;

    /**
     * Piece::Rook on which the moves are done
     */
    protected Piece rook;

    /**
     *
     * @param piece Rook on which moves are done
     */
    public RookMoves(Piece piece) {
        straightMove = new Straight(piece, Integer.MAX_VALUE, true);
        rook = piece;
    }

    /**
     * Checks whether a move to the specified cell is valid. But the actual move is not performed.
     * @param rank Rank of the target cell
     * @param file File of the target cell
     * @return True if move is permitted. False otherwise.
     */
    @Override
    public boolean isMovePermitted(CellInfo.Rank rank, CellInfo.File file) {
        return straightMove.isMoveAllowed(rank, file);
    }
    
    /**
     * Moves the piece to the specified cell. Note that the validity of the movement is not checked by this method.
     * @param newRank Rank of the target cell
     * @param newFile File of the target cell
     */
    @Override
    public void moveTo(CellInfo.Rank newRank, CellInfo.File newFile) {
        Cell currentCell = Launcher.board.getCell(rook.getRankPosition(), rook.getFilePosition());
        currentCell.removePieceFromCellGroup(rook);
        currentCell.enableEventHandlers();
        rook.setPosition(newRank, newFile);
    }

    /**
     * Moves the piece to the specified cell, if it is permitted.
     * @param rank Rank of the target cell
     * @param file File of the target cell
     */
    @Override
    public void moveIfPermitted(CellInfo.Rank rank, CellInfo.File file) {
        if(isMovePermitted(rank, file))
            moveTo(rank, file);
    }
    
}
