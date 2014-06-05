/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate;

import checkmate.design.Piece;

/**
 *
 * @author bhasme
 */
public class GamePlay {
    
    private boolean isMouseDown;
    private boolean isKeyDown;
    private Piece movingPiece;
    
    private GamePlay() {
    }
    
    public static GamePlay getInstance() {
        return GamePlayHolder.INSTANCE;
    }
    
    private static class GamePlayHolder {

        private static final GamePlay INSTANCE = new GamePlay();
    }
}
