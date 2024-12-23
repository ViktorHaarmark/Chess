package src.main.piece;

import src.main.GamePanel;
import src.main.Enums.PieceType;

public class Rook extends Piece {
    public Rook(int color, int col, int row) {
        super(color, col, row);
        pieceType = PieceType.ROOK;

        switch(color) {
            case GamePanel.WHITE: image = getImage("/res/piece/white_rook"); if (row != 7 || col%7 != 0) {hasMoved = true;} break;
            case GamePanel.BLACK: image = getImage("/res/piece/black_rook"); if (row != 0 || col%7 != 0) {hasMoved = true;} break;
            default: break;
            }
    }

    @Override
    public Piece clone() {
        Rook newRook = new Rook(color, col, row);
        if (hasMoved) {
            newRook.hasMoved = true;
        }
        return newRook;
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

