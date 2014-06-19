/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.move;

import checkmate.design.Piece;
import checkmate.util.Address;
import checkmate.util.CellInfo;

/**
 *
 * @author bhasme
 */
public class Castling implements IMove{
    /**
     * Piece instance that is allowed to move straight
     */
    private final Piece king;
    private Address cellSkippedByKing;
    
    /**
     * Performs validations related to Castling movements
     * @param piece Piece to be validated
     */
    public Castling(Piece piece) {
        this.king = piece;
    }

    @Override
    public boolean isMoveAllowed(Address targetCell) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Checks whether the target cell is a proper cell to which castling can be done
     * @param targetCell Cell to which the king moves during castling
     * @return true if the targetCell is valid for castling, false otherwise
     */
    public boolean isTargetAddressValid(Address targetCell) {
        boolean isRankValid = targetCell.rank == king.getRankPosition();
        boolean isFileValid = (targetCell.file == CellInfo.File.B || targetCell.file == CellInfo.File.F);
        return isRankValid && isFileValid;
    }
    
    /**
     * Set the skippedCell address in the class
     * @param targetCell Cell to which the king moves during castling
     */
    public void setCellSkippedByKing(Address targetCell) {
        cellSkippedByKing = new Address();
        cellSkippedByKing.rank = targetCell.rank;
        cellSkippedByKing.file = targetCell.file;
    }
    
}
