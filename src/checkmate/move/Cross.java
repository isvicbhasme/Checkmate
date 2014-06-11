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
public class Cross implements IMove {

    /**
     * Piece instance that is allowed to move straight
     */
    private final Piece piece;

    /**
     * Maximum steps that this.piece is allowed to move
     */
    protected int maxSteps;

    /**
     * Performs validations related to straight movements
     *
     * @param piece Piece to be validated
     * @param maxSteps Maximum steps that is allowed in one move
     */
    public Cross(Piece piece, int maxSteps) {
        this.piece = piece;
        this.maxSteps = maxSteps;
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
        if (isDiagonal(toRank, toFile) && isNumOfStepsValid(toFile)) {
            isPathClear = isMoveObstructed(toRank, toFile);
        }
        return isPathClear;
    }

    private boolean isDiagonal(CellInfo.Rank toRank, CellInfo.File toFile) {
        int rankDifference = Math.abs(piece.getRankPosition().ordinal() - toRank.ordinal());
        int fileDifference = Math.abs(piece.getFilePosition().ordinal() - toFile.ordinal());
        return rankDifference == fileDifference;
    }

    private boolean isMoveObstructed(CellInfo.Rank toRank, CellInfo.File toFile) throws IllegalStateException {
        boolean isClear = true;
        Set<CellInfo.Rank> ranksInPath = getRanksInPath(toRank);
        Set<CellInfo.File> filesInPath = getFilesInPath(toFile);
        Iterator<CellInfo.Rank> rankIterator = ranksInPath.iterator();
        Iterator<CellInfo.File> fileIterator = filesInPath.iterator();
        while (rankIterator.hasNext() && fileIterator.hasNext()) {
            CellInfo.Rank rankInPath = rankIterator.next();
            CellInfo.File fileInPath = fileIterator.next();
            if (fileInPath == piece.getFilePosition() && rankInPath == piece.getRankPosition()) {
                continue; //skip because this is where is current piece resides
            }
            Cell cellInPath = Launcher.board.getCell(rankInPath, fileInPath);
            if (cellInPath.isOccupied()) {
                isClear = false;
                break;
            }
        }
        return isClear;
    }

    private Set<CellInfo.File> getFilesInPath(CellInfo.File toFile) {
        Set<CellInfo.File> filesInPath;
        if (piece.getFilePosition().ordinal() < toFile.ordinal()) {
            filesInPath = EnumSet.range(piece.getFilePosition(), toFile);
        } else {
            filesInPath = EnumSet.range(toFile, piece.getFilePosition());
        }
        return filesInPath;
    }

    private Set<CellInfo.Rank> getRanksInPath(CellInfo.Rank toRank) {
        Set<CellInfo.Rank> ranksInPath;
        if (piece.getRankPosition().ordinal() < toRank.ordinal()) {
            ranksInPath = EnumSet.range(piece.getRankPosition(), toRank);
        } else {
            ranksInPath = EnumSet.range(toRank, piece.getRankPosition());
        }
        return ranksInPath;
    }

    private boolean isNumOfStepsValid(CellInfo.File targetFile) {
        return Math.abs(piece.getFilePosition().ordinal() - targetFile.ordinal()) <= maxSteps;
    }
}
