/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.design;

import checkmate.Launcher;
import checkmate.event.CellEvtHandler;
import checkmate.util.Address;
import checkmate.util.CellInfo;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author bhasme
 */
public class Cell extends StackPane {

    private final int length;
    private final Rectangle cell;
    private static final CellEvtHandler cellHandler = new CellEvtHandler();
    private boolean isOccupied;
    protected CellInfo.Color color;
    protected CellInfo.Rank rank;
    protected CellInfo.File file;

    public Cell(CellInfo.Rank rank, CellInfo.File file) {
        super();
        cell = new Rectangle(Launcher.resource.getDoubleConfig("Cell.length"),
                Launcher.resource.getDoubleConfig("Cell.length"),
                Paint.valueOf(computeColor(file, rank).getColorName()));
        this.length = Launcher.resource.getIntConfig("Cell.length");
        cell.setX(length * file.ordinal());
        cell.setY(length * rank.ordinal());
        cell.setStrokeType(StrokeType.INSIDE);
        cell.setStrokeWidth(Launcher.resource.getDoubleConfig("Cell.highlightWidth"));
        this.color = computeColor(file, rank);
        this.rank = rank;
        this.file = file;
        getChildren().add(cell);
        enableEventHandlers();
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
    
    public Address getAddress() {
        return new Address(rank, file);
    }

    public int getDistanceFromCenter() {
        return CellInfo.centerDistanceMap[file.ordinal() + (8 * rank.ordinal())];
    }

    public void addPieceToCell(Piece piece) {
        while (getChildren().size() > 1) {
            getChildren().remove(1);
        }
        getChildren().add(piece);
        isOccupied = true;
    }

    public void removePieceFromCell(Piece piece) {
        getChildren().remove(piece);
        piece.resetPosition();
        isOccupied = false;
    }

    private static CellInfo.Color computeColor(CellInfo.File file, CellInfo.Rank rank) {
        if ((file.ordinal() + rank.ordinal()) % 2 == 0) {
            return CellInfo.Color.WHITE;
        } else {
            return CellInfo.Color.BLACK;
        }
    }

    public final void enableEventHandlers() {
        setOnMouseClicked(cellHandler::handleMouseEvent);

    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    void disableEventHandlers() {
        setOnMouseClicked(null);
    }

    public void enableHighlight() {
        cell.setStroke(Paint.valueOf(Launcher.resource.getStringConfig("Cell.highlight")));
    }
    
    public void disableHighlight() {
        cell.setStroke(null);
    }
    
    public boolean isHighlighted() {
        return cell.getStroke() == null;
    }
    
    public boolean isOccupiedByWhite() {
        return isOccupied && ((Piece)getChildren().get(1)).isWhitePiece();
    }
    
    public Piece getPiece() {
        return (getChildren().size() > 1)? (Piece)getChildren().get(1) : null;
    }
}
