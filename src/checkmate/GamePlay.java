/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate;

import checkmate.design.Cell;
import checkmate.design.Piece;

/**
 *
 * @author bhasme
 */
public class GamePlay {

    private boolean isPieceSelected;
    private Piece movingPiece;

    private GamePlay() {
    }

    public static GamePlay getInstance() {
        return GamePlayHolder.INSTANCE;
    }

    private static class GamePlayHolder {

        private static final GamePlay INSTANCE = new GamePlay();
    }

    public boolean isPieceSelected() {
        return isPieceSelected;
    }

    public void setIsPieceSelected(boolean isPieceSelected) {
        this.isPieceSelected = isPieceSelected;
    }

    public Piece getMovingPiece() {
        return movingPiece;
    }

    public void setMovingPiece(Piece movingPiece) {
        this.movingPiece = movingPiece;
        movingPiece.getCell().enableHighlight();
    }

    public void resetPieceMovement() {
        movingPiece.getCell().disableHighlight();
        movingPiece = null;
        isPieceSelected = false;
    }

}
