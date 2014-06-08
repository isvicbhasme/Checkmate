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
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Isaac
 */
public class Straight {

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
     * Performs validations related to straight movements
     *
     * @param piece Piece to be validated
     * @param maxSteps Maximum steps that is allowed in one move
     * @param isHorizontalMoveAllowed Shows if piece can move along the x-axis
     * (horizontal)
     */
    public Straight(Piece piece, int maxSteps, boolean isHorizontalMoveAllowed) {
        this.piece = piece;
        this.maxSteps = maxSteps;
        this.isHorizontalMoveAllowed = isHorizontalMoveAllowed;
    }

    /**
     * Checks whether this.piece can move to the given target cell
     *
     * @param toRank Rank of target cell
     * @param toFile File of target cell
     * @return true if movement is allowed, false otherwise.
     */
    public boolean isMoveAllowed(CellInfo.Rank toRank, CellInfo.File toFile) {
        boolean isPathClear = false;
        if (isNumOfStepsValid(toFile, toRank)) {
            isPathClear = isMoveObstructed(toRank, toFile);
        } else {
            return isPathClear;
        }
        System.out.println("Straight " + isPathClear);
        return isPathClear;
    }

    private boolean isMoveObstructed(CellInfo.Rank toRank, CellInfo.File toFile) throws IllegalStateException {
        boolean isClear = false;
        if (piece.getRankPosition() == toRank && isHorizontalMoveAllowed) {
            isClear = isHorizontalMoveBlocked(toFile);
        } else if (piece.getFilePosition() == toFile) {
            isClear = isVerticalMoveBlocked(toRank);
        }
        return isClear;
    }

    private boolean isVerticalMoveBlocked(CellInfo.Rank toRank) throws IllegalStateException {
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

    private boolean isHorizontalMoveBlocked(CellInfo.File toFile) throws IllegalStateException {
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

    private boolean isNumOfStepsValid(CellInfo.File targetFile, CellInfo.Rank targetRank) {
        if (isHorizontalMoveAllowed) {
            return (Math.abs(targetFile.ordinal() - piece.getFilePosition().ordinal()) < maxSteps
                    && Math.abs(targetRank.ordinal() - piece.getRankPosition().ordinal()) < maxSteps);
        } else {
            return Math.abs(targetRank.ordinal() - piece.getRankPosition().ordinal()) < maxSteps;
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
}
