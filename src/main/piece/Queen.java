package src.main.piece;

import src.main.GamePanel;
import src.main.PieceType;

public class Queen extends Piece {
    public Queen(int color, int col, int row) {
        super(color, col, row);
        pieceType = PieceType.QUEEN;

        if (color == GamePanel.WHITE) {
            image = getImage("/res/piece/white_queen");
        }
        else {
            image = getImage("/res/piece/black_queen");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
            // Rook move
            if (targetCol == preCol || targetRow == preRow) {
                if ( !pieceIsOnStraightLine(targetCol, targetRow)) {
                    return true;
                }
            }
            // bishop move
            if (Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)) {
                if ( isValidSquare(targetCol, targetRow) && !pieceIsOnDiagonalLine(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        return false;
    }
}

