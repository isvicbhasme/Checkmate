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
public class Knight extends MovablePiece{

    public Knight(Piece piece) {
        this.piece = piece;
        this.moveTypes.add(new HopMove(piece));
    }
    
}
