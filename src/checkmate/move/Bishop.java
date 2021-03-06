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
public class Bishop extends MovablePiece {

    /**
     *
     * @param piece Bishop on which moves are done
     */
    public Bishop(Piece piece) {
        this.moveTypes.add(new CrossMove(piece, Integer.MAX_VALUE));
        this.piece = piece;
    }
}
