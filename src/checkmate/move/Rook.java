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
public class Rook extends MovablePiece {
    
    private final static boolean HORIZONTAL_MOVE_ALLOWED = true;
    private final static boolean BACKWARD_MOVE_ALLOWED = true;

    /**
     *
     * @param piece Rook on which moves are done
     */
    public Rook(Piece piece) {
        this.moveTypes.add(new StraightMove(piece, Integer.MAX_VALUE, HORIZONTAL_MOVE_ALLOWED, BACKWARD_MOVE_ALLOWED));
        this.piece = piece;
    }

}
