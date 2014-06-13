/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.design;

import checkmate.move.KingMoves;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;

/**
 *
 * @author Isaac
 */
public class King extends Piece {

    public King(PieceInfo.Type pieceType, PieceInfo.Position position) {
        super(pieceType, position);
        setInitialPosition(pieceType, position);
        initEventHandlers();
        this.moveHandler = new KingMoves(this);
    }

    @Override
    protected final void setInitialPosition(PieceInfo.Type pieceType, PieceInfo.Position position) {
        CellInfo.Rank defaultRank;
        CellInfo.File defaultFile;
        if (pieceType == PieceInfo.Type.BLACK_KING) {
            defaultRank = PieceInfo.InitBlackPosition.KING.getRank();
            defaultFile = PieceInfo.InitBlackPosition.KING.getFile();
        } else if(pieceType == PieceInfo.Type.WHITE_KING) {
            defaultRank = PieceInfo.InitWhitePosition.KING.getRank();
            defaultFile = PieceInfo.InitWhitePosition.KING.getFile();
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
