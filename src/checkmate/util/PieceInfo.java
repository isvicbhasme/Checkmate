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

    public enum FontInfo {

        TYPE("TAHOMA"),
        SIZE("40");

        public final String data;

        FontInfo(String data) {
            this.data = data;
        }
    }

    public enum Type {

        ROOK_LEFT,
        KNIGHT_LEFT,
        BISHOP_LEFT,
        KING,
        QUEEN,
        BISHOP_RIGHT,
        KNIGHT_RIGHT,
        ROOK_RIGHT;
    }

    public enum InitBlackPosition {

        ROOK_LEFT(CellInfo.Rank.ONE, CellInfo.File.A),
        KNIGHT_LEFT(CellInfo.Rank.ONE, CellInfo.File.B),
        BISHOP_LEFT(CellInfo.Rank.ONE, CellInfo.File.C),
        KING(CellInfo.Rank.ONE, CellInfo.File.D),
        QUEEN(CellInfo.Rank.ONE, CellInfo.File.E),
        BISHOP_RIGHT(CellInfo.Rank.ONE, CellInfo.File.F),
        KNIGHT_RIGHT(CellInfo.Rank.ONE, CellInfo.File.G),
        ROOK_RIGHT(CellInfo.Rank.ONE, CellInfo.File.H);

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
        KING(CellInfo.Rank.EIGHT, CellInfo.File.D),
        QUEEN(CellInfo.Rank.EIGHT, CellInfo.File.E),
        BISHOP_RIGHT(CellInfo.Rank.EIGHT, CellInfo.File.F),
        KNIGHT_RIGHT(CellInfo.Rank.EIGHT, CellInfo.File.G),
        ROOK_RIGHT(CellInfo.Rank.EIGHT, CellInfo.File.H);

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

}
