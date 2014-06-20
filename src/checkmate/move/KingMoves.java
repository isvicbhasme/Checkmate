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

    public KingMoves(Piece piece) {
        this.piece = piece;
        this.moveTypes.add(new Straight(piece, MAX_MOVES, HORIZONTAL_MOVE_ALLOWED, BACKWARD_MOVE_ALLOWED));
        this.moveTypes.add(new Cross(piece, MAX_MOVES));
        this.moveTypes.add(new Castling(piece));
    }

    @Override
    public void moveTo(CellInfo.Rank rank, CellInfo.File file) {
        if(isCastling(file)) {
            Rook rook = getRook(file);
            rook.getMoveHandler().moveTo(rank, CellInfo.File.values[file.ordinal() - 1]);
        }
        super.moveTo(rank, file);
    }
    
    protected boolean isCastling(CellInfo.File file) {
        return (Math.abs(piece.getFilePosition().ordinal() - file.ordinal()) > 1);
    }
    
    protected Rook getRook(CellInfo.File file) {
        if(file.ordinal() > piece.getFilePosition().ordinal())
        {
            return (Rook) Launcher.board.getCell(piece.getRankPosition(), CellInfo.File.H).getPiece();
        } else {
            return (Rook) Launcher.board.getCell(piece.getRankPosition(), CellInfo.File.A).getPiece();
        }
    }
}
