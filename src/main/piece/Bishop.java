package src.main.piece;

import src.main.GamePanel;
import src.main.PieceType;

public class Bishop extends Piece {
    public Bishop(int color, int col, int row) {
        super(color, col, row);
        pieceType = PieceType.BISHOP;

        if (color == GamePanel.WHITE) {
            image = getImage("/res/piece/white_bishop");
        }
        else {
            image = getImage("/res/piece/black_bishop");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            if (Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)) {
                if ( isValidSquare(targetCol, targetRow) && !pieceIsOnDiagonalLine(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        return false;
    }
    //&& !pieceIsOnDiagonalLine(targetCol, targetRow)
}
