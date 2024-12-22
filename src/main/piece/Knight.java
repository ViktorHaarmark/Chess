package src.main.piece;

import src.main.GamePanel;
import src.main.PieceType;

public class Knight extends Piece {
    public Knight(int color, int col, int row) {
        super(color, col, row);
        pieceType = PieceType.KNIGHT;
        switch(color) {
            case GamePanel.WHITE: image = getImage("/res/piece/white_knight"); break;
            case GamePanel.BLACK: image = getImage("/res/piece/black_knight"); break;
            default: break;
            }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow)) {
            if (Math.abs(targetCol - preCol) == 2 && Math.abs(targetRow - preRow) == 1 ||
                    Math.abs(targetCol - preCol) == 1 && Math.abs(targetRow - preRow) == 2 ) {
                        if ( isValidSquare(targetCol, targetRow) ) {
                            return true;
                        }
            }
    
        }
        return false;
    }

    public boolean controlSquare(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow)) {
            if (Math.abs(targetCol - preCol) == 2 && Math.abs(targetRow - preRow) == 1 ||
                    Math.abs(targetCol - preCol) == 1 && Math.abs(targetRow - preRow) == 2 ) {
                        return true;
            }
    
        }
        return false;
    }
}
