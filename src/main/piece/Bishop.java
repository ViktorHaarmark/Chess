package src.main.piece;

import src.main.GamePanel;
import src.main.Enums.PieceType;

public class Bishop extends Piece {
    public Bishop(int color, int col, int row) {
        super(color, col, row);
        pieceType = PieceType.BISHOP;
        switch(color) {
            case GamePanel.WHITE: image = getImage("/res/piece/white_bishop"); break;
            case GamePanel.BLACK: image = getImage("/res/piece/black_bishop"); break;
            default: break;
            }
        }

    @Override
    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            if (Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)) {
                if ( (isValidSquare(targetCol, targetRow)) && !pieceIsOnDiagonalLine(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean controlSquare(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            if (Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)) {
                if ( !pieceIsOnDiagonalLine(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
