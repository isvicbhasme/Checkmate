/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.move;

import checkmate.Launcher;
import checkmate.design.King;
import checkmate.design.Piece;
import checkmate.design.Rook;
import checkmate.util.Address;
import checkmate.util.CellInfo;
import java.util.ArrayList;

/**
 *
 * @author bhasme
 */
public class CastlingMove extends Moves implements IMove{

    private Address cellSkippedByKing;
    private Address targetCell;
    private Rook rook;
    private boolean isRightMove;

    /**
     * Performs validations related to Castling movements
     *
     * @param piece Piece to be validated
     */
    public CastlingMove(Piece piece) {
        super(piece);
    }

    @Override
    public boolean isMoveAllowed(Address targetCell) {
        this.targetCell = targetCell;
        if (isTargetAddressValid()) {
            setIsRightMove(targetCell.file);
            setRook(targetCell.file);
            setCellSkippedByKing();
            if (isInitialMove() && !((King)piece).isInCheck() && isPathUnoccupied() && isPathSafe()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the target cell is a proper cell to which castling can be
     * done
     *
     * @return true if the targetCell is valid for castling, false otherwise
     */
    protected boolean isTargetAddressValid() {
        boolean isRankValid = targetCell.rank == piece.getRank();
        boolean isFileValid = (targetCell.file == CellInfo.File.C || targetCell.file == CellInfo.File.G);
        return isRankValid && isFileValid;
    }

    /**
     * Set the skippedCell address in the class
     *
     */
    protected void setCellSkippedByKing() {
        cellSkippedByKing = new Address();
        cellSkippedByKing.rank = targetCell.rank;
        cellSkippedByKing.file = getIsRightMove()
                ? CellInfo.File.values[piece.getFile().ordinal() + 1]
                : CellInfo.File.values[piece.getFile().ordinal() - 1];
    }

    protected boolean isPathSafe() {
        return isStraightPathSafe(cellSkippedByKing.rank, cellSkippedByKing.file)
                && isStraightPathSafe(piece.getRank(), piece.getFile())
                && isCrossPathSafe(cellSkippedByKing.rank, cellSkippedByKing.file)
                && isCrossPathSafe(piece.getRank(), piece.getFile());
    }

    protected boolean isStraightPathSafe(CellInfo.Rank kingRank, CellInfo.File kingFile) {
        CellInfo.File file = kingFile;
        CellInfo.Rank rank = kingRank;
        int change = (rank == CellInfo.Rank.ONE) ? 1 : -1;
        int index = 0;
        boolean isSafe = true;
        for (index = 1; index < 8; index++, rank = CellInfo.Rank.values[rank.ordinal() + change]) {
            if (Launcher.board.getCell(rank, file).isOccupied()) {
                if (isOpponent(rank, file)) {
                    Piece piece = Launcher.board.getCell(rank, file).getPiece();
                    if (piece.getMoveHandler().isMovePermitted(kingRank, kingFile)) {
                        isSafe = false;
                    }
                } else {
                    isSafe = true;
                }
                break;
            }
        }
        return isSafe;
    }

    protected boolean isCrossPathSafe(CellInfo.Rank kingRank, CellInfo.File kingFile) {
        ArrayList<Address> positions = new ArrayList<>();
        Address newPosition;
        boolean isSafe = true;
        int rankChange = (piece.getRank() == CellInfo.Rank.ONE) ? 1 : -1;
        int fileChange = -1;
        Outer:
        for (int i = 0; i < 2; i++) {
            positions.clear();
            fileChange = fileChange == -1 ? 1 : -1;
            CellInfo.Rank rank = kingRank;
            CellInfo.File file = kingFile;
            while ((newPosition = getNextCrossCell(rank, file, rankChange, fileChange)) != null) {
                positions.add(newPosition);
                rank = newPosition.rank;
                file = newPosition.file;
            }
            for (Address position : positions) {
                if (Launcher.board.getCell(position.rank, position.file).isOccupied()) {
                    if (isOpponent(position.rank, position.file)) {
                        Piece piece = Launcher.board.getCell(position.rank, position.file).getPiece();
                        if (piece.getMoveHandler().isMovePermitted(kingRank, kingFile)) {
                            isSafe = false;
                            break Outer;
                        }
                    } else {
                        isSafe = true;
                    }
                    break;
                }
            }
        }
        return isSafe;
    }

    protected Address getNextCrossCell(CellInfo.Rank currentRank, CellInfo.File currentFile, int rankChange, int fileChange) {
        if (currentFile == CellInfo.File.A || currentFile == CellInfo.File.H) {
            return null;
        }
        Address address = new Address();
        address.rank = CellInfo.Rank.values[currentRank.ordinal() + rankChange];
        address.file = CellInfo.File.values[currentFile.ordinal() + fileChange];
        return address;
    }


    private boolean isPathUnoccupied() {
        boolean isFree = !Launcher.board.getCell(cellSkippedByKing).isOccupied()
                && !Launcher.board.getCell(targetCell).isOccupied();
        if (!isFree) {
            return isFree;
        }
        int lastFile = rook.getFile().ordinal() - 1;
        int change = getIsRightMove()? 1 : -1;
        int nextFile = piece.getFile().ordinal() + change;
        for (; nextFile <= lastFile; nextFile += change) {
            if (Launcher.board.getCell(piece.getRank(), CellInfo.File.values[nextFile]).isOccupied()) {
                isFree = false;
                break;
            }
        }
        return isFree;
    }
    
    protected void setRook(CellInfo.File kingsTarget) {
        if(getIsRightMove())
        {
            this.rook = (Rook) Launcher.board.getCell(piece.getRank(), CellInfo.File.H).getPiece();
        } else {
            this.rook = (Rook) Launcher.board.getCell(piece.getRank(), CellInfo.File.A).getPiece();
        }
    }
    
    Rook getRook() {
        return this.rook;
    }

    private boolean isInitialMove() {
        return ((King)piece).isFirstMove() && rook!= null && rook.isFirstMove();
    }
    
    private void setIsRightMove(CellInfo.File kingsTargetFile) {
        this.isRightMove = (kingsTargetFile.ordinal() > piece.getFile().ordinal());
    }
    
    protected boolean getIsRightMove() {
        return this.isRightMove;
    }
}
