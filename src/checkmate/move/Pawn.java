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

    public Pawn(Piece piece) {
        this.piece = piece;
        straightMove = new StraightMove(piece, maxMoves, HORIZONTAL_MOVE_ALLOWED, BACKWARD_MOVE_ALLOWED);
        straightMove.setIsAttackingAllowed(false);
        crossMove = new CrossMove(piece, 1);
        crossMove.setIsOnlyAttackAllowed(true);
        crossMove.setIsBackwardMoveRestricted(true);
        this.moveTypes.add(straightMove);
        this.moveTypes.add(crossMove);
    }

    @Override
    public void moveTo(CellInfo.Rank rank, CellInfo.File file) {
        super.moveTo(rank, file);
        this.maxMoves = 1;
        straightMove.setMaxSteps(maxMoves);
    }
    
    
}
