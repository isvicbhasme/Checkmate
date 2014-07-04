/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.move;

import checkmate.design.Piece;
import checkmate.util.CellInfo;

/**
 *
 * @author Isaac
 */
public class Pawn extends MovablePiece{
    
    private int maxMoves = 2;
    private final static boolean HORIZONTAL_MOVE_ALLOWED = false;
    private final static boolean BACKWARD_MOVE_ALLOWED = false;
    private final StraightMove straightMove;
    private final CrossMove crossMove;
    private final EnPassantMove enPassantMove;

    public Pawn(Piece piece) {
        this.piece = piece;
        straightMove = new StraightMove(piece, maxMoves, HORIZONTAL_MOVE_ALLOWED, BACKWARD_MOVE_ALLOWED);
        straightMove.setIsAttackingAllowed(false);
        crossMove = new CrossMove(piece, 1);
        crossMove.setIsOnlyAttackAllowed(true);
        crossMove.setIsBackwardMoveRestricted(true);
        enPassantMove = new EnPassantMove(piece);
        this.moveTypes.add(straightMove);
        this.moveTypes.add(crossMove);
        this.moveTypes.add(enPassantMove);
    }

    @Override
    public void moveTo(CellInfo.Rank rank, CellInfo.File file) {
        if(Math.abs(piece.getRank().ordinal() - rank.ordinal()) == 2) {
            ((checkmate.design.Pawn)piece).setIsEnpassantPossible(true);
        } else if (((checkmate.design.Pawn)piece).isEnpassantPossible()) {
            ((checkmate.design.Pawn)piece).setIsEnpassantPossible(false);
        }
        super.moveTo(rank, file);
        this.maxMoves = 1;
        straightMove.setMaxSteps(maxMoves);
    }
    
    
}
