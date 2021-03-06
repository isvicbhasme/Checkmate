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
public class CrossMove extends Moves implements IMove {

    /**
     * Maximum steps that this.piece is allowed to move
     */
    private int maxSteps;

    private boolean isOnlyAttackAllowed;

    /**
     * Performs validations related to cross movements
     *
     * @param piece Piece to be validated
     * @param maxSteps Maximum steps that is allowed in one move
     */
    public CrossMove(Piece piece, int maxSteps) {
        super(piece);
        this.maxSteps = maxSteps;
        this.isOnlyAttackAllowed = false;
    }

    /**
     * Checks whether this.piece can move to the given target cell
     *
     * @param targetCell
     * @return true if movement is allowed, false otherwise.
     */
    @Override
    public boolean isMoveAllowed(Address targetCell) {
        boolean canMove = false;
        if (isDiagonal(targetCell) && isNumOfStepsValid(targetCell.file)) {
            if (isOnlyAttackAllowed && !Launcher.board.getCell(targetCell).isOccupied()) {
                canMove = false;
            } else {
                canMove = (isBackwardMoveRestricted() && isPieceGoingBackward(targetCell)) ? false : isMoveObstructed(targetCell);
            }
        }
        return canMove;
    }

    private boolean isDiagonal(Address target) {
        int rankDifference = Math.abs(piece.getRank().ordinal() - target.rank.ordinal());
        int fileDifference = Math.abs(piece.getFile().ordinal() - target.file.ordinal());
        return rankDifference == fileDifference;
    }

    private boolean isMoveObstructed(Address target) throws IllegalStateException {
        boolean isClear = true;
        Address current = piece.getAddress();
        while ((current = getNextCellInPath(current, target)) != null && !current.equals(target)) {
            if (Launcher.board.getCell(current).isOccupied()) {
                isClear = false;
                break;
            }
        }
        return isClear;
    }

    private Address getNextCellInPath(Address currentCell, Address lastCell) {
        CellInfo.Rank rank = currentCell.rank;
        CellInfo.File file = currentCell.file;
        CellInfo.Rank lastRank = lastCell.rank;
        CellInfo.File lastFile = lastCell.file;
        Address newAddress = new Address();
        if (rank.ordinal() < lastRank.ordinal()) {
            newAddress.rank = CellInfo.Rank.values[rank.ordinal() + 1];
        } else {
            newAddress.rank = CellInfo.Rank.values[rank.ordinal() - 1];
        }
        if (file.ordinal() < lastFile.ordinal()) {
            newAddress.file = CellInfo.File.values[file.ordinal() + 1];
        } else {
            newAddress.file = CellInfo.File.values[file.ordinal() - 1];
        }
        return newAddress;
    }

    private boolean isNumOfStepsValid(CellInfo.File targetFile) {
        return Math.abs(piece.getFile().ordinal() - targetFile.ordinal()) <= maxSteps;
    }

    public void setIsOnlyAttackAllowed(boolean isOnlyAttackAllowed) {
        this.isOnlyAttackAllowed = isOnlyAttackAllowed;
    }
}
