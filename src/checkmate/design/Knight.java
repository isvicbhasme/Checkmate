
package checkmate.design;

import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;

/**
 * Contains design behaviors specific to a knight
 * @author bhasme
 */
public class Knight extends Piece {

    public Knight(PieceInfo.Type pieceType, PieceInfo.Position position) {
        super(pieceType, position);
        initEventHandlers();
        moveHandler = new checkmate.move.Knight(this);
    }

    @Override
    protected final void setInitialPosition(PieceInfo.Type pieceType, PieceInfo.Position position) {
        CellInfo.Rank defaultRank;
        CellInfo.File defaultFile;
        if (pieceType == PieceInfo.Type.BLACK_KNIGHT) {
            defaultRank = position == PieceInfo.Position.LEFT ? PieceInfo.InitBlackPosition.KNIGHT_LEFT.getRank()
                    : PieceInfo.InitBlackPosition.KNIGHT_RIGHT.getRank();

            defaultFile = position == PieceInfo.Position.LEFT ? PieceInfo.InitBlackPosition.KNIGHT_LEFT.getFile()
                    : PieceInfo.InitBlackPosition.KNIGHT_RIGHT.getFile();
        } else if(pieceType == PieceInfo.Type.WHITE_KNIGHT) {
            defaultRank = position == PieceInfo.Position.LEFT ? PieceInfo.InitWhitePosition.KNIGHT_LEFT.getRank()
                    : PieceInfo.InitWhitePosition.KNIGHT_RIGHT.getRank();

            defaultFile = position == PieceInfo.Position.LEFT ? PieceInfo.InitWhitePosition.KNIGHT_LEFT.getFile()
                    : PieceInfo.InitWhitePosition.KNIGHT_RIGHT.getFile();
        } else {
            throw new IllegalStateException("Illegal piece creation");
        }
        setPosition(defaultRank, defaultFile);
    }

    @Override
    protected final void initEventHandlers() {
        setOnMouseClicked(eventHandler::handleMouseEvent);
        setOnKeyPressed(eventHandler::handleKeyEvent);
    }

}
