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
            new Rook(PieceInfo.Type.BLACK_ROOK, PieceInfo.RelativePosition.LEFT);
            new Knight(PieceInfo.Type.BLACK_KNIGHT, PieceInfo.RelativePosition.LEFT);
            new Bishop(PieceInfo.Type.BLACK_BISHOP, PieceInfo.RelativePosition.LEFT);
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
