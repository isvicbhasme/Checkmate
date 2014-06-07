/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.design;

import checkmate.event.PawnEvtHandler;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;

/**
 *
 * @author Isaac
 */
public class Pawn extends Piece {

    protected PawnEvtHandler pawnHandler;

    public Pawn(PieceInfo.Type pieceType, PieceInfo.Position position) {
        super(pieceType, position);
        pawnHandler = new PawnEvtHandler();
        setInitialPosition(pieceType, position);
        initEventHandlers();
    }

    @Override
    protected final void setInitialPosition(PieceInfo.Type pieceType, PieceInfo.Position position) {
        CellInfo.Rank defaultRank;
        CellInfo.File defaultFile;
        if (pieceType == PieceInfo.Type.BLACK_PAWN) {
            defaultRank = getInitBlackRank(position);
            defaultFile = getInitBlackFile(position);
        } else if (pieceType == PieceInfo.Type.WHITE_PAWN) {
            defaultRank = getInitWhiteRank(position);
            defaultFile = getInitWhiteFile(position);
        } else {
            throw new IllegalStateException("Illegal piece creation");
        }
        setPosition(defaultRank, defaultFile);
    }
    
    private CellInfo.Rank getInitBlackRank(PieceInfo.Position position) {
        switch(position)
        {
            case ONE: return PieceInfo.InitBlackPosition.PAWN_1.getRank();
            case TWO: return PieceInfo.InitBlackPosition.PAWN_2.getRank();
            case THREE: return PieceInfo.InitBlackPosition.PAWN_3.getRank();
            case FOUR: return PieceInfo.InitBlackPosition.PAWN_4.getRank();
            case FIVE: return PieceInfo.InitBlackPosition.PAWN_5.getRank();
            case SIX: return PieceInfo.InitBlackPosition.PAWN_6.getRank();
            case SEVEN: return PieceInfo.InitBlackPosition.PAWN_7.getRank();
            case EIGHT: return PieceInfo.InitBlackPosition.PAWN_8.getRank();
        }
        throw new IllegalStateException("Illegal rank position requested");
    }
    
    private CellInfo.File getInitBlackFile(PieceInfo.Position position) {
        switch(position)
        {
            case ONE: return PieceInfo.InitBlackPosition.PAWN_1.getFile();
            case TWO: return PieceInfo.InitBlackPosition.PAWN_2.getFile();
            case THREE: return PieceInfo.InitBlackPosition.PAWN_3.getFile();
            case FOUR: return PieceInfo.InitBlackPosition.PAWN_4.getFile();
            case FIVE: return PieceInfo.InitBlackPosition.PAWN_5.getFile();
            case SIX: return PieceInfo.InitBlackPosition.PAWN_6.getFile();
            case SEVEN: return PieceInfo.InitBlackPosition.PAWN_7.getFile();
            case EIGHT: return PieceInfo.InitBlackPosition.PAWN_8.getFile();
        }
        throw new IllegalStateException("Illegal file position requested");
    }
    
    private CellInfo.Rank getInitWhiteRank(PieceInfo.Position position) {
        switch(position)
        {
            case ONE: return PieceInfo.InitWhitePosition.PAWN_1.getRank();
            case TWO: return PieceInfo.InitWhitePosition.PAWN_2.getRank();
            case THREE: return PieceInfo.InitWhitePosition.PAWN_3.getRank();
            case FOUR: return PieceInfo.InitWhitePosition.PAWN_4.getRank();
            case FIVE: return PieceInfo.InitWhitePosition.PAWN_5.getRank();
            case SIX: return PieceInfo.InitWhitePosition.PAWN_6.getRank();
            case SEVEN: return PieceInfo.InitWhitePosition.PAWN_7.getRank();
            case EIGHT: return PieceInfo.InitWhitePosition.PAWN_8.getRank();
        }
        throw new IllegalStateException("Illegal rank position requested");
    }
    
    private CellInfo.File getInitWhiteFile(PieceInfo.Position position) {
        switch(position)
        {
            case ONE: return PieceInfo.InitWhitePosition.PAWN_1.getFile();
            case TWO: return PieceInfo.InitWhitePosition.PAWN_2.getFile();
            case THREE: return PieceInfo.InitWhitePosition.PAWN_3.getFile();
            case FOUR: return PieceInfo.InitWhitePosition.PAWN_4.getFile();
            case FIVE: return PieceInfo.InitWhitePosition.PAWN_5.getFile();
            case SIX: return PieceInfo.InitWhitePosition.PAWN_6.getFile();
            case SEVEN: return PieceInfo.InitWhitePosition.PAWN_7.getFile();
            case EIGHT: return PieceInfo.InitWhitePosition.PAWN_8.getFile();
        }
        throw new IllegalStateException("Illegal file position requested");
    }

    @Override
    protected final void initEventHandlers() {
        setOnMouseClicked(pawnHandler::handleMouseEvent);
        setOnKeyPressed(pawnHandler::handleKeyEvent);
    }
}
