/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.move;

import checkmate.Launcher;
import checkmate.design.Cell;
import checkmate.design.Piece;
import checkmate.util.Address;
import checkmate.util.CellInfo;

/**
 *
 * @author Isaac
 */
public abstract class PieceMoves implements IMovable{
    
    protected IMove moveType;
    protected Piece piece;
    
    /**
     * Moves the piece to the specified cell. Note that the validity of the movement is not checked by this method.
     * @param rank Rank of the target cell
     * @param file File of the target cell
     */
    @Override
    public void moveTo(CellInfo.Rank rank, CellInfo.File file) {
        Cell currentCell = Launcher.board.getCell(piece.getRankPosition(), piece.getFilePosition());
        currentCell.removePieceFromCellGroup(piece);
        currentCell.enableEventHandlers();
        piece.setPosition(rank, file);
    }
    
    /**
     * Checks whether a move to the specified cell is valid. But the actual move is not performed.
     * @param rank Rank of the target cell
     * @param file File of the target cell
     * @return True if move is permitted. False otherwise.
     */
    @Override
    public boolean isMovePermitted(CellInfo.Rank rank, CellInfo.File file) {
        return moveType.isMoveAllowed(new Address(rank, file));
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
