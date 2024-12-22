package src.main.piece;

import src.main.GamePanel;
import src.main.LastMove;
import src.main.PieceType;

public class Pawn extends Piece {
    int moveDirection;
    public Pawn(int color, int col, int row) {
        super(color, col, row);
        pieceType = PieceType.PAWN;

        if (color == GamePanel.WHITE) {
            image = getImage("/res/piece/white_pawn");
            moveDirection = 1;
        } else {
            image = getImage("/res/piece/black_pawn");
            moveDirection = -1;
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
                if(Math.abs(targetCol-preCol) == 1 && targetRow - preRow == moveDirection) {
                    return true;
                }
            }
            else {
                hittingP = null;
            }
        }
        return false;
    }

    @Override
    public boolean canMove(int targetCol, int targetRow) {

        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            // Movement
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

    @Override
    public boolean controlSquare(int targetCol, int targetRow) {
        if (Math.abs(targetCol - col) == 1 && targetRow == row - moveDirection) {
            return true;
        }
        return false;
    }
}

