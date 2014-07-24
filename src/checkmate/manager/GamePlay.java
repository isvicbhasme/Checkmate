/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.manager;

import checkmate.Launcher;
import checkmate.ai.IAi;
import checkmate.ai.Tree;
import checkmate.design.Piece;
import checkmate.util.PieceInfo;
import checkmate.util.ProjectInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The GamePlay singleton class is responsible for maintaining the status of a
 * ongoing chess game
 *
 * @author bhasme
 */
public class GamePlay {

    private boolean isPieceSelected;
    private Piece movingPiece;
    private static GamePlay instance = null;
    private PieceInfo.Color playTurn = PieceInfo.Color.WHITE;
    private ProjectInfo.PlayType playType;
    private IAi intelligence;

    private GamePlay() {
        try {
            intelligence = (IAi) Class.forName(Launcher.resource.getStringConfig("GamePlay.ai")).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns an existing instance of GamePlay. If not existing, a new instance
     * is created.
     *
     * @return GamePlay instance
     */
    public static GamePlay getInstance() {
        if (instance == null) {
            instance = new GamePlay();
        }
        return instance;
    }

    /**
     * Indicates whether a piece is already selected to make a move or attack
     *
     * @return true is selected, false otherwise
     */
    public boolean isPieceSelected() {
        return isPieceSelected;
    }

    /**
     * Retrieves the instance of the selected piece
     *
     * @return Piece
     */
    public Piece getMovingPiece() {
        return movingPiece;
    }

    /**
     * Sets the given piece as the selected piece to initiate a move or attack
     *
     * @param movingPiece
     */
    public void setMovingPiece(Piece movingPiece) {
        this.movingPiece = movingPiece;
        this.isPieceSelected = true;
        movingPiece.getCell().enableHighlight();
    }

    /**
     * Toggles the play, so the other player can make a move
     */
    public void togglePlayTurn() {
        playTurn = (playTurn == PieceInfo.Color.WHITE) ? PieceInfo.Color.BLACK : PieceInfo.Color.WHITE;
    }

    /**
     * Indicates whether it is the white's turn to make a move or attack
     *
     * @return true if white's turn, false otherwise
     */
    public boolean isWhitesTurnToPlay() {
        return playTurn == PieceInfo.Color.WHITE;
    }

    /**
     * Indicates whether it is the black's turn to make a move or attack
     *
     * @return true if black's turn, false otherwise
     */
    public boolean isBlacksTurnToPlay() {
        return playTurn == PieceInfo.Color.BLACK;
    }

    /**
     * Indicates the color of the side which needs to make a move or attack
     *
     * @return PieceInfo.Color
     */
    public PieceInfo.Color getPlayTurn() {
        return playTurn;
    }

    /**
     * De-selects the selected piece, to make a fresh selection
     */
    public void resetPieceMovement() {
        movingPiece.getCell().disableHighlight();
        movingPiece = null;
        isPieceSelected = false;
    }

    public ProjectInfo.PlayType getPlayType() {
        return playType;
    }

    public void setPlayType(ProjectInfo.PlayType playType) {
        this.playType = playType;
    }
    
    public boolean isAiTurnToPlay() {
        if(getPlayType() == ProjectInfo.PlayType.DOUBLE_PLAYER)
            throw new IllegalAccessError("Double player mode cannot play against AI");
        return playTurn != getPlayType().getPlayerColor();
    }
    
    public void triggerAiMove() {
        intelligence.generateMove();
    }
}
