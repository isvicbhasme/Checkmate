/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.move;

import checkmate.design.Piece;

/**
 *
 * @author Isaac
 */
public class RookMoves extends PieceMoves {

    /**
     *
     * @param piece Rook on which moves are done
     */
    public RookMoves(Piece piece) {
        this.moveTypes.add(new Straight(piece, Integer.MAX_VALUE, true));
        this.piece = piece;
    }

}
