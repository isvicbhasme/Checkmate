package checkmate.design;

import checkmate.Launcher;
import checkmate.move.IMovable;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;
import java.io.IOException;

/**
 * Contains design behaviors specific to a rook
 * @author bhasme
 */
public class Rook extends Piece {

    protected boolean isFirstMove;

    /**
     *
     * @param pieceType Enum specifying the type of chess piece
     * @param position Enum specifying the position of chess piece
     * @throws IOException
     */
    public Rook(PieceInfo.Type pieceType, PieceInfo.Position position) throws IOException {
        super(pieceType, position);
        initEventHandlers();
        moveHandler = (IMovable) new checkmate.move.Rook(this);
    }

    /**
     * Sets the initial position of a chess piece during startup
     *
     * @param pieceType Enum specifying the type of chess piece
     * @param position Enum specifying the position of chess piece
     */
    @Override
    protected final void setInitialPosition(PieceInfo.Type pieceType, PieceInfo.Position position) {
        CellInfo.Rank defaultRank;
        CellInfo.File defaultFile;
        if (pieceType == PieceInfo.Type.BLACK_ROOK) {
            defaultRank = position == PieceInfo.Position.LEFT ? PieceInfo.InitBlackPosition.ROOK_LEFT.getRank()
                    : PieceInfo.InitBlackPosition.ROOK_RIGHT.getRank();

            defaultFile = position == PieceInfo.Position.LEFT ? PieceInfo.InitBlackPosition.ROOK_LEFT.getFile()
                    : PieceInfo.InitBlackPosition.ROOK_RIGHT.getFile();
        } else if (pieceType == PieceInfo.Type.WHITE_ROOK) {
            defaultRank = position == PieceInfo.Position.LEFT ? PieceInfo.InitWhitePosition.ROOK_LEFT.getRank()
                    : PieceInfo.InitWhitePosition.ROOK_RIGHT.getRank();

            defaultFile = position == PieceInfo.Position.LEFT ? PieceInfo.InitWhitePosition.ROOK_LEFT.getFile()
                    : PieceInfo.InitWhitePosition.ROOK_RIGHT.getFile();
        } else {
            throw new IllegalStateException("Illegal piece creation");
        }
        setPosition(defaultRank, defaultFile);
        isFirstMove = true;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    /**
     * Initialize the event handlers for mouse and keyboard.
     */
    @Override
    protected final void initEventHandlers() {
        setOnMouseClicked(eventHandler::handleMouseEvent);
        setOnKeyPressed(eventHandler::handleKeyEvent);
    }

    @Override
    public void setPosition(CellInfo.Rank newRank, CellInfo.File newFile) {
        super.setPosition(newRank, newFile);
        if (isFirstMove()) {
            disableCastlingHash();
            isFirstMove = false;
        }
    }

    private void disableCastlingHash() {
        Piece king = isWhitePiece()? Launcher.board.getPiece(PieceInfo.Type.WHITE_KING) : Launcher.board.getPiece(PieceInfo.Type.BLACK_KING);
        if(isKingSide()) {
            ((King)king).disableKingSideCastleHash();
        }
        else {
            ((King)king).disableQueenSideCastleHash();
        }
    }
}
