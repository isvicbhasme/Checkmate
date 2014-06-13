/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.design;

import checkmate.Launcher;
import checkmate.event.PieceEvtHandler;
import checkmate.move.IMovable;
import checkmate.util.Address;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author bhasme
 */
public abstract class Piece extends Text {

    protected CellInfo.File currentFile;
    protected CellInfo.Rank currentRank;
    protected PieceInfo.Type pieceType;
    protected final char unicodeString;
    protected final PieceInfo.Position position;
    protected static final PieceEvtHandler eventHandler = new PieceEvtHandler();
    protected IMovable moveHandler;

    public Piece(PieceInfo.Type pieceType, PieceInfo.Position position) {
        super(pieceType.getUnicodeChar() + "");
        this.unicodeString = pieceType.getUnicodeChar();
        this.position = position;
        setFont(getPieceFont());
        initCommonEvents();
        this.pieceType = pieceType;
    }

    public CellInfo.File getFilePosition() {
        return currentFile;
    }

    private Font getPieceFont() {
        Font font = Font.font(PieceInfo.FontInfo.TYPE.data, Double.parseDouble(PieceInfo.FontInfo.SIZE.data));
        if (font == null) {
            font = Font.font(Double.parseDouble(PieceInfo.FontInfo.SIZE.data));
        }
        return font;
    }

    public CellInfo.Rank getRankPosition() {
        return currentRank;
    }

    public void setPosition(CellInfo.Rank newRank, CellInfo.File newFile) {
        Cell cell = Launcher.board.getCell(newRank, newFile);
        cell.addPieceToCellGroup(this);
        currentFile = newFile;
        currentRank = newRank;
        Cell newCell = Launcher.board.getCell(newRank, newFile);
        newCell.disableEventHandlers();
    }

    private void initCommonEvents() {
    }

    public PieceInfo.Type getPieceType() {
        return pieceType;
    }

    protected abstract void initEventHandlers();

    protected abstract void setInitialPosition(PieceInfo.Type pieceType, PieceInfo.Position position);

    /**
     * Retrieves the cell in which the current piece is located.
     * @return Cell of the current piece
     */
    public Cell getCell() {
        return Launcher.board.getCell(currentRank, currentFile);
    }

    public IMovable getMoveHandler() {
        return moveHandler;
    }
    
    public Address getAddress() {
        return new Address(currentRank, currentFile);
    }
    
    public boolean isWhitePiece() {
        return pieceType.getUnicodeChar() < pieceType.BLACK_KING.getUnicodeChar();
    }
}
