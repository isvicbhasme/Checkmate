

package checkmate.design;

import checkmate.move.IMovable;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;

/**
 * Contains design behaviors specific to a queen
 * @author Isaac
 */
public class Queen extends Piece{
    
    public Queen(PieceInfo.Type pieceType, PieceInfo.Position position) {
        super(pieceType, position);
        initEventHandlers();
        moveHandler = (IMovable) new checkmate.move.Queen(this);
    }

    @Override
    protected final void setInitialPosition(PieceInfo.Type pieceType, PieceInfo.Position position) {
        CellInfo.Rank defaultRank;
        CellInfo.File defaultFile;
        if (pieceType == PieceInfo.Type.BLACK_QUEEN) {
            defaultRank = PieceInfo.InitBlackPosition.QUEEN.getRank();
            defaultFile = PieceInfo.InitBlackPosition.QUEEN.getFile();
        } else if(pieceType == PieceInfo.Type.WHITE_QUEEN) {
            defaultRank = PieceInfo.InitWhitePosition.QUEEN.getRank();
            defaultFile = PieceInfo.InitWhitePosition.QUEEN.getFile();
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
