/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.design;

import checkmate.Launcher;
import checkmate.util.CellInfo;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author bhasme
 */
public class Cell extends CellGroup {

    private final int length;
    private Rectangle cell;

    public Cell(CellInfo.Rank rank, CellInfo.File file) {
        super();
        cell = new Rectangle(Launcher.resource.getDoubleConfig("Square.length"),
                Launcher.resource.getDoubleConfig("Square.length"),
                Paint.valueOf(computeColor(file, rank).getColorName()));
        this.length = Launcher.resource.getIntConfig("Square.length");
        cell.setX(length * file.ordinal());
        cell.setY(length * rank.ordinal());
        this.color = computeColor(file, rank);
        this.rank = rank;
        this.file = file;
        getChildren().add(cell);
    }

    public double getLength() {
        return cell.getX();
    }

    public CellInfo.Color getColor() {
        return color;
    }

    public CellInfo.Rank getRank() {
        return rank;
    }

    public CellInfo.File getFile() {
        return file;
    }

    public int getDistanceFromCenter() {
        return CellInfo.centerDistanceMap[file.ordinal() + (8 * rank.ordinal())];
    }
    
    public void addPieceToCellGroup(Piece piece) {
        while(getChildren().size() > 1) {
            getChildren().remove(1);
        }
        getChildren().add(piece);
    }
    
    public void removePieceFromCellGroup(Piece piece) {
        getChildren().remove(piece);
    }
            
    private static CellInfo.Color computeColor(CellInfo.File file, CellInfo.Rank rank) {
        if ((file.ordinal() + rank.ordinal()) % 2 == 0) {
            return CellInfo.Color.WHITE;
        } else {
            return CellInfo.Color.BLACK;
        }
    }

}
