/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.design;

import checkmate.event.KnightEvtHandler;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;

/**
 *
 * @author bhasme
 */
public class Knight extends Piece{
    
    public static final char unicodeString = '\u2658';
    protected KnightEvtHandler knightHandler; 

    public Knight(PieceInfo.Color color, PieceInfo.Type pieceType) {
        super(unicodeString, color, pieceType);
        knightHandler = new KnightEvtHandler(this);
        setInitialPosition(color, pieceType);
        initEventHandlers();
    }

    @Override
    protected final void setInitialPosition(PieceInfo.Color color, PieceInfo.Type pieceType) {
        CellInfo.Rank defaultRank;
        CellInfo.File defaultFile;
        if (color == PieceInfo.Color.BLACK) {
            defaultRank = pieceType == PieceInfo.Type.KNIGHT_LEFT ? PieceInfo.InitBlackPosition.KNIGHT_LEFT.getRank()
                    : PieceInfo.InitBlackPosition.KNIGHT_RIGHT.getRank();

            defaultFile = pieceType == PieceInfo.Type.KNIGHT_LEFT ? PieceInfo.InitBlackPosition.KNIGHT_LEFT.getFile()
                    : PieceInfo.InitBlackPosition.KNIGHT_RIGHT.getFile();
        } else {
            defaultRank = pieceType == PieceInfo.Type.KNIGHT_LEFT ? PieceInfo.InitWhitePosition.KNIGHT_LEFT.getRank()
                    : PieceInfo.InitWhitePosition.KNIGHT_RIGHT.getRank();

            defaultFile = pieceType == PieceInfo.Type.KNIGHT_LEFT ? PieceInfo.InitWhitePosition.KNIGHT_LEFT.getFile()
                    : PieceInfo.InitWhitePosition.KNIGHT_RIGHT.getFile();
        }
        setPosition(defaultRank, defaultFile);
    }

    @Override
    protected final void initEventHandlers() {
        setOnMouseClicked(knightHandler::handleMouseEvent);
        setOnKeyPressed(knightHandler::handleKeyEvent);
    }
    
}
