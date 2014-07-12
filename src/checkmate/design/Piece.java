/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.design;

import checkmate.Launcher;
import checkmate.event.PieceEvtHandler;
import checkmate.manager.RepetitionManager;
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
    protected char unicodeChar;
    protected final PieceInfo.Position position;
    protected static final PieceEvtHandler eventHandler = new PieceEvtHandler();
    protected IMovable moveHandler;
    protected final PieceInfo.Color color;
    private final boolean isKingSide;

    public Piece(PieceInfo.Type pieceType, PieceInfo.Position position) {
        super(pieceType.getUnicodeChar() + "");
        this.unicodeChar = pieceType.getUnicodeChar();
        this.position = position;
        setFont(getPieceFont());
        initCommonEvents();
        this.pieceType = pieceType;
        this.color = (pieceType.getUnicodeChar() < PieceInfo.Type.BLACK_KING.getUnicodeChar()) ? PieceInfo.Color.WHITE : PieceInfo.Color.BLACK;
        setInitialPosition(pieceType, position);
        RepetitionManager.getInstance().hash(pieceType, currentRank, currentFile);
        isKingSide = currentFile.ordinal() > CellInfo.File.D.ordinal();
    }
    
    /**
     * Indicates whether the piece is positioned at the king side or queen side
     * @return true if on the king side, false otherwise
     */
    public boolean isKingSide() {
        return isKingSide;
    }

    /**
     * Gets the file in which the piece is located
     * @return file of piece
     */
    public CellInfo.File getFile() {
        return currentFile;
    }

    private Font getPieceFont() {
        Font font = Font.font(Launcher.resource.getStringConfig("Piece.Font.type"), Launcher.resource.getIntConfig("Piece.Font.size"));
        if (font == null) {
            font = Font.font(Launcher.resource.getIntConfig("Piece.Font.size"));
        }
        return font;
    }

    /**
     * Gets the rank in which the piece is located
     * @return rank of piece
     */
    public CellInfo.Rank getRank() {
        return currentRank;
    }

    /**
     * Sets the position of this piece to the indicated cell position
     * @param newRank rank position
     * @param newFile file position
     */
    public void setPosition(CellInfo.Rank newRank, CellInfo.File newFile) {
        Cell cell = Launcher.board.getCell(newRank, newFile);
        cell.addPieceToCell(this);
        currentFile = newFile;
        currentRank = newRank;
        Cell newCell = Launcher.board.getCell(newRank, newFile);
        newCell.disableEventHandlers();
    }

    private void initCommonEvents() {
    }

    /**
     * Gets the type of this piece as set during board setup
     * @return the piece type
     */
    public PieceInfo.Type getPieceType() {
        return pieceType;
    }

    /**
     * Creates the event handlers for this piece responsible for handling keyboard
     * and mouse events.
     */
    protected abstract void initEventHandlers();

    /**
     * Sets the position of the piece at start-up
     * @param pieceType indicates the type of this piece
     * @param position indicates the position of this piece type
     */
    protected abstract void setInitialPosition(PieceInfo.Type pieceType, PieceInfo.Position position);

    /**
     * Retrieves the cell in which the current piece is located.
     *
     * @return Cell of the current piece
     */
    public Cell getCell() {
        return Launcher.board.getCell(currentRank, currentFile);
    }

    /**
     * Gets the moveHandler of the piece, responsible for evaluating and moving the piece.
     * @return the moveHandler of the piece
     */
    public IMovable getMoveHandler() {
        return moveHandler;
    }

    /**
     * gets the address of the cell in which the piece is located
     * @return the piece address of type Address
     */
    public Address getAddress() {
        return new Address(currentRank, currentFile);
    }

    /**
     * Indicates whether this piece is white or black
     * @return true if this piece is white, false otherwise
     */
    public boolean isWhitePiece() {
        return color == PieceInfo.Color.WHITE;
    }
    
    /**
     * Sets the rank and file positions to NULL
     * However, this does not remove the piece from its current position.
     * 
     */
    public void resetPosition() {
        currentFile = null;
        currentRank = null;
    }
}
