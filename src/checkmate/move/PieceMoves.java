/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.move;

import checkmate.Launcher;
import checkmate.design.Cell;
import checkmate.design.Piece;
import checkmate.util.Address;
import checkmate.util.CellInfo;
import java.util.ArrayList;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 *
 * @author Isaac
 */
public abstract class PieceMoves implements IMovable {

    protected ArrayList<IMove> moveTypes;
    protected Piece piece;

    public PieceMoves() {
        this.moveTypes = new ArrayList<>();
    }

    /**
     * Moves the piece to the specified cell. Note that the validity of the
     * movement is not checked by this method.
     *
     * @param rank Rank of the target cell
     * @param file File of the target cell
     */
    @Override
    public void moveTo(CellInfo.Rank rank, CellInfo.File file) {
        Cell currentCell = Launcher.board.getCell(piece.getRank(), piece.getFile());
        Cell newCell = Launcher.board.getCell(rank, file);
        boolean isPieceMoveInitiated = false;
        if (isMoveToHigherZOrder(rank, file)) {
            movePiece(currentCell, newCell);
            piece.setTranslateX(currentCell.getLayoutX() - newCell.getLayoutX());
            piece.setTranslateY(currentCell.getLayoutY() - newCell.getLayoutY());
            isPieceMoveInitiated = true;
        }
        animateMovement(newCell, currentCell, isPieceMoveInitiated);
    }
    
    /**
     * Helper method to remove piece from current cell and set its position to new cell.
     * @param currentCell Current cell in which the piece is located
     * @param newCell New cell to which the piece should be moved
     */
    private void movePiece(Cell currentCell, Cell newCell) {
        currentCell.removePieceFromCellGroup(piece);
        currentCell.enableEventHandlers();
        piece.setPosition(newCell.getRank(), newCell.getFile());
    }

    /**
     * Indicates whether the piece is being moved along a path where the cells can block the visibility of the piece
     * @param rank Target rank of the piece
     * @param file Target file of the piece
     * @return 
     */
    private boolean isMoveToHigherZOrder(CellInfo.Rank rank, CellInfo.File file) {
        return rank.ordinal() > piece.getRank().ordinal() || (rank.ordinal() == piece.getRank().ordinal() && file.ordinal() > piece.getFile().ordinal());
    }

    /**
     * Performs animation of the movement and moves the piece if not already moved
     * @param newCell New cell to which the piece is being moved
     * @param currentCell Current cell of where the piece is located
     * @param isMoveComplete Indicates whether the movement is already done
     */
    private void animateMovement(Cell newCell, Cell currentCell, boolean isMoveComplete) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), piece);
        transition.setByX(newCell.getLayoutX() - currentCell.getLayoutX());
        transition.setByY(newCell.getLayoutY() - currentCell.getLayoutY());
        transition.play();
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                piece.setTranslateX(0);
                piece.setTranslateY(0);
                if (!isMoveComplete) {
                    movePiece(currentCell, newCell);
                }
            }
        });
    }

    /**
     * Checks whether a move to the specified cell is valid. But the actual move
     * is not performed.
     *
     * @param rank Rank of the target cell
     * @param file File of the target cell
     * @return True if move is permitted. False otherwise.
     */
    @Override
    public boolean isMovePermitted(CellInfo.Rank rank, CellInfo.File file) {
        for (IMove move : moveTypes) {
            if (move.isMoveAllowed(new Address(rank, file))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Moves the piece to the specified cell, if it is permitted.
     *
     * @param rank Rank of the target cell
     * @param file File of the target cell
     */
    @Override
    public void moveIfPermitted(CellInfo.Rank rank, CellInfo.File file) {
        if (isMovePermitted(rank, file)) {
            moveTo(rank, file);
        }
    }

}
