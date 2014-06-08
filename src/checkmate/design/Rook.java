/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.design;

import checkmate.Launcher;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author bhasme
 */
public class Rook extends Piece {

    public Rook(PieceInfo.Type pieceType, PieceInfo.Position position) throws IOException {
        super(pieceType, position);
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
        setOnMouseClicked(pieceHandler::handleMouseEvent);
        setOnKeyPressed(pieceHandler::handleKeyEvent);
    }

    @Override
    public boolean isMoveAllowed(CellInfo.Rank toRank, CellInfo.File toFile) {
        boolean isPathClear = false;
        if (getRankPosition() == toRank) {
            if (getFilePosition().compareTo(toFile) < 0) {
                Set<CellInfo.File> filesInPath = EnumSet.range(getFilePosition(), toFile);
                isPathClear = isFilePathClear(filesInPath);
            } else if (getFilePosition().compareTo(toFile) > 0) {
                Set<CellInfo.File> filesInPath = EnumSet.range(toFile, getFilePosition());
                isPathClear = isFilePathClear(filesInPath);
            } else {
                throw new IllegalStateException("Source and destination of move is the same");
            }
        } else if(getFilePosition() == toFile) {
            if (getRankPosition().compareTo(toRank) < 0) {
                Set<CellInfo.Rank> ranksInPath = EnumSet.range(getRankPosition(), toRank);
                isPathClear = isRankPathClear(ranksInPath);
            } else if (getRankPosition().compareTo(toRank) > 0) {
                Set<CellInfo.Rank> filesInPath = EnumSet.range(toRank, getRankPosition());
                isPathClear = isRankPathClear(filesInPath);
            } else {
                throw new IllegalStateException("Source("+getRankPosition()+", "+getFilePosition()+") and destination("+toRank+", "+toFile+") of move is the same");
            }
        }
        System.out.println("Sending "+isPathClear);
        return isPathClear;
    }

    private boolean isFilePathClear(Set<CellInfo.File> filesInPath ) {
        boolean isPathClear = true;
        Iterator<CellInfo.File> pathIterator = filesInPath.iterator();
        while (pathIterator.hasNext()) {
            CellInfo.File fileInPath = pathIterator.next();
            if (fileInPath == getFilePosition()) {
                continue; //skip because this is where is current piece resides
            }
            Cell cellInPath = Launcher.board.getCell(getRankPosition(), fileInPath);
            if (cellInPath.isOccupied()) {
                isPathClear = false;
                break;
            }
        }
        return isPathClear;
    }
    
    private boolean isRankPathClear(Set<CellInfo.Rank> ranksInPath ) {
        boolean isPathClear = true;
        Iterator<CellInfo.Rank> pathIterator = ranksInPath.iterator();
        while (pathIterator.hasNext()) {
            CellInfo.Rank rankInPath = pathIterator.next();
            if (rankInPath == getRankPosition()) {
                continue; //skip because this is where is current piece resides
            }
            Cell cellInPath = Launcher.board.getCell(rankInPath, getFilePosition());
            if (cellInPath.isOccupied()) {
                isPathClear = false;
                break;
            }
        }
        return isPathClear;
    }

}
