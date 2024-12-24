package src.main.piece;

import src.main.GamePanel;
import src.main.Enums.PieceType;

public class Queen extends Piece {
    public Queen(int color, int col, int row) {
        super(color, col, row);
        pieceType = PieceType.QUEEN;

        switch(color) {
            case GamePanel.WHITE: image = getImage("/res/piece/white_queen"); break;
            case GamePanel.BLACK: image = getImage("/res/piece/black_queen"); break;
            default: break;
            }
    }

    @Override
    public Piece clone() {
        Queen newQueen = new Queen(color, col, row);
        return newQueen;
    }

    @Override
    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            // Rook move
            if (targetCol == preCol || targetRow == preRow) {
                if ( isValidSquare(targetCol, targetRow) && !pieceIsOnStraightLine(targetCol, targetRow)) {
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

    @Override
    public boolean controlSquare(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
            // Rook move
            if (targetCol == preCol || targetRow == preRow) {
                if ( !pieceIsOnStraightLine(targetCol, targetRow)) {
                    return true;
                }
            }
            // bishop move
            if (Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)) {
                if ( !pieceIsOnDiagonalLine(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        return false;
    }
}

