/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.util;

/**
 *
 * @author bhasme
 */
public class ProjectInfo {
    public static final String configFileName = "/checkmate/util/checkmate.properties";
    
    public enum PlayType {
        DOUBLE_PLAYER,
        SINGLE_PLAYER;
        
        private PieceInfo.Color playerColor;
        
        PlayType() {
            playerColor = null;
        }
        
        public void setPlayerColor(PieceInfo.Color playerColor) {
            this.playerColor = playerColor;
        }
        
        public PieceInfo.Color getPlayerColor() {
            return this.playerColor;
        }
    }
}
