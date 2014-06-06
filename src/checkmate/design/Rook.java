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

    public static final char unicodeString = '\u2656';
    protected RookEvtHandler rookHandler;

    public Rook(PieceInfo.Color color, PieceInfo.Type pieceType) throws IOException {
        super(unicodeString, color, pieceType);
        rookHandler = new RookEvtHandler();
        setInitialPosition(color, pieceType);
        initEventHandlers();
    }

    @Override
    protected final void setInitialPosition(PieceInfo.Color color, PieceInfo.Type pieceType) {
        CellInfo.Rank defaultRank;
        CellInfo.File defaultFile;
        if (color == PieceInfo.Color.BLACK) {
            defaultRank = pieceType == PieceInfo.Type.ROOK_LEFT ? PieceInfo.InitBlackPosition.ROOK_LEFT.getRank()
                    : PieceInfo.InitBlackPosition.ROOK_RIGHT.getRank();

            defaultFile = pieceType == PieceInfo.Type.ROOK_LEFT ? PieceInfo.InitBlackPosition.ROOK_LEFT.getFile()
                    : PieceInfo.InitBlackPosition.ROOK_RIGHT.getFile();
        } else {
            defaultRank = pieceType == PieceInfo.Type.ROOK_LEFT ? PieceInfo.InitWhitePosition.ROOK_LEFT.getRank()
                    : PieceInfo.InitWhitePosition.ROOK_RIGHT.getRank();

            defaultFile = pieceType == PieceInfo.Type.ROOK_LEFT ? PieceInfo.InitWhitePosition.ROOK_LEFT.getFile()
                    : PieceInfo.InitWhitePosition.ROOK_RIGHT.getFile();
        }
        setPosition(defaultRank, defaultFile);
    }

    @Override
    protected final void initEventHandlers() {
        setOnMouseClicked(rookHandler::handleMouseEvent);
        setOnKeyPressed(rookHandler::handleKeyEvent);
    }

}
