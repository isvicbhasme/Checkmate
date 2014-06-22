/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.manager;

import checkmate.design.Piece;
import checkmate.util.Address;
import checkmate.util.PieceInfo;

/**
 *
 * @author bhasme
 */
public class GamePlay {

    private boolean isPieceSelected;
    private Piece movingPiece;
    private MoveRecorder moveRecorder;
    private RecordingBuffer recordingData;
    private static GamePlay instance = null;
    private PieceInfo.Color playTurn = PieceInfo.Color.WHITE;

    private GamePlay() {
        this.moveRecorder = new MoveRecorder();
        this.recordingData = new RecordingBuffer();
    }

    public static GamePlay getInstance() {
        if (instance == null) {
            instance = new GamePlay();
        }
        return instance;
    }

    public boolean isPieceSelected() {
        return isPieceSelected;
    }

    public Piece getMovingPiece() {
        return movingPiece;
    }

    public void setMovingPiece(Piece movingPiece) {
        this.movingPiece = movingPiece;
        this.isPieceSelected = true;
        movingPiece.getCell().enableHighlight();
    }

    public void togglePlayTurn() {
        playTurn = (playTurn == PieceInfo.Color.WHITE) ? PieceInfo.Color.BLACK : PieceInfo.Color.WHITE;
    }

    public boolean isWhitesTurnToPlay() {
        return playTurn == PieceInfo.Color.WHITE;
    }

    public boolean isBlacksTurnToPlay() {
        return playTurn == PieceInfo.Color.BLACK;
    }

    public PieceInfo.Color getPlayTurn() {
        return playTurn;
    }

    public void resetPieceMovement() {
        movingPiece.getCell().disableHighlight();
        movingPiece = null;
        isPieceSelected = false;
    }

    public void writeMoveToBuffer(Movement move) {
        recordingData.addToBuffer(move);
    }

    public void writeMoveToBuffer(Address fromCell, Address toCell, PieceInfo.Type pieceType) {
        recordingData.addToBuffer(fromCell, toCell, pieceType);
    }

    public void flushBuffer() {
        if (recordingData.getBuffer().size() > 0) {
            moveRecorder.addToDisc(recordingData.getBuffer());
            recordingData.clearBuffer();
        }
    }
}
