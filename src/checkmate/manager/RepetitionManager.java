/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.manager;

import checkmate.util.Address;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import jdk.nashorn.internal.codegen.types.BitwiseType;

/**
 *
 * @author basme
 */
public class RepetitionManager {

    private final int numOfCells = CellInfo.Rank.size * CellInfo.File.size;
    private final int numOfPieces = PieceInfo.Type.size;
    private final long[][] bitStringTable = new long[numOfPieces][numOfCells];
    private Queue hashHistory = new ArrayBlockingQueue(100);
    private long zobristKey = 0;
    private int seed = 0;

    private RepetitionManager() {
        initBitStringTable();
    }

    public static RepetitionManager getInstance() {
        return RepetitionManagerHolder.INSTANCE;
    }

    private static class RepetitionManagerHolder {

        private static final RepetitionManager INSTANCE = new RepetitionManager();
    }

    private void initBitStringTable() {
        Random random = new Random();
        for (int i = 0; i < numOfPieces; i++) {
            for (int j = 0; j < numOfCells; j++) {
                random.setSeed(seed++);
                bitStringTable[i][j] = random.nextLong();
                if(bitStringTable[i][j] < 0)
                    bitStringTable[i][j] *= -1;
            }
        }
    }
    
    public boolean isCurrentMoveRepeated() {
        return hashHistory.contains(zobristKey);
    }
    
    public void storeHash() {
        hashHistory.add(zobristKey);
    }

    public void hash(PieceInfo.Type pieceType, CellInfo.Rank rank, CellInfo.File file) {
        zobristKey = zobristKey ^ bitStringTable[pieceType.ordinal()][(rank.ordinal()*8) + file.ordinal()];
    }

    public void hash(PieceInfo.Type pieceType, Address address) {
        hash(pieceType, address.rank, address.file);
    }
}
