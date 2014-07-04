/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.move;

import checkmate.Launcher;
import checkmate.design.Piece;
import checkmate.util.Address;
import checkmate.util.CellInfo;

/**
 *
 * @author Isaac
 */
public abstract class Moves {
    /**
     * Indicates whether backward movement is restricted
     */
    private boolean isBackwardMoveRestricted = false;
    /**
     * Piece instance that is allowed to move straight
     */
    protected final Piece piece;
    protected Address targetAddress;

    public Moves(Piece piece) {
        this.piece = piece;
    }
    
    

    protected boolean isPieceGoingBackward(Address cell) {
        if (piece.isWhitePiece()) {
            return cell.rank.ordinal() > piece.getRank().ordinal();
        } else {
            return cell.rank.ordinal() < piece.getRank().ordinal();
        }
    }

    protected void setTargetCell(Address targetCell) {
        this.targetAddress = targetCell;
    }

    protected boolean isCellAbovePiece(Address cell) {
        return cell.rank.ordinal() < piece.getRank().ordinal();
    }

    protected boolean isCellLeftOfPiece(Address cell) {
        return cell.file.ordinal() < piece.getFile().ordinal();
    }
    
    protected void setIsBackwardMoveRestricted(boolean isRestricted) {
        this.isBackwardMoveRestricted = isRestricted;
    }
    
    protected boolean isBackwardMoveRestricted() {
        return this.isBackwardMoveRestricted;
    }

    protected boolean isOpponent(CellInfo.Rank rank, CellInfo.File file) {
        return piece.isWhitePiece() != Launcher.board.getCell(rank, file).isOccupiedByWhite();
    }
    
}
