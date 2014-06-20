/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.design;

import checkmate.util.Address;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.GridPane;

/**
 *
 * @author bhasme
 */
public class Board extends GridPane {

    HashMap<PieceInfo.Type, Piece> boardPieces = new HashMap();

    public Board() {
        create();
    }

    public final void create() {
        for (CellInfo.Rank rank : CellInfo.Rank.values()) {
            for (CellInfo.File file : CellInfo.File.values()) {
                add(new Cell(rank, file), file.ordinal(), rank.ordinal());
            }
        }
    }

    public Cell getCell(CellInfo.Rank rank, CellInfo.File file) {
        return (Cell) getChildren().get(file.ordinal() + (8 * rank.ordinal()));
    }

    public Cell getCell(Address newAddress) {
        return (Cell) getChildren().get(newAddress.file.ordinal() + (8 * newAddress.rank.ordinal()));
    }

    public void createPieces() {
        try {
            boardPieces.put(PieceInfo.Type.BLACK_ROOK, new Rook(PieceInfo.Type.BLACK_ROOK, PieceInfo.Position.LEFT));
            boardPieces.put(PieceInfo.Type.BLACK_KNIGHT, new Knight(PieceInfo.Type.BLACK_KNIGHT, PieceInfo.Position.LEFT));
            boardPieces.put(PieceInfo.Type.BLACK_BISHOP, new Bishop(PieceInfo.Type.BLACK_BISHOP, PieceInfo.Position.LEFT));
            boardPieces.put(PieceInfo.Type.BLACK_KING, new King(PieceInfo.Type.BLACK_KING, PieceInfo.Position.CENTER));
            boardPieces.put(PieceInfo.Type.BLACK_QUEEN, new Queen(PieceInfo.Type.BLACK_QUEEN, PieceInfo.Position.CENTER));
            boardPieces.put(PieceInfo.Type.BLACK_BISHOP, new Bishop(PieceInfo.Type.BLACK_BISHOP, PieceInfo.Position.RIGHT));
            boardPieces.put(PieceInfo.Type.BLACK_KNIGHT, new Knight(PieceInfo.Type.BLACK_KNIGHT, PieceInfo.Position.RIGHT));
            boardPieces.put(PieceInfo.Type.BLACK_ROOK, new Rook(PieceInfo.Type.BLACK_ROOK, PieceInfo.Position.RIGHT));
            boardPieces.put(PieceInfo.Type.BLACK_PAWN, new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.ONE));
            boardPieces.put(PieceInfo.Type.BLACK_PAWN, new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.TWO));
            boardPieces.put(PieceInfo.Type.BLACK_PAWN, new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.THREE));
            boardPieces.put(PieceInfo.Type.BLACK_PAWN, new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.FOUR));
            boardPieces.put(PieceInfo.Type.BLACK_PAWN, new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.FIVE));
            boardPieces.put(PieceInfo.Type.BLACK_PAWN, new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.SIX));
            boardPieces.put(PieceInfo.Type.BLACK_PAWN, new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.SEVEN));
            boardPieces.put(PieceInfo.Type.BLACK_PAWN, new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.EIGHT));
            
            boardPieces.put(PieceInfo.Type.WHITE_ROOK, new Rook(PieceInfo.Type.WHITE_ROOK, PieceInfo.Position.LEFT));
            boardPieces.put(PieceInfo.Type.WHITE_KNIGHT, new Knight(PieceInfo.Type.WHITE_KNIGHT, PieceInfo.Position.LEFT));
            boardPieces.put(PieceInfo.Type.WHITE_BISHOP, new Bishop(PieceInfo.Type.WHITE_BISHOP, PieceInfo.Position.LEFT));
            boardPieces.put(PieceInfo.Type.WHITE_KING, new King(PieceInfo.Type.WHITE_KING, PieceInfo.Position.CENTER));
            boardPieces.put(PieceInfo.Type.WHITE_QUEEN, new Queen(PieceInfo.Type.WHITE_QUEEN, PieceInfo.Position.CENTER));
            boardPieces.put(PieceInfo.Type.WHITE_BISHOP, new Bishop(PieceInfo.Type.WHITE_BISHOP, PieceInfo.Position.RIGHT));
            boardPieces.put(PieceInfo.Type.WHITE_KNIGHT, new Knight(PieceInfo.Type.WHITE_KNIGHT, PieceInfo.Position.RIGHT));
            boardPieces.put(PieceInfo.Type.WHITE_ROOK, new Rook(PieceInfo.Type.WHITE_ROOK, PieceInfo.Position.RIGHT));
            boardPieces.put(PieceInfo.Type.WHITE_PAWN, new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.ONE));
            boardPieces.put(PieceInfo.Type.WHITE_PAWN, new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.TWO));
            boardPieces.put(PieceInfo.Type.WHITE_PAWN, new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.THREE));
            boardPieces.put(PieceInfo.Type.WHITE_PAWN, new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.FOUR));
            boardPieces.put(PieceInfo.Type.WHITE_PAWN, new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.FIVE));
            boardPieces.put(PieceInfo.Type.WHITE_PAWN, new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.SIX));
            boardPieces.put(PieceInfo.Type.WHITE_PAWN, new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.SEVEN));
            boardPieces.put(PieceInfo.Type.WHITE_PAWN, new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.EIGHT));

        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
