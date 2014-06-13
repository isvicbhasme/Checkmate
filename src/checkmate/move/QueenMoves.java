/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.move;

import checkmate.design.Piece;

/**
 *
 * @author basme
 */
public class QueenMoves extends PieceMoves{
        
    private final static boolean HORIZONTAL_MOVE_ALLOWED = true;
    private final static boolean BACKWARD_MOVE_ALLOWED = true;

    public QueenMoves(Piece piece) {
        this.piece = piece;
        this.moveTypes.add(new Straight(piece, Integer.MAX_VALUE, HORIZONTAL_MOVE_ALLOWED, BACKWARD_MOVE_ALLOWED));
        this.moveTypes.add(new Cross(piece, Integer.MAX_VALUE));
    }
    
}
