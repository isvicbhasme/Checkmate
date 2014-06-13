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
     * @param targetCell
     * @return true if movement is allowed, false otherwise.
     */
    @Override
    public boolean isMoveAllowed(Address targetCell) {
        boolean isPathClear = false;
        if (isDiagonal(targetCell) && isNumOfStepsValid(targetCell.file)) {
            isPathClear = isMoveObstructed(targetCell);
        }
        return isPathClear;
    }

    private boolean isDiagonal(Address target) {
        int rankDifference = Math.abs(piece.getRankPosition().ordinal() - target.rank.ordinal());
        int fileDifference = Math.abs(piece.getFilePosition().ordinal() - target.file.ordinal());
        return rankDifference == fileDifference;
    }

    private boolean isMoveObstructed(Address target) throws IllegalStateException {
        boolean isClear = true;
        Address current = piece.getAddress();
        while((current = getNextCellInPath(current, target)) != null) {
            if(Launcher.board.getCell(current).isOccupied())
            {
                isClear = false;
                break;
            }
        }
        return isClear;
    }

    private Address getNextCellInPath(Address currentCell, Address lastCell) {
        if (currentCell.equals(lastCell)) {
            return null;
        }
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
