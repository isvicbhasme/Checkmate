/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.design;

import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.GridPane;

/**
 *
 * @author bhasme
 */
public class Board extends GridPane {

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

    public void createPieces() {
        try {
            new Rook(PieceInfo.Type.BLACK_ROOK, PieceInfo.Position.LEFT);
            new Knight(PieceInfo.Type.BLACK_KNIGHT, PieceInfo.Position.LEFT);
            new Bishop(PieceInfo.Type.BLACK_BISHOP, PieceInfo.Position.LEFT);
            new King(PieceInfo.Type.BLACK_KING, PieceInfo.Position.CENTER);
            new Queen(PieceInfo.Type.BLACK_QUEEN, PieceInfo.Position.CENTER);
            new Bishop(PieceInfo.Type.BLACK_BISHOP, PieceInfo.Position.RIGHT);
            new Knight(PieceInfo.Type.BLACK_KNIGHT, PieceInfo.Position.RIGHT);
            new Rook(PieceInfo.Type.BLACK_ROOK, PieceInfo.Position.RIGHT);
            new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.ONE);
            new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.TWO);
            new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.THREE);
            new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.FOUR);
            new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.FIVE);
            new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.SIX);
            new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.SEVEN);
            new Pawn(PieceInfo.Type.BLACK_PAWN, PieceInfo.Position.EIGHT);
            
            new Rook(PieceInfo.Type.WHITE_ROOK, PieceInfo.Position.LEFT);
            new Knight(PieceInfo.Type.WHITE_KNIGHT, PieceInfo.Position.LEFT);
            new Bishop(PieceInfo.Type.WHITE_BISHOP, PieceInfo.Position.LEFT);
            new King(PieceInfo.Type.WHITE_KING, PieceInfo.Position.CENTER);
            new Queen(PieceInfo.Type.WHITE_QUEEN, PieceInfo.Position.CENTER);
            new Bishop(PieceInfo.Type.WHITE_BISHOP, PieceInfo.Position.RIGHT);
            new Knight(PieceInfo.Type.WHITE_KNIGHT, PieceInfo.Position.RIGHT);
            new Rook(PieceInfo.Type.WHITE_ROOK, PieceInfo.Position.RIGHT);
            new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.ONE);
            new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.TWO);
            new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.THREE);
            new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.FOUR);
            new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.FIVE);
            new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.SIX);
            new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.SEVEN);
            new Pawn(PieceInfo.Type.WHITE_PAWN, PieceInfo.Position.EIGHT);
            
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
