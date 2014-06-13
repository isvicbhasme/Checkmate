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
public class Straight implements IMove {

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

    /**
     * Performs validations related to straight movements
     *
     * @param piece Piece to be validated
     * @param maxSteps Maximum steps that is allowed in one move
     * @param isHorizontalMoveAllowed Shows if piece can move along the x-axis
     * (horizontal)
     */
    public Straight(Piece piece, int maxSteps, boolean isHorizontalMoveAllowed, boolean isBackwardMoveAllowed) {
        this.piece = piece;
        this.maxSteps = maxSteps;
        this.isHorizontalMoveAllowed = isHorizontalMoveAllowed;
        this.isBackwardMoveRestricted = !isBackwardMoveAllowed;
    }

    /**
     * Checks whether this.piece can move to the given target cell
     *
     * @param cellAddress Target cell against which move should be checked
     * @return true if movement is allowed, false otherwise.
     */
    @Override
    public boolean isMoveAllowed(Address cellAddress) {
        boolean isPathClear = false;
        if (isNumOfStepsValid(cellAddress)) {
            isPathClear = isPathFree(cellAddress);
        } else {
            return isPathClear;
        }
        return isPathClear;
    }

    private boolean isPathFree(Address cellAddress) throws IllegalStateException {
        boolean isClear = false;
        if (piece.getRankPosition() == cellAddress.rank && isHorizontalMoveAllowed) {
            isClear = isHorizontalPathFree(cellAddress.file);
        } else if (piece.getFilePosition() == cellAddress.file) {
            isClear = (isBackwardMoveRestricted && isPieceGoingBackward(cellAddress)) ? false 
                    : isVerticalPathFree(cellAddress.rank);
        }
        return isClear;
    }

    private boolean isVerticalPathFree(CellInfo.Rank toRank) throws IllegalStateException {
        boolean isPathClear = false;
        if (piece.getRankPosition().compareTo(toRank) < 0) {
            Set<CellInfo.Rank> ranksInPath = EnumSet.range(piece.getRankPosition(), toRank);
            isPathClear = isRankPathClear(ranksInPath);
        } else if (piece.getRankPosition().compareTo(toRank) > 0) {
            Set<CellInfo.Rank> ranksInPath = EnumSet.range(toRank, piece.getRankPosition());
            isPathClear = isRankPathClear(ranksInPath);
        } else {
            throw new IllegalStateException("Source and destination of move is the same");
        }
        return isPathClear;
    }

    private boolean isHorizontalPathFree(CellInfo.File toFile) throws IllegalStateException {
        boolean isPathClear = false;
        if (piece.getFilePosition().compareTo(toFile) < 0) {
            Set<CellInfo.File> filesInPath = EnumSet.range(piece.getFilePosition(), toFile);
            isPathClear = isFilePathClear(filesInPath);
        } else if (piece.getFilePosition().compareTo(toFile) > 0) {
            Set<CellInfo.File> filesInPath = EnumSet.range(toFile, piece.getFilePosition());
            isPathClear = isFilePathClear(filesInPath);
        } else {
            throw new IllegalStateException("Source and destination of move is the same");
        }
        return isPathClear;
    }

    private boolean isNumOfStepsValid(Address cellAddress) {
        if (isHorizontalMoveAllowed) {
            return (Math.abs(cellAddress.file.ordinal() - piece.getFilePosition().ordinal()) <= maxSteps
                    && Math.abs(cellAddress.rank.ordinal() - piece.getRankPosition().ordinal()) <= maxSteps);
        } else {
            return Math.abs(cellAddress.rank.ordinal() - piece.getRankPosition().ordinal()) <= maxSteps;
        }
    }

    private boolean isFilePathClear(Set<CellInfo.File> filesInPath) {
        boolean isPathClear = true;
        Iterator<CellInfo.File> pathIterator = filesInPath.iterator();
        while (pathIterator.hasNext()) {
            CellInfo.File fileInPath = pathIterator.next();
            if (fileInPath == piece.getFilePosition()) {
                continue; //skip because this is where is current piece resides
            }
            Cell cellInPath = Launcher.board.getCell(piece.getRankPosition(), fileInPath);
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
            if (rankInPath == piece.getRankPosition()) {
                continue; //skip because this is where is current piece resides
            }
            Cell cellInPath = Launcher.board.getCell(rankInPath, piece.getFilePosition());
            if (cellInPath.isOccupied()) {
                isPathClear = false;
                break;
            }
        }
        return isPathClear;
    }

    private boolean isPieceGoingBackward(Address targetCell) {
        if (piece.isWhitePiece()) {
            return targetCell.rank.ordinal() > piece.getRankPosition().ordinal();
        } else {
            return targetCell.rank.ordinal() < piece.getRankPosition().ordinal();
        }
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }
}
