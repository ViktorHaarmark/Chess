package src.main.piece;

import src.main.GamePanel;
import src.main.PieceType;

public class King extends Piece {

    public King(int color, int col, int row) {
        super(color, col, row);
        pieceType = PieceType.KING;

        if (color == GamePanel.WHITE) {
            image = getImage("/res/piece/white_king");
        }
        else {
            image = getImage("/res/piece/black_king");
        }
        if(color == 0 && (row != 7 || col != 4) || color == 1 && (row != 0 || col != 4)) {
            hasMoved = true;
        }
    }


    private boolean enemyPieceControlStraightLine(int targetCol, int targetRow) {

        // When this piece moves left/right
        for (int c = Math.min(preCol, targetCol)+1; c < Math.max(preCol, targetCol); c++) {
            for(Piece piece : GamePanel.simPieces) {
                if (piece.canMove(c, targetRow) && piece.color != color) {
                    return true;
                }
            }
        }
        return false;
    }

    
    @Override
    protected boolean pieceIsOnStraightLine(int targetCol, int targetRow) {

        // When the king checks for castling, he does not consider himself in the way.
        for (int c = Math.min(preCol, targetCol)+1; c < Math.max(preCol, targetCol); c++) {
            for(Piece piece : GamePanel.pieces) {
                if (piece.preCol == c && piece.preRow == targetRow) {
                    hittingP = piece;
                    return true;
                }
            }
        }
        return false;
    }

public boolean canMove(int targetCol, int targetRow) {
    if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
        // Normal movement
        if (Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 ||
                Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1) {
                    if (isValidSquare(targetCol, targetRow)) {
                        return true;
                    }
                }
        
        // Kingside castling
        if (targetCol == preCol + 2 && targetRow == preRow && !pieceIsOnStraightLine(targetCol, targetRow)) {
            if (!enemyPieceControlStraightLine(targetCol, targetRow)) {
                for (Piece piece : GamePanel.simPieces) {
                    if (piece.col == preCol + 3 && piece.row == preRow && !piece.hasMoved && piece.pieceType == PieceType.ROOK) {
                        GamePanel.castlingP = piece;
                        return true;
                    }
                }
            }
        }

        // Queenside castling
        if (targetCol == preCol - 2 && targetRow == preRow && !pieceIsOnStraightLine(targetCol-1, targetRow) ) {
            if (!enemyPieceControlStraightLine(targetCol, targetRow) ) {
                for (Piece piece : GamePanel.simPieces) {
                    if (piece.col == 0 && piece.row == preRow && !piece.hasMoved && piece.pieceType == PieceType.ROOK) {
                        GamePanel.castlingP = piece;
                        return true;
                    }
                }

            }
        }
    }
    

    return false;
}
}