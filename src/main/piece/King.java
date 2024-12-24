package src.main.piece;

import src.main.GamePanel;
import src.main.Enums.PieceType;

public class King extends Piece {

    public King(int color, int col, int row) {
        super(color, col, row);
        pieceType = PieceType.KING;

        switch(color) {
            case GamePanel.WHITE: image = getImage("/res/piece/white_king"); break;
            case GamePanel.BLACK: image = getImage("/res/piece/black_king"); break;
            default: break;
        }
    }

    @Override
    public Piece clone() {
        King newKing = new King(color, col, row);
        return newKing;
    }


    private boolean enemyPieceControlCastlingLine(int targetCol, int targetRow) {
        // When this piece moves left/right
        for (int c = Math.min(preCol, targetCol)+1; c < Math.max(preCol, targetCol); c++) {
            for(Piece piece : GamePanel.simPieces) {
                if (piece.controlSquare(c, targetRow) && piece.color != color) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    private boolean pieceIsOnCastlingLine(int targetCol, int targetRow) {
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

    @Override
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
            if (targetCol == preCol + 2 && targetRow == preRow && !pieceIsOnCastlingLine(7, targetRow)) {
                if (!enemyPieceControlCastlingLine(targetCol, targetRow)) {
                    for (Piece piece : GamePanel.simPieces) {
                        if (piece.col == preCol + 3 && piece.row == preRow && piece.pieceType == PieceType.ROOK) {
                            if(piece.color == GamePanel.WHITE && GamePanel.lastMove.whiteKingsideCastle || piece.color == GamePanel.BLACK && GamePanel.lastMove.blackKingsideCastle) {
                                GamePanel.castlingP = piece;
                                return true;
                            }
                        }
                    }
                }
            }

            // Queenside castling
            if (targetCol == preCol - 2 && targetRow == preRow && !pieceIsOnCastlingLine(0, targetRow) ) {
                if (!enemyPieceControlCastlingLine(targetCol, targetRow) ) {
                    for (Piece piece : GamePanel.simPieces) {
                        if (piece.col == 0 && piece.row == preRow && piece.pieceType == PieceType.ROOK) {
                            if(piece.color == GamePanel.WHITE && GamePanel.lastMove.whiteQueensideCastle || piece.color == GamePanel.BLACK && GamePanel.lastMove.blackQueensideCastle) {
                                GamePanel.castlingP = piece;
                                return true;
                            }
                        }
                    }
                }
            }
        }
    return false;
    }

    @Override
    public boolean controlSquare(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            if (Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 ||
                Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1) {
                    return true;
            }
        }
        return false;
        
    }
}
