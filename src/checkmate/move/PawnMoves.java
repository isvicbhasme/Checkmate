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
public class PawnMoves extends PieceMoves{
    
    private int maxMoves = 2;
    private final static boolean HORIZONTAL_MOVE_ALLOWED = false;
    private final static boolean BACKWARD_MOVE_ALLOWED = false;
    private final Straight straightMove;

    public PawnMoves(Piece piece) {
        this.piece = piece;
        straightMove = new Straight(piece, maxMoves, HORIZONTAL_MOVE_ALLOWED, BACKWARD_MOVE_ALLOWED);
        this.moveTypes.add(straightMove);
    }

    @Override
    public void moveTo(CellInfo.Rank rank, CellInfo.File file) {
        super.moveTo(rank, file);
        this.maxMoves = 1;
        straightMove.setMaxSteps(maxMoves);
    }
    
    
}
