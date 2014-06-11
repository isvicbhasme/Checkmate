/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.move;

import checkmate.util.CellInfo;

/**
 *
 * @author Isaac
 */
public interface IMove {

    public boolean isMoveAllowed(CellInfo.Rank rank, CellInfo.File file);
    
}
