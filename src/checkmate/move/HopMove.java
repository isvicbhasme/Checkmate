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
public class HopMove  extends Moves implements IMove{
    
    private enum Quadrant {
        TOP_LEFT(-1, -1),
        TOP_RIGHT(-1, 1),
        BOTTOM_LEFT(1, -1),
        BOTTOM_RIGHT(1, 1);
        
        int first;
        int second;

        private Quadrant(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
    
    public HopMove(Piece piece) {
        super(piece);
    }
    
    @Override
    public boolean isMoveAllowed(Address targetCell) {
        CellInfo.Rank srcRank = piece.getRank();
        CellInfo.File srcFile = piece.getFile();
        if(srcRank == targetCell.rank || srcFile == targetCell.file)
            return false;
        
        Address[] expectedCells;
        if(isCellAbovePiece(targetCell)) {
            if(isCellLeftOfPiece(targetCell)) {
                expectedCells = getExpectedCells(Quadrant.TOP_LEFT);
            } else {
                expectedCells = getExpectedCells(Quadrant.TOP_RIGHT);
            }
        }
        else {
            if(isCellLeftOfPiece(targetCell)) {
                expectedCells = getExpectedCells(Quadrant.BOTTOM_LEFT);
            } else {
                expectedCells = getExpectedCells(Quadrant.BOTTOM_RIGHT);
            }
        }
        if(expectedCells == null)
            return false;
        return isTargetCellExpected(expectedCells, targetCell);
    }
       
    private Address[] getExpectedCells(Quadrant magnitude) {
        Address expectedCells[] = new Address[2];
        int index = 0;
        int rank = piece.getRank().ordinal() + (2 * magnitude.first);
        int file = piece.getFile().ordinal() + (1 * magnitude.second);
        if(isRankFileValid(rank, file))
            expectedCells[index++] = new Address(CellInfo.Rank.values[rank], CellInfo.File.values[file]);
        rank = piece.getRank().ordinal() + (1 * magnitude.first);
        file = piece.getFile().ordinal() + (2 * magnitude.second);
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
