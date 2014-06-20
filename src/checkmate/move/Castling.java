/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.move;

import checkmate.Launcher;
import checkmate.design.King;
import checkmate.design.Piece;
import checkmate.util.Address;
import checkmate.util.CellInfo;
import java.util.ArrayList;

/**
 *
 * @author bhasme
 */
public class Castling implements IMove {

    /**
     * Piece instance that is allowed to move straight
     */
    private final King king;
    private Address cellSkippedByKing;
    private Address rookCell;
    private Address targetCell;

    /**
     * Performs validations related to Castling movements
     *
     * @param piece Piece to be validated
     */
    public Castling(Piece piece) {
        this.king = (King) piece;
    }

    @Override
    public boolean isMoveAllowed(Address targetCell) {
        this.targetCell = targetCell;
        if (isTargetAddressValid()) {
            setRookPosition();
            setCellSkippedByKing();
            if (!king.isInCheck() && isPathUnoccupied() && isPathSafe()) {
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
        boolean isRankValid = targetCell.rank == king.getRankPosition();
        boolean isFileValid = (targetCell.file == CellInfo.File.B || targetCell.file == CellInfo.File.F);
        return isRankValid && isFileValid;
    }

    /**
     * Set the skippedCell address in the class
     *
     */
    protected void setCellSkippedByKing() {
        cellSkippedByKing = new Address();
        cellSkippedByKing.rank = targetCell.rank;
        cellSkippedByKing.file = (rookCell.file.ordinal() > king.getFilePosition().ordinal())
                ? CellInfo.File.values[king.getFilePosition().ordinal() + 1]
                : CellInfo.File.values[king.getFilePosition().ordinal() - 1];
    }

    protected void setRookPosition() {
        rookCell = new Address();
        rookCell.rank = king.getRankPosition();
        rookCell.file = (targetCell.file.ordinal() > king.getFilePosition().ordinal())
                ? CellInfo.File.H : CellInfo.File.A;
    }

    protected boolean isPathSafe() {
        return isStraightPathSafe(cellSkippedByKing.rank, cellSkippedByKing.file)
                && isStraightPathSafe(king.getRankPosition(), king.getFilePosition())
                && isCrossPathSafe(cellSkippedByKing.rank, cellSkippedByKing.file)
                && isCrossPathSafe(king.getRankPosition(), king.getFilePosition());
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
        int rankChange = (king.getRankPosition() == CellInfo.Rank.ONE) ? 1 : -1;
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

    protected Address getNextCrossCell(CellInfo.Rank rank, CellInfo.File file, int rankChange, int fileChange) {
        if (rank == CellInfo.Rank.EIGHT || file == CellInfo.File.H) {
            return null;
        }
        Address address = new Address();
        address.rank = CellInfo.Rank.values[rank.ordinal() + rankChange];
        address.file = CellInfo.File.values[file.ordinal() + fileChange];
        return address;
    }

    protected boolean isOpponent(CellInfo.Rank rank, CellInfo.File file) {
        return king.isWhitePiece() != Launcher.board.getCell(rank, file).isOccupiedByWhite();
    }

    private boolean isPathUnoccupied() {
        boolean isFree = !Launcher.board.getCell(cellSkippedByKing).isOccupied()
                && !Launcher.board.getCell(targetCell).isOccupied();
        if (!isFree) {
            return isFree;
        }
        int lastFile = rookCell.file.ordinal() - 1;
        int change = (lastFile > king.getFilePosition().ordinal()) ? 1 : -1;
        int nextFile = king.getFilePosition().ordinal() + change;
        for (; nextFile <= lastFile; nextFile += change) {
            if (Launcher.board.getCell(king.getRankPosition(), CellInfo.File.values[nextFile]).isOccupied()) {
                isFree = false;
                break;
            }
        }
        return isFree;
    }
}
