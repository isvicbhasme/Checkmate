/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.manager;

import checkmate.util.Address;
import checkmate.util.PieceInfo;
import java.util.ArrayList;

/**
 *
 * @author Isaac
 */
public class RecordingBuffer {

    ArrayList<Movement> buffer = new ArrayList<>();

    public RecordingBuffer(Movement move) {
        buffer.add(move);
    }

    public RecordingBuffer() {
    }

    public void addToBuffer(Movement move) {
        buffer.add(move);
    }

    public void addToBuffer(Address fromCell, Address toCell, PieceInfo.Type pieceType) {
        buffer.add(new Movement(fromCell, toCell, pieceType));
    }
    
    public void clearBuffer() {
        buffer.clear();
    }
    
    public ArrayList<Movement> getBuffer() {
        return buffer;
    }
}
