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
    public int castlingDirection;
    public boolean whiteKingsideCastle = true;
    public boolean whiteQueensideCastle = true;
    public boolean blackKingsideCastle = true;
    public boolean blackQueensideCastle= true;

    public Move() {
    }

    public Move(int col, int row, int preCol, int preRow, PieceType pieceType, Piece capturedPiece,  boolean wkc, boolean wqc, boolean bkc, boolean bqc) {
        this.col = col; this.row = row;
        this.preCol = preCol; this.preRow = preRow;
        this.pieceType = pieceType;
        this.capturedPiece = capturedPiece;
        this.whiteKingsideCastle = wkc; this.whiteQueensideCastle = wqc; this.blackKingsideCastle = bkc; this.blackQueensideCastle = bqc;
    }

    public void undo() {
        for (Piece piece : GamePanel.pieces) {
            if (piece.col == col && piece.row == row) { //Castling rook needs to be resolved
                piece.preCol = preCol;
                piece.preRow = preRow;
                piece.resetPosition();
            }
        }
        if (capturedPiece != null) {
            GamePanel.pieces.add(capturedPiece);
        }
        System.out.println(castlingDirection);
        undoCastling();
        GamePanel.copyPieces(GamePanel.pieces, GamePanel.simPieces);
        GamePanel.changePlayer();
        GamePanel.moveList.removeLast();
        if (!GamePanel.moveList.isEmpty()) {
            GamePanel.lastMove = GamePanel.moveList.getLast();
        } else {
            GamePanel.lastMove = new Move(); 
        }
    }

    private void undoCastling() {
        if (castlingDirection == 1) {//Kingside castling
            for (Piece rook : GamePanel.pieces) {
                if (rook.col == 5 && rook.row == preRow) {
                    rook.preCol = 7;
                    rook.resetPosition();
                }
            }
        }
        else if (castlingDirection == -1) { //Queenside castling
            for (Piece rook : GamePanel.pieces) {
                if (rook.col == 3 && rook.row == preRow) {
                    rook.preCol = 0;
                    rook.resetPosition();
                }
            }
        }
    }





}
