package src.main;

import java.util.List;

import src.main.piece.Bishop;
import src.main.piece.King;
import src.main.piece.Knight;
import src.main.piece.Pawn;
import src.main.piece.Piece;
import src.main.piece.Queen;
import src.main.piece.Rook;

public class BoardSetup {

    public BoardSetup() {
    }

    // Normal setup
    public static void setPieces(List<Piece> pieceList) {
        // White pieces
        pieceList.add(new Pawn(GamePanel.WHITE, 0, 6));
        pieceList.add(new Pawn(GamePanel.WHITE, 1, 6));
        pieceList.add(new Pawn(GamePanel.WHITE, 2, 6));
        pieceList.add(new Pawn(GamePanel.WHITE, 3, 6));
        pieceList.add(new Pawn(GamePanel.WHITE, 4, 6));
        pieceList.add(new Pawn(GamePanel.WHITE, 5, 6));
        pieceList.add(new Pawn(GamePanel.WHITE, 6, 6));
        pieceList.add(new Pawn(GamePanel.WHITE, 7, 6));
        pieceList.add(new Rook(GamePanel.WHITE, 0, 7));
        pieceList.add(new Rook(GamePanel.WHITE, 7, 7));
        pieceList.add(new Knight(GamePanel.WHITE, 1, 7));
        pieceList.add(new Knight(GamePanel.WHITE, 6, 7));
        pieceList.add(new Bishop(GamePanel.WHITE, 2, 7));
        pieceList.add(new Bishop(GamePanel.WHITE, 5, 7));
        pieceList.add(new Queen(GamePanel.WHITE, 3, 7));
        pieceList.add(new King(GamePanel.WHITE, 4, 7));

        // Black pieces
        pieceList.add(new Pawn(GamePanel.BLACK, 0, 1));
        pieceList.add(new Pawn(GamePanel.BLACK, 1, 1));
        pieceList.add(new Pawn(GamePanel.BLACK, 2, 1));
        pieceList.add(new Pawn(GamePanel.BLACK, 3, 1));
        pieceList.add(new Pawn(GamePanel.BLACK, 4, 1));
        pieceList.add(new Pawn(GamePanel.BLACK, 5, 1));
        pieceList.add(new Pawn(GamePanel.BLACK, 6, 1));
        pieceList.add(new Pawn(GamePanel.BLACK, 7, 1));
        pieceList.add(new Rook(GamePanel.BLACK, 0, 0));
        pieceList.add(new Rook(GamePanel.BLACK, 7, 0));
        pieceList.add(new Knight(GamePanel.BLACK, 1, 0));
        pieceList.add(new Knight(GamePanel.BLACK, 6, 0));
        pieceList.add(new Bishop(GamePanel.BLACK, 2, 0));
        pieceList.add(new Bishop(GamePanel.BLACK, 5, 0));
        pieceList.add(new Queen(GamePanel.BLACK, 3, 0));
        pieceList.add(new King(GamePanel.BLACK, 4, 0));
    }

    public static void checkMateTest(List<Piece> pieceList) {
        pieceList.add(new Queen(GamePanel.WHITE, 0, 6));
        pieceList.add(new King(GamePanel.WHITE, 1, 5));
        pieceList.add(new King(GamePanel.BLACK, 2, 7));
    }

    public static void castlingTest(List<Piece> pieceList) {
        pieceList.add(new King(GamePanel.WHITE, 4, 7));
        pieceList.add(new King(GamePanel.BLACK, 4, 0));
        pieceList.add(new Rook(GamePanel.BLACK, 0, 0));
        pieceList.add(new Rook(GamePanel.BLACK, 7, 0));
        pieceList.add(new Rook(GamePanel.WHITE, 4, 4));
        pieceList.add(new Pawn(GamePanel.BLACK, 4, 3));
        pieceList.add(new Rook(GamePanel.WHITE, 0, 7));
        pieceList.add(new Rook(GamePanel.WHITE, 7, 7));

    }

}
