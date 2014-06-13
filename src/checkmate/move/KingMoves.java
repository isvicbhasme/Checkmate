/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.move;

import checkmate.design.Piece;

/**
 *
 * @author bhasme
 */
public class KingMoves extends PieceMoves{
    
    private final static int MAX_MOVES = 1;
    private final static boolean HORIZONTAL_MOVE_ALLOWED = true;
    private final static boolean BACKWARD_MOVE_ALLOWED = true;

    public KingMoves(Piece piece) {
        this.piece = piece;
        this.moveTypes.add(new Straight(piece, MAX_MOVES, HORIZONTAL_MOVE_ALLOWED, BACKWARD_MOVE_ALLOWED));
        this.moveTypes.add(new Cross(piece, MAX_MOVES));
    }
    
}
