/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.design;

import checkmate.event.RookEvtHandler;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;
import java.io.IOException;

/**
 *
 * @author bhasme
 */
public class Rook extends Piece {

    protected RookEvtHandler rookHandler;

    public Rook(PieceInfo.Type pieceType, PieceInfo.Position position) throws IOException {
        super(pieceType, position);
        rookHandler = new RookEvtHandler();
        setInitialPosition(pieceType, position);
        initEventHandlers();
    }

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
    }

    @Override
    protected final void initEventHandlers() {
        setOnMouseClicked(rookHandler::handleMouseEvent);
        setOnKeyPressed(rookHandler::handleKeyEvent);
    }

    @Override
    public boolean isMoveAllowed(CellInfo.Rank toRank, CellInfo.File toFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
