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
public class PieceInfo {

    public enum Color {

        BLACK("BLACK"),
        WHITE("WHITE");

        private final String realColor;

        Color(String realColor) {
            this.realColor = realColor;
        }

        public String getColorName() {
            return this.realColor;
        }
    }
    
    public enum Position {

        LEFT,
        RIGHT,
        CENTER,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT;
    }

    public enum InitBlackPosition {

        ROOK_LEFT(CellInfo.Rank.ONE, CellInfo.File.A),
        KNIGHT_LEFT(CellInfo.Rank.ONE, CellInfo.File.B),
        BISHOP_LEFT(CellInfo.Rank.ONE, CellInfo.File.C),
        QUEEN(CellInfo.Rank.ONE, CellInfo.File.D),
        KING(CellInfo.Rank.ONE, CellInfo.File.E),
        BISHOP_RIGHT(CellInfo.Rank.ONE, CellInfo.File.F),
        KNIGHT_RIGHT(CellInfo.Rank.ONE, CellInfo.File.G),
        ROOK_RIGHT(CellInfo.Rank.ONE, CellInfo.File.H),
        PAWN_1(CellInfo.Rank.TWO, CellInfo.File.A),
        PAWN_2(CellInfo.Rank.TWO, CellInfo.File.B),
        PAWN_3(CellInfo.Rank.TWO, CellInfo.File.C),
        PAWN_4(CellInfo.Rank.TWO, CellInfo.File.D),
        PAWN_5(CellInfo.Rank.TWO, CellInfo.File.E),
        PAWN_6(CellInfo.Rank.TWO, CellInfo.File.F),
        PAWN_7(CellInfo.Rank.TWO, CellInfo.File.G),
        PAWN_8(CellInfo.Rank.TWO, CellInfo.File.H);
        

        private final CellInfo.Rank rank;
        private final CellInfo.File file;

        private InitBlackPosition(CellInfo.Rank rank, CellInfo.File file) {
            this.rank = rank;
            this.file = file;
        }

        public CellInfo.Rank getRank() {
            return rank;
        }

        public CellInfo.File getFile() {
            return file;
        }
    }

    public enum InitWhitePosition {

        ROOK_LEFT(CellInfo.Rank.EIGHT, CellInfo.File.A),
        KNIGHT_LEFT(CellInfo.Rank.EIGHT, CellInfo.File.B),
        BISHOP_LEFT(CellInfo.Rank.EIGHT, CellInfo.File.C),
        QUEEN(CellInfo.Rank.EIGHT, CellInfo.File.D),
        KING(CellInfo.Rank.EIGHT, CellInfo.File.E),
        BISHOP_RIGHT(CellInfo.Rank.EIGHT, CellInfo.File.F),
        KNIGHT_RIGHT(CellInfo.Rank.EIGHT, CellInfo.File.G),
        ROOK_RIGHT(CellInfo.Rank.EIGHT, CellInfo.File.H),
        PAWN_1(CellInfo.Rank.SEVEN, CellInfo.File.A),
        PAWN_2(CellInfo.Rank.SEVEN, CellInfo.File.B),
        PAWN_3(CellInfo.Rank.SEVEN, CellInfo.File.C),
        PAWN_4(CellInfo.Rank.SEVEN, CellInfo.File.D),
        PAWN_5(CellInfo.Rank.SEVEN, CellInfo.File.E),
        PAWN_6(CellInfo.Rank.SEVEN, CellInfo.File.F),
        PAWN_7(CellInfo.Rank.SEVEN, CellInfo.File.G),
        PAWN_8(CellInfo.Rank.SEVEN, CellInfo.File.H);

        private final CellInfo.Rank rank;
        private final CellInfo.File file;

        private InitWhitePosition(CellInfo.Rank rank, CellInfo.File file) {
            this.rank = rank;
            this.file = file;
        }

        public CellInfo.Rank getRank() {
            return rank;
        }

        public CellInfo.File getFile() {
            return file;
        }
    }

    public enum Type {
        WHITE_ROOK('\u2656', Name.ROOK),
        WHITE_KNIGHT('\u2658', Name.KNIGHT),
        WHITE_BISHOP('\u2657', Name.BISHOP),
        WHITE_KING('\u2654', Name.KING),
        WHITE_QUEEN('\u2655', Name.QUEEN),
        WHITE_PAWN('\u2659', Name.PAWN),
        BLACK_ROOK('\u265C', Name.ROOK),
        BLACK_KNIGHT('\u265E', Name.KNIGHT),
        BLACK_BISHOP('\u265D', Name.BISHOP),
        BLACK_KING('\u265A', Name.KING),
        BLACK_QUEEN('\u265B', Name.QUEEN),
        BLACK_PAWN('\u265F', Name.PAWN);
        
        private final char unicodeChar;
        private final Name pieceName;

        Type(char unicodeChar, Name pieceName) {
            this.unicodeChar = unicodeChar;
            this.pieceName = pieceName;
        }
        
        public static final int size = Type.values().length;
        
        public char getUnicodeChar() {
            return unicodeChar;
        }
        
        public Name getPieceName() {
            return pieceName;
        }
    }
    
    public enum CastlingSide {
        WHITE_KING_SIDE,
        WHITE_QUEEN_SIDE,
        BLACK_KING_SIDE,
        BLACK_QUEEN_SIDE;
        
        public static final int size = CastlingSide.values().length;
    }
    
    public enum Name {
        ROOK(500),
        KNIGHT(320),
        BISHOP(325),
        QUEEN(975),
        KING(32767),
        PAWN(100);
        
        private final int score;
        
        Name(int score) {
            this.score = score;
        }
        
        public int getScore() {
            return score;
        }
    }
}
