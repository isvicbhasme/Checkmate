/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkmate.move;

import checkmate.Launcher;
import checkmate.design.Piece;
import checkmate.manager.RepetitionManager;
import checkmate.util.CellInfo;
import checkmate.util.PieceInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Isaac
 */
public class Pawn extends MovablePiece{
    
    private int maxMoves = 2;
    private final static boolean HORIZONTAL_MOVE_ALLOWED = false;
    private final static boolean BACKWARD_MOVE_ALLOWED = false;
    private final StraightMove straightMove;
    private final CrossMove crossMove;
    private final EnPassantMove enPassantMove;

    public Pawn(Piece piece) {
        this.piece = piece;
        straightMove = new StraightMove(piece, maxMoves, HORIZONTAL_MOVE_ALLOWED, BACKWARD_MOVE_ALLOWED);
        straightMove.setIsAttackingAllowed(false);
        crossMove = new CrossMove(piece, 1);
        crossMove.setIsOnlyAttackAllowed(true);
        crossMove.setIsBackwardMoveRestricted(true);
        enPassantMove = new EnPassantMove(piece);
        this.moveTypes.add(straightMove);
        this.moveTypes.add(crossMove);
        this.moveTypes.add(enPassantMove);
    }

    @Override
    public void moveTo(CellInfo.Rank rank, CellInfo.File file) {
        if(Math.abs(piece.getRank().ordinal() - rank.ordinal()) == 2) {
            ((checkmate.design.Pawn)piece).setIsEnpassantPossible(true);
        } else if (((checkmate.design.Pawn)piece).isEnpassantPossible()) {
            ((checkmate.design.Pawn)piece).setIsEnpassantPossible(false);
        }
        super.moveTo(rank, file);
        this.maxMoves = 1;
        straightMove.setMaxSteps(maxMoves);
        if(isPromoteApplicable(rank)) {
            initiatePromotionDialog();
        }
    }
    
    private boolean isPromoteApplicable(CellInfo.Rank rank) {
        return rank == CellInfo.Rank.ONE || rank == CellInfo.Rank.EIGHT;
    }
    
    private void initiatePromotionDialog() {
        final Stage promoteDialogStage = new Stage(StageStyle.DECORATED);
        promoteDialogStage.initModality(Modality.WINDOW_MODAL);
        promoteDialogStage.initOwner(Launcher.primaryWindow);
        
        DialogBox dialogBox = new DialogBox();
        Scene dialogScene = new Scene(dialogBox);
        promoteDialogStage.setScene(dialogScene);
        promoteDialogStage.setTitle("Pawn Promotion");
        promoteDialogStage.show();
    }
    
    private class DialogBox extends VBox {
        
        private Text selectedPieceText = null;

        public DialogBox() {
            super(10);
            setAlignment(Pos.CENTER);
            addPiecesToFirstRow();
            addButtonsToSecondRow();
        }

        private void addPiecesToFirstRow() {
            HBox hbox = new HBox(15);
            Text rookOption = getPieceGraphics(PieceInfo.Name.ROOK);
            Text bishopOption = getPieceGraphics(PieceInfo.Name.BISHOP);
            Text knightOption = getPieceGraphics(PieceInfo.Name.KNIGHT);
            Text queenOption = getPieceGraphics(PieceInfo.Name.QUEEN);
            hbox.getChildren().addAll(rookOption, bishopOption, knightOption, queenOption);
            this.getChildren().add(hbox);
        }
        
        private void addButtonsToSecondRow() {
            HBox hbox = new HBox(15);
            hbox.getChildren().add(setupOkButton());
            hbox.getChildren().add(setupCancelButton());
            setMargin(hbox, new Insets(10));
            hbox.setAlignment(Pos.CENTER);
            this.getChildren().add(hbox);
        }

        private Button setupCancelButton() {
            Button cancelBtn = new Button("Cancel");
            cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    closeWindowOfButton((Button)event.getSource());
                }
            });
            return cancelBtn;
        }

        private Button setupOkButton() {
            Button okBtn = new Button("Ok");
            okBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    PieceInfo.Type pieceType = (PieceInfo.Type)selectedPieceText.getUserData();
                    IMovable moveHandler = getNewMoveHandler(pieceType);
                    ((checkmate.design.Pawn)piece).promote(moveHandler, pieceType.getUnicodeChar());
                    RepetitionManager.getInstance().recreatePawnBitString(piece.getPieceTypeForHashing(), piece.getRank(), piece.getFile());
                    closeWindowOfButton((Button)event.getSource());
                }

                private IMovable getNewMoveHandler(PieceInfo.Type pieceType) throws IllegalStateException {
                    IMovable moveHandler = null;
                    switch(pieceType.getPieceName()) {
                        case BISHOP: 
                            moveHandler = new checkmate.move.Bishop(piece);
                            break;
                        case KNIGHT:
                            moveHandler = new checkmate.move.Knight(piece);
                            break;
                        case ROOK:
                            moveHandler = new checkmate.move.Rook(piece);
                            break;
                        case QUEEN:
                            moveHandler = new checkmate.move.Queen(piece);
                            break;
                        default:
                            throw new IllegalStateException("Promotion to "+pieceType+" is not a legal operation in chess");
                    }
                    return moveHandler;
                }
            });
            return okBtn;
        }
        
        private void closeWindowOfButton(Button btn) {
            Stage dialogStage = (Stage)btn.getScene().getWindow();
            dialogStage.close();
        }
        
        private Text getPieceGraphics(PieceInfo.Name pieceName) {
            char unicode = 0;
            PieceInfo.Type pieceType;
            switch (pieceName) {
                case BISHOP:
                    pieceType = piece.isWhitePiece()? PieceInfo.Type.WHITE_BISHOP: PieceInfo.Type.BLACK_BISHOP;
                    break;
                case KNIGHT:
                    pieceType = piece.isWhitePiece()? PieceInfo.Type.WHITE_KNIGHT : PieceInfo.Type.BLACK_KNIGHT;
                    break;
                case ROOK:
                    pieceType = piece.isWhitePiece()? PieceInfo.Type.WHITE_ROOK : PieceInfo.Type.BLACK_ROOK;
                    break;
                case QUEEN:
                    pieceType = piece.isWhitePiece()? PieceInfo.Type.WHITE_QUEEN : PieceInfo.Type.BLACK_QUEEN;
                    break;
                default:
                    throw new IllegalStateException("Promotion of "+pieceName+" is not a legal operation in chess");
            }
            unicode = pieceType.getUnicodeChar();
            Text text = new Text(unicode+"");
            text.setUserData(pieceType);
            text.setFont(Font.font(Launcher.resource.getIntConfig("Piece.Font.size")));
            text.setFill(Color.BLACK);
            text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                
                @Override
                public void handle(MouseEvent event) {
                    if(selectedPieceText != null) {
                        selectedPieceText.setFill(Color.BLACK);
                        selectedPieceText = null;
                    }
                    selectedPieceText = ((Text)event.getSource());
                    selectedPieceText.setFill(Color.FORESTGREEN);
                }
            });
            return text;
        }
    }
}
