/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.design;

import checkmate.event.BishopEvtHandler;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;

/**
 *
 * @author Isaac
 */
public final class Bishop extends Piece{
    
    protected BishopEvtHandler bishopHandler;

    public Bishop(PieceInfo.Type pieceType, PieceInfo.Position position) {
        super(pieceType, position);
        bishopHandler = new BishopEvtHandler();
        setInitialPosition(pieceType, position);
        initEventHandlers();
    }

    @Override
    protected final void setInitialPosition(PieceInfo.Type pieceType, PieceInfo.Position position) {
        CellInfo.Rank defaultRank;
        CellInfo.File defaultFile;
        if (pieceType == PieceInfo.Type.BLACK_BISHOP) {
            defaultRank = position == PieceInfo.Position.LEFT ? PieceInfo.InitBlackPosition.BISHOP_LEFT.getRank()
                    : PieceInfo.InitBlackPosition.BISHOP_RIGHT.getRank();

            defaultFile = position == PieceInfo.Position.LEFT ? PieceInfo.InitBlackPosition.BISHOP_LEFT.getFile()
                    : PieceInfo.InitBlackPosition.BISHOP_RIGHT.getFile();
        } else if(pieceType == PieceInfo.Type.WHITE_BISHOP) {
            defaultRank = position == PieceInfo.Position.LEFT ? PieceInfo.InitWhitePosition.BISHOP_LEFT.getRank()
                    : PieceInfo.InitWhitePosition.BISHOP_RIGHT.getRank();

            defaultFile = position == PieceInfo.Position.LEFT ? PieceInfo.InitWhitePosition.BISHOP_LEFT.getFile()
                    : PieceInfo.InitWhitePosition.BISHOP_RIGHT.getFile();
        } else {
            throw new IllegalStateException("Illegal piece creation");
        }
        setPosition(defaultRank, defaultFile);
    }

    @Override
    protected final void initEventHandlers() {
        setOnMouseClicked(bishopHandler::handleMouseEvent);
        setOnKeyPressed(bishopHandler::handleKeyEvent);
    }
    
}
