/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.design;

import checkmate.Launcher;
import checkmate.manager.RepetitionManager;
import checkmate.util.Address;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.GridPane;

/**
 * The Board class is a container for all chess elements - cells and pieces
 * @author bhasme
 */
public class Board extends GridPane {

    private HashMap<PieceInfo.Type, Piece> boardPieces = new HashMap();
    private HashMap<PieceInfo.Type, Piece> defeatedPieces = new HashMap();

    public Board() {
        create();
    }

    /**
     * Creates all cells of the chess board (black, white squares) at their respective
     * rank and file positions
     */
    public final void create() {
        for (CellInfo.Rank rank : CellInfo.Rank.values()) {
            for (CellInfo.File file : CellInfo.File.values()) {
                add(new Cell(rank, file), file.ordinal(), rank.ordinal());
            }
        }
    }

    /**
     * Retrieves the cell at the given rank and file
     * @param rank Rank on the board
     * @param file File on the board
     * @return cell object
     */
    public Cell getCell(CellInfo.Rank rank, CellInfo.File file) {
        return (Cell) getChildren().get(file.ordinal() + (8 * rank.ordinal()));
    }

    /**
     * Retrieves the cell at the given address on the board
     * @param newAddress address on the board
     * @return cell object
     */
    public Cell getCell(Address newAddress) {
        return (Cell) getChildren().get(newAddress.file.ordinal() + (8 * newAddress.rank.ordinal()));
    }

    /**
     * Creates and initialized all chess pieces on the board
     */
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
        RepetitionManager.getInstance().storeHash();
    }

    /**
     * Gets a piece of the given type from the chess board
     * @param pieceType
     * @return chess piece of type design.Piece
     */
    public Piece getPiece(PieceInfo.Type pieceType) {
        if (pieceType == PieceInfo.Type.BLACK_PAWN || pieceType == PieceInfo.Type.WHITE_PAWN) {
            throw new UnsupportedOperationException("Cannot get piece of Type: " + pieceType);
        }
        return boardPieces.get(pieceType);
    }

    /**
     * Removes the given piece from the chess board. This method can be used when a piece
     * is attacked and thus needs to be removed from the board.
     * 
     * If a king is conquered, it implicitly invokes the end of game procedure.
     * @param piece Piece to be removed
     */
    public void removeFromBoard(Piece piece) {
        piece.getCell().removePieceFromCell(piece);
        defeatedPieces.put(piece.getPieceType(), boardPieces.remove(piece.getPieceType()));
        if (piece.getPieceType().getPieceName() == PieceInfo.Name.KING) {
            String message = "Game Over...";
            if (piece.getPieceType() == PieceInfo.Type.WHITE_KING) {
                message += " Black wins!";
            } else {
                message += " White wins!";
            }
            Launcher.endGameWithMsg(message);
        }
    }
}
