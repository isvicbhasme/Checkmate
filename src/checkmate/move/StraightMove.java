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
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Isaac
 */
public class StraightMove implements IMove {

    /**
     * Piece instance that is allowed to move straight
     */
    private final Piece piece;

    /**
     * Maximum steps that this.piece is allowed to move
     */
    protected int maxSteps;

    /**
     * Indicates whether the piece can move horizontal
     */
    private final boolean isHorizontalMoveAllowed;

    /**
     * Indicates whether the piece can move vertical
     */
    private final boolean isBackwardMoveRestricted;
    private Address targetCell;
    private boolean isAttackingAllowed;

    /**
     * Performs validations related to straight movements
     *
     * @param piece Piece to be validated
     * @param maxSteps Maximum steps that is allowed in one move
     * @param isHorizontalMoveAllowed Shows if piece can move along the x-axis
     * (horizontal)
     */
    public StraightMove(Piece piece, int maxSteps, boolean isHorizontalMoveAllowed, boolean isBackwardMoveAllowed) {
        this.piece = piece;
        this.maxSteps = maxSteps;
        this.isHorizontalMoveAllowed = isHorizontalMoveAllowed;
        this.isBackwardMoveRestricted = !isBackwardMoveAllowed;
        this.isAttackingAllowed = true;
    }

    /**
     * Checks whether this.piece can move to the given target cell
     *
     * @param target Target cell against which move should be checked
     * @return true if movement is allowed, false otherwise.
     */
    @Override
    public boolean isMoveAllowed(Address target) {
        boolean canMove = false;
        if (isNumOfStepsValid(target)) {
            if (!isAttackingAllowed && Launcher.board.getCell(target).isOccupied()) {
                canMove = false;
            } else {
                setTargetCell(target);
                canMove = isPathFree();
            }
        }
        return canMove;
    }

    private boolean isPathFree() throws IllegalStateException {
        boolean isClear = false;
        if (piece.getRank() == targetCell.rank && isHorizontalMoveAllowed) {
            isClear = isHorizontalPathFree(targetCell.file);
        } else if (piece.getFile() == targetCell.file) {
            isClear = (isBackwardMoveRestricted && isPieceGoingBackward()) ? false
                    : isVerticalPathFree(targetCell.rank);
        }
        return isClear;
    }

    private boolean isVerticalPathFree(CellInfo.Rank toRank) throws IllegalStateException {
        boolean isPathClear = false;
        if (piece.getRank().compareTo(toRank) < 0) {
            Set<CellInfo.Rank> ranksInPath = EnumSet.range(piece.getRank(), toRank);
            isPathClear = isRankPathClear(ranksInPath);
        } else if (piece.getRank().compareTo(toRank) > 0) {
            Set<CellInfo.Rank> ranksInPath = EnumSet.range(toRank, piece.getRank());
            isPathClear = isRankPathClear(ranksInPath);
        } else {
            throw new IllegalStateException("Source and destination of move is the same");
        }
        return isPathClear;
    }

    private boolean isHorizontalPathFree(CellInfo.File toFile) throws IllegalStateException {
        boolean isPathClear = false;
        if (piece.getFile().compareTo(toFile) < 0) {
            Set<CellInfo.File> filesInPath = EnumSet.range(piece.getFile(), toFile);
            isPathClear = isFilePathClear(filesInPath);
        } else if (piece.getFile().compareTo(toFile) > 0) {
            Set<CellInfo.File> filesInPath = EnumSet.range(toFile, piece.getFile());
            isPathClear = isFilePathClear(filesInPath);
        } else {
            throw new IllegalStateException("Source and destination of move is the same");
        }
        return isPathClear;
    }

    private boolean isNumOfStepsValid(Address cellAddress) {
        if (isHorizontalMoveAllowed) {
            return (Math.abs(cellAddress.file.ordinal() - piece.getFile().ordinal()) <= maxSteps
                    && Math.abs(cellAddress.rank.ordinal() - piece.getRank().ordinal()) <= maxSteps);
        } else {
            return Math.abs(cellAddress.rank.ordinal() - piece.getRank().ordinal()) <= maxSteps;
        }
    }

    private boolean isFilePathClear(Set<CellInfo.File> filesInPath) {
        boolean isPathClear = true;
        Iterator<CellInfo.File> pathIterator = filesInPath.iterator();
        while (pathIterator.hasNext()) {
            CellInfo.File fileInPath = pathIterator.next();
            if (fileInPath == piece.getFile() || fileInPath == targetCell.file) {
                continue; //skip the ends, because it is either the piece's current position or the destination
            }
            Cell cellInPath = Launcher.board.getCell(piece.getRank(), fileInPath);
            if (cellInPath.isOccupied()) {
                isPathClear = false;
                break;
            }
        }
        return isPathClear;
    }

    private boolean isRankPathClear(Set<CellInfo.Rank> ranksInPath) {
        boolean isPathClear = true;
        Iterator<CellInfo.Rank> pathIterator = ranksInPath.iterator();
        while (pathIterator.hasNext()) {
            CellInfo.Rank rankInPath = pathIterator.next();
            if (rankInPath == piece.getRank() || rankInPath == targetCell.rank) {
                continue; //skip the ends, because it is either the piece's current position or the destination
            }
            Cell cellInPath = Launcher.board.getCell(rankInPath, piece.getFile());
            if (cellInPath.isOccupied()) {
                isPathClear = false;
                break;
            }
        }
        return isPathClear;
    }

    private boolean isPieceGoingBackward() {
        if (piece.isWhitePiece()) {
            return targetCell.rank.ordinal() > piece.getRank().ordinal();
        } else {
            return targetCell.rank.ordinal() < piece.getRank().ordinal();
        }
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    private void setTargetCell(Address targetCell) {
        this.targetCell = targetCell;
    }

    public void setIsAttackingAllowed(boolean isAttackingAllowed) {
        this.isAttackingAllowed = isAttackingAllowed;
    }
}
