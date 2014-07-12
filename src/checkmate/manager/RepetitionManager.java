/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.manager;

import checkmate.design.Piece;
import checkmate.util.Address;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;
import java.util.Iterator;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * This singleton class is responsible for hashing the state of a board at the end of
 * every move/attack. Also maintains the hash in its history list.
 * This helps to identify duplicated moves/attack during a game session
 * @author bhasme
 */
public class RepetitionManager {

    private final int numOfCells = CellInfo.Rank.size * CellInfo.File.size;
    private final int numOfPieces = PieceInfo.Type.size;
    private final long[][] bitStringTable = new long[numOfPieces][numOfCells];
    private long whitesTurnToPlay;
    private long[] castlingRights = new long[PieceInfo.CastlingSide.size];
    private long[] enPassantAvailables = new long[CellInfo.File.size];
    private Queue hashHistory = new ArrayBlockingQueue(100);
    private long zobristKey = 0;
    private int seed = 0;

    private RepetitionManager() {
        initBitStringTable();
        initZobristKey();
    }

    /**
     * Gets an existing instance of this class; if not existing, a new instance is created
     * @return an instance of RepetitionManager
     */
    public static RepetitionManager getInstance() {
        return RepetitionManagerHolder.INSTANCE;
    }
    
    /**
     * Hashes the given piece at the given source and target address. This operation
     * does not store the hash to the history, since there can be multiple hashes to be performed
     * before storing to the history data.
     * 
     * @param selectedPiece the piece that needs to be hashed
     * @param sourceAddress the source address
     * @param targetAddress the target address
     */
    public void hashPieceMovement(Piece selectedPiece, Address sourceAddress, Address targetAddress) {
        hash(selectedPiece.getPieceType(), sourceAddress);
        hash(selectedPiece.getPieceType(), targetAddress);
    }
    
    public void storePieceMovementHash(Piece selectedPiece, Address sourceAddress, Address targetAddress) {
        hashTogglePlay();
        hash(selectedPiece.getPieceType(), sourceAddress);
        hash(selectedPiece.getPieceType(), targetAddress);
        storeHash();
    }
    
    public void storePieceAttackHash(Piece attackedPiece, Address targetAddress, Piece attackingPiece, Address sourceAddress) {
        hashTogglePlay();
        hash(attackedPiece.getPieceType(), targetAddress);
        hash(attackingPiece.getPieceType(), sourceAddress);
        hash(attackingPiece.getPieceType(), targetAddress);
        storeHash();
    }

    private static class RepetitionManagerHolder {

        private static final RepetitionManager INSTANCE = new RepetitionManager();
    }

    /**
     * Initializes the bit string table which contains a bitString for every piece
     * at every possible position on the chess board
     */
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
        for (int i = 0; i < castlingRights.length; i++) {
            random.setSeed(seed++);
            castlingRights[i] = random.nextLong();
        }
        for (int i = 0; i < enPassantAvailables.length; i++) {
            random.setSeed(seed++);
            enPassantAvailables[i] = random.nextLong();
        }
    }

    /**
     * Generates the initial hash based on Zobrist's hashing algorithm
     */
    private void initZobristKey() {
        zobristKey ^= whitesTurnToPlay;
        for (int i = 0; i < castlingRights.length; i++) {
            zobristKey ^= castlingRights[i];
        }
    }

    /**
     * Perform hashing pertaining to pawn promotion. 
     * This involves removing (by hashing) the pawn from its existing rank 
     * and generating new set of bitStrings for that rank
     * at which the new piece will be hashed.
     * 
     * NOTE: The pieceType for hashing will continue to remain as PAWN even after promotion to another piece
     * @param pieceType
     * @param rank Rank of the pawn
     * @param file File of the pawn
     */
    public void doPawnPromoteHashing(PieceInfo.Type pieceType, CellInfo.Rank rank, CellInfo.File file) {
        if (pieceType.getPieceName() != PieceInfo.Name.PAWN) {
            throw new IllegalStateException("Only a Pawn should be promoted");
        }
        hash(pieceType, rank, file);
        Random random = new Random();
        for (int j = 0; j < numOfCells; j++) {
            random.setSeed(seed++);
            bitStringTable[pieceType.ordinal()][j] = random.nextLong();
        }
        hash(pieceType, rank, file);
    }

    /**
     * Indicates whether the current hash is already present in its history database.
     * This can be done to check if the current state of the chess board was repeated previously.
     * @return true if the state is repeated, false otherwise
     */
    public boolean isCurrentMoveRepeated() {
        return hashHistory.contains(zobristKey);
    }

    /**
     * Stores the current hash to the history database.
     */
    public void storeHash() {
        hashHistory.add(zobristKey);
        int foundCount = 0;
        Iterator iterator = hashHistory.iterator();
        while (iterator.hasNext()) {
            if (zobristKey == (long) iterator.next()) {
                foundCount++;
            }
        }
        if (foundCount > 2) {
            //TODO: Display dialog for optional draw of game
            System.out.println("Move repeated thrice");
        }
    }

    /**
     * Hashes the given piece at the given position. This operation
     * does not store the hash to the history, since there can be multiple hashes to be performed
     * before storing to the history data.
     * @param pieceType
     * @param rank
     * @param file
     */
    public void hash(PieceInfo.Type pieceType, CellInfo.Rank rank, CellInfo.File file) {
        zobristKey = zobristKey ^ bitStringTable[pieceType.ordinal()][(rank.ordinal() * 8) + file.ordinal()];
    }

    /**
     * Hashes the given piece at the given source and address. This operation
     * does not store the hash to the history, since there can be multiple hashes to be performed
     * before storing to the history data.
     * @param pieceType
     * @param address
     */
    public void hash(PieceInfo.Type pieceType, Address address) {
        hash(pieceType, address.rank, address.file);
    }

    /**
     * Performs an hash on 'whose turn it is to play'. This is done so that the same chess piece positions
     * will not generate the same zobrist hash if the 'turn to play' differs
     */
    public void hashTogglePlay() {
        zobristKey ^= whitesTurnToPlay;
    }

    /**
     * Hash the file at which enpassant is possible/no longer possible
     * @param enPassantFile
     */
    public void hashEnPassant(CellInfo.File enPassantFile) {
        zobristKey ^= enPassantAvailables[enPassantFile.ordinal()];
    }

    /**
     * Hash the castling rights of a chess session
     * @param position
     */
    public void hashCastlingRights(PieceInfo.CastlingSide position) {
        zobristKey ^= castlingRights[position.ordinal()];
    }
}
