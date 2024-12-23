package src.main;

import java.util.ArrayList;

import src.main.piece.Bishop;
import src.main.piece.King;
import src.main.piece.Knight;
import src.main.piece.Pawn;
import src.main.piece.Piece;
import src.main.piece.Queen;
import src.main.piece.Rook;

public class BoardSetup {
    public static final int WHITE = -1;
    public static final int BLACK = 1;

    public BoardSetup() {
    }

    // Normal setup
    public static void setPieces(ArrayList<Piece> pieceList) {

        // White pieces
        pieceList.add(new Pawn(WHITE, 0, 6));
        pieceList.add(new Pawn(WHITE, 1, 6));
        pieceList.add(new Pawn(WHITE, 2, 6));
        pieceList.add(new Pawn(WHITE, 3, 6));
        pieceList.add(new Pawn(WHITE, 4, 6));
        pieceList.add(new Pawn(WHITE, 5, 6));
        pieceList.add(new Pawn(WHITE, 6, 6));
        pieceList.add(new Pawn(WHITE, 7, 6));
        pieceList.add(new Rook(WHITE, 0, 7));
        pieceList.add(new Rook(WHITE, 7, 7));
        pieceList.add(new Knight(WHITE, 1, 7));
        pieceList.add(new Knight(WHITE, 6, 7));
        pieceList.add(new Bishop(WHITE, 2, 7));
        pieceList.add(new Bishop(WHITE, 5, 7));
        pieceList.add(new Queen(WHITE, 3, 7));
        pieceList.add(new King(WHITE, 4, 7));

        // Black pieces
        pieceList.add(new Pawn(BLACK, 0, 1));
        pieceList.add(new Pawn(BLACK, 1, 1));
        pieceList.add(new Pawn(BLACK, 2, 1));
        pieceList.add(new Pawn(BLACK, 3, 1));
        pieceList.add(new Pawn(BLACK, 4, 1));
        pieceList.add(new Pawn(BLACK, 5, 1));
        pieceList.add(new Pawn(BLACK, 6, 1));
        pieceList.add(new Pawn(BLACK, 7, 1));
        pieceList.add(new Rook(BLACK, 0, 0));
        pieceList.add(new Rook(BLACK, 7, 0));
        pieceList.add(new Knight(BLACK, 1, 0));
        pieceList.add(new Knight(BLACK, 6, 0));
        pieceList.add(new Bishop(BLACK, 2, 0));
        pieceList.add(new Bishop(BLACK, 5, 0));
        pieceList.add(new Queen(BLACK, 3, 0));
        pieceList.add(new King(BLACK, 4, 0));
  
    }

    public static void checkMateTest(ArrayList<Piece> pieceList) {
        
        pieceList.add(new Queen(WHITE, 0, 6));
        pieceList.add(new King(WHITE, 1, 5));
        pieceList.add(new King(BLACK, 2, 7));
    }

    public static void castlingTest(ArrayList<Piece> pieceList) {
        pieceList.add(new King(WHITE, 4, 7));
        pieceList.add(new King(BLACK, 4, 0));
        pieceList.add(new Rook(BLACK, 0, 0));
        pieceList.add(new Rook(BLACK, 7, 0));
        pieceList.add(new Rook(BLACK, 4, 0));
        pieceList.add(new Rook(WHITE, 0, 7));
        pieceList.add(new Rook(WHITE, 7, 7));

    }

}
