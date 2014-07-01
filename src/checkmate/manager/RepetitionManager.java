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

/**
 *
 * @author bhasme
 */
public class RepetitionManager {

    private final int numOfCells = CellInfo.Rank.size * CellInfo.File.size;
    private final int numOfPieces = PieceInfo.Type.size;
    private final long[][] bitStringTable = new long[numOfPieces][numOfCells];
    private long whitesTurnToPlay;
    private long[] castlingRights = new long[4];
    private long[] enPassantAvailables = new long[CellInfo.File.size];
    private Queue hashHistory = new ArrayBlockingQueue(100);
    private long zobristKey = 0;
    private int seed = 0;

    private RepetitionManager() {
        initBitStringTable();
        initZobristKey();
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
            }
        }
        random.setSeed(seed++);
        whitesTurnToPlay = random.nextLong();
        for (int i=0; i< castlingRights.length; i++) {
            random.setSeed(seed++);
            castlingRights[i] = random.nextLong();
        }
        for (int i=0; i< enPassantAvailables.length; i++) {
            random.setSeed(seed++);
            enPassantAvailables[i] = random.nextLong();
        }
    }
    
    private void initZobristKey() {
        zobristKey ^= whitesTurnToPlay;
        for (int i=0; i< castlingRights.length; i++) {
            zobristKey ^= castlingRights[i];
        }
    }

    public boolean isCurrentMoveRepeated() {
        return hashHistory.contains(zobristKey);
    }

    public void storeHash() {
        hashHistory.add(zobristKey);
    }

    public void hash(PieceInfo.Type pieceType, CellInfo.Rank rank, CellInfo.File file) {
        zobristKey = zobristKey ^ bitStringTable[pieceType.ordinal()][(rank.ordinal() * 8) + file.ordinal()];
    }

    public void hash(PieceInfo.Type pieceType, Address address) {
        hash(pieceType, address.rank, address.file);
    }
    
    public void hashTogglePlay() {
        zobristKey ^= whitesTurnToPlay;
    }
    
    public void hashEnPassant(CellInfo.File enPassantFile) {
        zobristKey ^= enPassantAvailables[enPassantFile.ordinal()];
    }
    
    public void hashCastlingRights(int position) {
        zobristKey ^= castlingRights[position];
    }
}
