package checkmate.design;

import checkmate.manager.RepetitionManager;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;

/**
 *
 * @author Isaac
 */
public class King extends Piece {

    protected boolean isFirstMove;
    protected boolean isInCheck;
    private boolean isKingSideCastlePossible = true;
    private boolean isQueenSideCastlePossible = true;

    public King(PieceInfo.Type pieceType, PieceInfo.Position position) {
        super(pieceType, position);
        initEventHandlers();
        this.moveHandler = new checkmate.move.King(this);
        this.isInCheck = false;
    }

    @Override
    protected final void setInitialPosition(PieceInfo.Type pieceType, PieceInfo.Position position) {
        CellInfo.Rank defaultRank;
        CellInfo.File defaultFile;
        if (pieceType == PieceInfo.Type.BLACK_KING) {
            defaultRank = PieceInfo.InitBlackPosition.KING.getRank();
            defaultFile = PieceInfo.InitBlackPosition.KING.getFile();
        } else if (pieceType == PieceInfo.Type.WHITE_KING) {
            defaultRank = PieceInfo.InitWhitePosition.KING.getRank();
            defaultFile = PieceInfo.InitWhitePosition.KING.getFile();
        } else {
            throw new IllegalStateException("Illegal piece creation");
        }
        setPosition(defaultRank, defaultFile);
        isFirstMove = true;
    }

    @Override
    protected final void initEventHandlers() {
        setOnMouseClicked(eventHandler::handleMouseEvent);
        setOnKeyPressed(eventHandler::handleKeyEvent);
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    @Override
    public void setPosition(CellInfo.Rank newRank, CellInfo.File newFile) {
        super.setPosition(newRank, newFile);
        if (isFirstMove()) {
            removeCastlingHashkeys();
            isKingSideCastlePossible = false;
            isQueenSideCastlePossible = false;
            isFirstMove = false;
        }
    }

    private void removeCastlingHashkeys() {
        disableKingSideCastleHash();
        disableQueenSideCastleHash();
    }
    
    public void disableKingSideCastleHash() {
        if(isWhitePiece())
            RepetitionManager.getInstance().hashCastlingRights(PieceInfo.CastlingSide.WHITE_KING_SIDE);
        else
            RepetitionManager.getInstance().hashCastlingRights(PieceInfo.CastlingSide.BLACK_KING_SIDE);
        isKingSideCastlePossible = false;
    }
    
    public void disableQueenSideCastleHash() {
        if(isWhitePiece())
            RepetitionManager.getInstance().hashCastlingRights(PieceInfo.CastlingSide.WHITE_QUEEN_SIDE);
        else
            RepetitionManager.getInstance().hashCastlingRights(PieceInfo.CastlingSide.BLACK_QUEEN_SIDE);
        isQueenSideCastlePossible = false;
    }

    public void setInCheck(boolean inCheck) {
        this.isInCheck = inCheck;
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }
}
