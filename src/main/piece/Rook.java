package src.main.piece;

import src.main.GamePanel;
import src.main.PieceType;

public class Rook extends Piece {
    public Rook(int color, int col, int row) {
        super(color, col, row);
        pieceType = PieceType.ROOK;

        if (color == GamePanel.WHITE) {
            image = getImage("/res/piece/white_rook");
        }
        else {
            image = getImage("/res/piece/black_rook");
        }

        if((color == GamePanel.WHITE && (row != 7 || col%7 != 0)) || (color == 1 && (row != 0 || col%7 != 0))) {
            hasMoved = true;
        }
    }

    @Override
    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
            if (targetCol == preCol || targetRow == preRow) {
                if (isValidSquare(targetCol, targetRow) && !pieceIsOnStraightLine(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean controlSquare(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
            if (targetCol == preCol || targetRow == preRow) {
                if ( !pieceIsOnStraightLine(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        return false;
    }
}

