package src.main.piece;

import src.main.GamePanel;
import src.main.LastMove;
import src.main.PieceType;

public class Pawn extends Piece {
    public Pawn(int color, int col, int row) {
        super(color, col, row);
        pieceType = PieceType.PAWN;

        if (color == GamePanel.WHITE) {
            image = getImage("/res/piece/white_pawn");
        } else {
            image = getImage("/res/piece/black_pawn");
        }
        if(color == GamePanel.WHITE && (row != 6 ) || color == GamePanel.BLACK && ((row != 1))) {
            hasMoved = true;
        }
    }

    @Override
    public boolean isValidSquare(int targetCol, int targetRow) {
        hittingP = getHittingP(targetCol, targetRow);

        if (hittingP == null) {
            if(preCol == targetCol) {
                return true;
            }
        }
        else {
            if (hittingP.color != this.color) {
                if(Math.abs(targetCol-preCol) == 1 && Math.abs(targetRow - preRow) == 1) {
                    return true;
                }
            }
            else {
                hittingP = null;
            }
        }
        return false;
    }

    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            // Movement
            int moveDirection = -1;
            if (color == GamePanel.WHITE) { //White pawns
                moveDirection = 1;
            }
            if (preRow - targetRow == moveDirection ||
                preRow - targetRow == 2*moveDirection && !hasMoved) {
                if (isValidSquare(targetCol, targetRow) && !pieceIsOnStraightLine(targetCol, targetRow)) {
                    return true;
                }
            }

            if(LastMove.pieceType == PieceType.PAWN && Math.abs(LastMove.preRow - LastMove.row) == 2) {
                if (targetCol == LastMove.col && targetRow == (LastMove.preRow+LastMove.row)/2) {
                    for (Piece piece : GamePanel.simPieces) {
                        if (piece.col == LastMove.col && piece.row == LastMove.row) {
                            this.hittingP = piece;
                            return true;
                        }
                    }
                }
            }


        }
            
            
    return false;
    }
}

