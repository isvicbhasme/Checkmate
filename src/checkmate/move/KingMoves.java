/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.move;

import checkmate.Launcher;
import checkmate.design.Piece;
import checkmate.design.Rook;
import checkmate.util.CellInfo;

/**
 *
 * @author bhasme
 */
public class KingMoves extends PieceMoves{
    
    private final static int MAX_MOVES = 1;
    private final static boolean HORIZONTAL_MOVE_ALLOWED = true;
    private final static boolean BACKWARD_MOVE_ALLOWED = true;
    private final Castling castling;

    public KingMoves(Piece piece) {
        this.piece = piece;
        this.castling = new Castling(piece);
        this.moveTypes.add(new Straight(piece, MAX_MOVES, HORIZONTAL_MOVE_ALLOWED, BACKWARD_MOVE_ALLOWED));
        this.moveTypes.add(new Cross(piece, MAX_MOVES));
        this.moveTypes.add(castling);
    }

    @Override
    public void moveTo(CellInfo.Rank rank, CellInfo.File file) {
        if(isCastling(file)) {
            int change = castling.getIsRightMove()? -1 : 1;
            castling.getRook().getMoveHandler().moveTo(rank, CellInfo.File.values[file.ordinal() + change]);
        }
        super.moveTo(rank, file);
    }
    
    protected boolean isCastling(CellInfo.File file) {
        return (Math.abs(piece.getFile().ordinal() - file.ordinal()) > 1);
    }
}
