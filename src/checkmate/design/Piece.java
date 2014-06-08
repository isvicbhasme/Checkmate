/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.design;

import checkmate.Launcher;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author bhasme
 */
public abstract class Piece extends Text implements IMovable {

    protected CellInfo.File currentFile;
    protected CellInfo.Rank currentRank;
    protected PieceInfo.Type pieceType;
    protected boolean isMouseDown;
    protected final char unicodeString;
    protected final PieceInfo.Position position;

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

    protected void setPosition(CellInfo.Rank newRank, CellInfo.File newFile) {
        Cell cell = Launcher.board.getCell(newRank, newFile);
        cell.addPieceToCellGroup(this);
        currentFile = newFile;
        currentRank = newRank;
        Cell newCell = Launcher.board.getCell(newRank, newFile);
        newCell.disableEventHandlers();
    }

    /**
     * Moves the piece to the specified cell. Note that the validity of the movement is not checked by this method.
     * @param newRank Rank of the target cell
     * @param newFile File of the target cell
     */
    @Override
    public void moveTo(CellInfo.Rank newRank, CellInfo.File newFile) {
        Cell currentCell = Launcher.board.getCell(currentRank, currentFile);
        currentCell.removePieceFromCellGroup(this);
        currentCell.enableEventHandlers();
        setPosition(newRank, newFile);
    }

    private void initCommonEvents() {
    }

    public PieceInfo.Type getPieceType() {
        return pieceType;
    }

    protected abstract void initEventHandlers();

    protected abstract void setInitialPosition(PieceInfo.Type pieceType, PieceInfo.Position position);

    /**
     * Checks if movement is allowed to an empty cell specified by the parameters
     * @param toRank Rank of the target cell
     * @param toFile File of the target cell
     * @return TRUE if the movement is valid, FALSE otherwise
     */
    public abstract boolean isMoveAllowed(CellInfo.Rank toRank, CellInfo.File toFile);
}
