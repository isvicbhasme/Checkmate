/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.move;

import checkmate.util.Address;
import checkmate.util.CellInfo;

/**
 *
 * @author Isaac
 */
public interface IMovable {
    public boolean isMovePermitted(CellInfo.Rank rank, CellInfo.File file);
    public boolean moveIfPermitted(CellInfo.Rank rank, CellInfo.File file);
    public boolean isMovePermitted(Address cellAddress);
    public boolean moveIfPermitted(Address cellAddress);
    public void moveTo(CellInfo.Rank rank, CellInfo.File file);
    public void moveTo(Address targetAddress);
}
