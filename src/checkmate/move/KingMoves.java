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

    public KingMoves(Piece piece) {
        this.piece = piece;
        this.moveTypes.add(new Straight(piece, 1, true));
        this.moveTypes.add(new Cross(piece, 1));
    }
    
}
