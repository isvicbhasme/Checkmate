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
import checkmate.manager.RepetitionManager;
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
        if (attackedPawn != null && attackedPawn.isEnpassantPossible()) {
            setIsOnlyAttackAllowed(false); //TODO: Not a convincing method name
            isAttackAllowed = super.isMoveAllowed(targetAddress);
            if (isAttackAllowed) {
                attackedPawn.setIsEnpassantPossible(false);
                removeAttackedPawn(attackedPawn);
            }
        }
        return isAttackAllowed;
    }

    private void removeAttackedPawn(Pawn attackedPiece) {
        RepetitionManager.getInstance().hash(attackedPiece.getPieceType(), attackedPiece.getAddress());
        attackedPiece.getCell().removePieceFromCellGroup((Piece) attackedPiece);
        Launcher.board.removeFromBoard(attackedPiece);
    }

    private Pawn getAttackedPawn(Address targetAddress) {
        Cell attackedPawnCell = Launcher.board.getCell(piece.getRank(), targetAddress.file);
        return (Pawn) attackedPawnCell.getPiece();
    }
}
