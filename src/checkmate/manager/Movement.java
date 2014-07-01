/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.manager;

import checkmate.util.Address;
import checkmate.util.PieceInfo;

/**
 *
 * @author Isaac
 */
public class Movement {

    Address from;
    Address to;
    PieceInfo.Type pieceName;

    public Movement() {
    }

    public Movement(Address from, Address to, PieceInfo.Type pieceName) {
        this.from = from;
        this.to = to;
        this.pieceName = pieceName;
    }
}
