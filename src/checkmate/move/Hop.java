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
public class Hop implements IMove{

    /**
     * Piece instance that is allowed to move straight
     */
    private final Piece piece;
    
    private enum ExpectedMagnitude {
        TOP_LEFT(-1, -1),
        TOP_RIGHT(-1, 1),
        BOTTOM_LEFT(1, -1),
        BOTTOM_RIGHT(1, 1);
        
        int first;
        int second;

        private ExpectedMagnitude(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
    
    public Hop(Piece piece) {
        this.piece = piece;
    }
    
    @Override
    public boolean isMoveAllowed(Address targetCell) {
        CellInfo.Rank srcRank = piece.getRankPosition();
        CellInfo.File srcFile = piece.getFilePosition();
        if(srcRank == targetCell.rank || srcFile == targetCell.file)
            return false;
        
        Address[] expectedCells;
        if(isCellAbovePiece(targetCell)) {
            if(isCellLeftOfPiece(targetCell)) {
                expectedCells = getExpectedCells(ExpectedMagnitude.TOP_LEFT);
            } else {
                expectedCells = getExpectedCells(ExpectedMagnitude.TOP_RIGHT);
            }
        }
        else {
            if(isCellLeftOfPiece(targetCell)) {
                expectedCells = getExpectedCells(ExpectedMagnitude.BOTTOM_LEFT);
            } else {
                expectedCells = getExpectedCells(ExpectedMagnitude.BOTTOM_RIGHT);
            }
        }
        for(Address cell: expectedCells) {
            if(cell != null)
                System.out.println("Cell checks: "+cell.rank+","+cell.file);
        }
        if(expectedCells == null)
            return false;
        return isTargetCellExpected(expectedCells, targetCell);
    }
    
    private boolean isCellAbovePiece(Address cell) {
        return (cell.rank.ordinal() < piece.getRankPosition().ordinal());
    }
    
    private boolean isCellLeftOfPiece(Address cell) {
        return (cell.file.ordinal() < piece.getFilePosition().ordinal());
    }
    
    private Address[] getExpectedCells(ExpectedMagnitude magnitude) {
        System.out.println("Getting "+magnitude);
        Address expectedCells[] = new Address[2];
        int index = 0;
        int rank = piece.getRankPosition().ordinal() + (2 * magnitude.first);
        int file = piece.getFilePosition().ordinal() + (1 * magnitude.second);
        if(isRankFileValid(rank, file))
            expectedCells[index++] = new Address(CellInfo.Rank.values[rank], CellInfo.File.values[file]);
        rank = piece.getRankPosition().ordinal() + (1 * magnitude.first);
        file = piece.getFilePosition().ordinal() + (2 * magnitude.second);
        if(isRankFileValid(rank, file))
            expectedCells[index++] = new Address(CellInfo.Rank.values[rank], CellInfo.File.values[file]);
        return expectedCells;
    }
    
    private Address[] getExpectedTopRightells() {
        Address expectedCells[] = new Address[2];
        int index = 0;
        int rank = piece.getRankPosition().ordinal() - 2;
        int file = piece.getFilePosition().ordinal() + 1;
        if(isRankFileValid(rank, file))
            expectedCells[index++] = new Address(CellInfo.Rank.values[rank], CellInfo.File.values[file]);
        rank = piece.getRankPosition().ordinal() - 1;
        file = piece.getFilePosition().ordinal() + 2;
        if(isRankFileValid(rank, file))
            expectedCells[index++] = new Address(CellInfo.Rank.values[rank], CellInfo.File.values[file]);
        return expectedCells;
    }
    
    private Address[] getExpectedBottomLeftCells() {
        Address expectedCells[] = new Address[2];
        int index = 0;
        int rank = piece.getRankPosition().ordinal() + 2;
        int file = piece.getFilePosition().ordinal() - 1;
        if(isRankFileValid(rank, file))
            expectedCells[index++] = new Address(CellInfo.Rank.values[rank], CellInfo.File.values[file]);
        rank = piece.getRankPosition().ordinal() + 1;
        file = piece.getFilePosition().ordinal() - 2;
        if(isRankFileValid(rank, file))
            expectedCells[index++] = new Address(CellInfo.Rank.values[rank], CellInfo.File.values[file]);
        return expectedCells;
    }
    
    private Address[] getExpectedBottomRightells() {
        Address expectedCells[] = new Address[2];
        int index = 0;
        int rank = piece.getRankPosition().ordinal() + 2;
        int file = piece.getFilePosition().ordinal() + 1;
        if(isRankFileValid(rank, file))
            expectedCells[index++] = new Address(CellInfo.Rank.values[rank], CellInfo.File.values[file]);
        rank = piece.getRankPosition().ordinal() + 1;
        file = piece.getFilePosition().ordinal() + 2;
        if(isRankFileValid(rank, file))
            expectedCells[index++] = new Address(CellInfo.Rank.values[rank], CellInfo.File.values[file]);
        return expectedCells;
    }

    private boolean isTargetCellExpected(Address[] expectedCells, Address target) {
        for(Address cell: expectedCells) {
            if(cell != null && target.rank == cell.rank && target.file == cell.file)
                return true;
        }
        return false;
    }
    
    private boolean isRankFileValid(int rank, int file) {
        return (rank >= 0 && rank <= 7 && file >= 0 && file <= 7);
    }
    
}
