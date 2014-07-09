/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.move;

import checkmate.Launcher;
import checkmate.design.Cell;
import checkmate.design.Piece;
import checkmate.design.Pawn;
import checkmate.util.Address;

/**
 *
 * @author Isaac
 */
public class EnPassantMove extends CrossMove {

    private final static int MAX_STEPS = 1;

    public EnPassantMove(Piece piece) {
        super(piece, MAX_STEPS);
    }

    @Override
    public boolean isMoveAllowed(Address targetAddress) {
        boolean isAttackAllowed = false;
        Pawn attackedPawn = getAttackedPawn(targetAddress);
        if (attackedPawn != null && attackedPawn.isEnpassantPossible() && attackedPawn.isWhitePiece() != piece.isWhitePiece()) {
            isAttackAllowed = super.isMoveAllowed(targetAddress);
        }
        return isAttackAllowed;
    }

    private Pawn getAttackedPawn(Address targetAddress) {
        try {
            Cell attackedPawnCell = Launcher.board.getCell(piece.getRank(), targetAddress.file);
            return (Pawn) attackedPawnCell.getPiece();
        } catch (ClassCastException e) {
            return null;
        }
    }
}
