/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.design;

import checkmate.util.CellInfo;

/**
 *
 * @author bhasme
 */
public interface IMovable {
    public void moveTo(CellInfo.Rank rank, CellInfo.File file);
}
