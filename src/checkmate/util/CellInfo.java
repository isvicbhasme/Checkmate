/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.util;

/**
 *
 * @author basme
 *
 * Contains different Enum types associated with a cell
 */
public class CellInfo {

    public static int centerDistanceMap[] = new int[]{
        3, 3, 3, 3, 3, 3, 3, 3,
        3, 2, 2, 2, 2, 2, 2, 3,
        3, 2, 1, 1, 1, 1, 2, 3,
        3, 2, 1, 0, 0, 1, 2, 3,
        3, 2, 1, 0, 0, 1, 2, 3,
        3, 2, 1, 1, 1, 1, 2, 3,
        3, 2, 2, 2, 2, 2, 2, 3,
        3, 3, 3, 3, 3, 3, 3, 3
    };

    /**
     * Denotes the color of the cell
     */
    public enum Color {

        BLACK("DIMGRAY"),
        WHITE("GHOSTWHITE");

        private final String realColor;

        Color(String realColor) {
            this.realColor = realColor;
        }

        public String getColorName() {
            return this.realColor;
        }
    }

    /**
     * Denotes the rank/row of the cell
     */
    public enum Rank {

        ONE, TWO, THREE, FIVE, SIX, SEVEN, EIGHT;
    }

    /**
     * Denotes the file/column of the cell
     */
    public enum File {

        A, B, C, D, E, F, G, H;
    }

}
