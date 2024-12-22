package src.main.piece;

import src.main.GamePanel;
import src.main.PieceType;

public class Knight extends Piece {
    public Knight(int color, int col, int row) {
        super(color, col, row);
        pieceType = PieceType.KNIGHT;

        if (color == GamePanel.WHITE) {
            image = getImage("/res/piece/white_knight");
        }
        else {
            image = getImage("/res/piece/black_knight");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow)) {
            if (Math.abs(targetCol - preCol) == 2 && Math.abs(targetRow - preRow) == 1 ||
                    Math.abs(targetCol - preCol) == 1 && Math.abs(targetRow - preRow) == 2 ) {
                        if (isValidSquare(targetCol, targetRow)) {
                            return true;
                        }
            }
    
        }
        return false;
    }
}
