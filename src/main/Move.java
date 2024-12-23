package src.main;

import src.main.Enums.PieceType;
import src.main.piece.Piece;

public class Move {
    public int col;
    public int row;
    public int preCol;
    public int preRow;
    public PieceType pieceType;
    public Piece capturedPiece;

    public Move() {
    }

    public Move(int col, int row, int preCol, int preRow, PieceType pieceType, Piece capturedPiece) {
        this.col = col; this.row = row;
        this.preCol = preCol; this.preRow = preRow;
        this.pieceType = pieceType;
        this.capturedPiece = capturedPiece;
    }

    public void undo() {
        for (Piece piece : GamePanel.pieces) {
            if (piece.col == col && piece.row == row) { //hasMoved need to be solved, also castling
                piece.preCol = preCol;
                piece.preRow = preRow;
                piece.resetPosition();
            }
        }
        if (capturedPiece != null) {
            GamePanel.pieces.add(capturedPiece);
        }
        GamePanel.copyPieces(GamePanel.pieces, GamePanel.simPieces);
        GamePanel.changePlayer();
        GamePanel.moveList.removeLast();
        if (!GamePanel.moveList.isEmpty()) {
            GamePanel.lastMove = GamePanel.moveList.getLast();
        } else {
            GamePanel.lastMove = new Move(); // Handle empty list case
        }
    }




}
