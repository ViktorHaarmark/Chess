package src.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import src.main.piece.Bishop;
import src.main.piece.King;
import src.main.piece.Knight;
import src.main.piece.Pawn;
import src.main.piece.Piece;
import src.main.piece.Queen;
import src.main.piece.Rook;

public class GamePanel extends JPanel implements Runnable{

    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    final int FPS = 60;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();

    // Pieces
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    ArrayList<Piece> promoPieces = new ArrayList<>();
    Piece activeP;
    public static Piece castlingP;

    // Color
    public static final int WHITE = -1;
    public static final int BLACK = 1;
    int currentColor = WHITE;

    // Booleans
    boolean canMove;
    boolean validSquare;
    boolean promotion;
    boolean checkmate;
    
    public static LastMove lastMove = new LastMove();


    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        setPieces();
        copyPieces(pieces, simPieces);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * 
     */
    public void setPieces() {

        // White pieces
        pieces.add(new Pawn(WHITE, 0, 6));
        pieces.add(new Pawn(WHITE, 1, 6));
        pieces.add(new Pawn(WHITE, 2, 6));
        pieces.add(new Pawn(WHITE, 3, 6));
        pieces.add(new Pawn(WHITE, 4, 6));
        pieces.add(new Pawn(WHITE, 5, 6));
        pieces.add(new Pawn(WHITE, 6, 6));
        pieces.add(new Pawn(WHITE, 7, 6));
        pieces.add(new Rook(WHITE, 0, 7));
        pieces.add(new Rook(WHITE, 7, 7));
        pieces.add(new Knight(WHITE, 1, 7));
        pieces.add(new Knight(WHITE, 6, 7));
        pieces.add(new Bishop(WHITE, 2, 7));
        pieces.add(new Bishop(WHITE, 5, 7));
        pieces.add(new Queen(WHITE, 3, 7));
        pieces.add(new King(WHITE, 4, 7));

        // Black pieces
        pieces.add(new Pawn(BLACK, 0, 1));
        pieces.add(new Pawn(BLACK, 1, 1));
        pieces.add(new Pawn(BLACK, 2, 1));
        pieces.add(new Pawn(BLACK, 3, 1));
        pieces.add(new Pawn(BLACK, 4, 1));
        pieces.add(new Pawn(BLACK, 5, 1));
        pieces.add(new Rook(BLACK, 6, 1));
        pieces.add(new Pawn(BLACK, 7, 1));
        pieces.add(new Rook(BLACK, 0, 0));
        pieces.add(new Rook(BLACK, 7, 0));
        pieces.add(new Knight(BLACK, 1, 0));
        pieces.add(new Knight(BLACK, 6, 0));
        pieces.add(new Bishop(BLACK, 2, 0));
        pieces.add(new Bishop(BLACK, 5, 0));
        pieces.add(new Queen(BLACK, 3, 0));
        pieces.add(new King(BLACK, 4, 0));
  
    }

    public void setPiecesCheckmate() {
        pieces.add(new Queen(WHITE, 0, 6));
        pieces.add(new King(WHITE, 1, 5));
        pieces.add(new King(BLACK, 2, 7));

    }
    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();
        for (int i = 0; i < source.size(); i++) {
            target.add(source.get(i));
        }
    }

    @Override
    public void run() {
        // Game loop, runs every 1/60 of a second
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

        }
    }

    private void update() {

        if (promotion) {
            promoting();
        }

        if (checkmate) {
            System.out.println("checkmate!");
        }
        
        else{
            if (mouse.pressed) {
                if (activeP == null) {
                    // If there is no active piece, check if a piece can be picked up.
                    for (Piece piece : simPieces) {
                        if (piece.color == currentColor &&
                            piece.col == mouse.x/Board.SQUARE_SIZE &&
                            piece.row == mouse.y/Board.SQUARE_SIZE) {
                                activeP = piece;
                        }
                    }
                }
                else {
                    // If the player is holding a piece, simulate the move
                    simulate();
                }
            }
            // Mouse button released
            if (mouse.pressed == false) {
                if (activeP != null) {
                    
                    if (validSquare) {//If move is being made
                        makeMove();
                    } 
                    else {
                        // Restore original list
                        copyPieces(pieces, simPieces);
                        activeP.resetPosition();
                        activeP = null;
                    }
                }
            }
        }
    }

    private void simulate() {

        // Reset the variables in every loop
        canMove = false;
        validSquare = false;
        castlingP = null;
        // Reset the piece list in every loop
        copyPieces(pieces, simPieces);

        // While a piece is being held, update the position
        activeP.x = mouse.x - Board.HALF_SQUARE_SIZE;
        activeP.y = mouse.y - Board.HALF_SQUARE_SIZE;
        activeP.col = activeP.getCol(activeP.x);
        activeP.row = activeP.getRow(activeP.y);

        // Check if a piece is hovering over a reachable square
        if(activeP.canMove(activeP.col, activeP.row) ) {
            // If taking a piece, remove it from the board
            if (activeP.hittingP != null) {
                simPieces.remove(activeP.hittingP.getIndex());
            }

            if(!isKingInCheck(activeP.color)) {
                canMove = true;
    
                validSquare = true;
            }
        }
    }

    private void makeMove() {
        // Update the piece list if something is being captured
        copyPieces(simPieces, pieces);

        // move the piece
        LastMove.col = activeP.col; LastMove.row = activeP.row; LastMove.preCol = activeP.preCol; LastMove.preRow = activeP.preRow;
        LastMove.pieceType = activeP.pieceType;

        doCastling();
        activeP.updatePosition();
        activeP.hasMoved = true;

        if(canPromote()) {
            promotion = true;
        }

        if(isCheckmate(currentColor * (-1))) {
            checkmate = true;
        }

        else {
            changePlayer();
        }
    }

    private void doCastling() {
        if (castlingP != null) {
            if (castlingP.col == 0) {
                castlingP.col = 3;
            }
            if (castlingP.col == 7) {
                castlingP.col = 5;
            }
            castlingP.x = castlingP.getX(castlingP.col);
            castlingP.hasMoved = true;
            castlingP.updatePosition();
        }
    }

    private boolean canPromote() {
        if (activeP.pieceType == PieceType.PAWN) {
            if(activeP.color == WHITE && activeP.row == 0 ||
                activeP.color == BLACK && activeP.row == 7) {
                    promoPieces.clear();
                    promoPieces.add(new Rook(currentColor, 9, 2));
                    promoPieces.add(new Knight(currentColor, 9, 3));
                    promoPieces.add(new Bishop(currentColor, 9, 4));
                    promoPieces.add(new Queen(currentColor, 9, 5));
                    return true;
                }
        }
        return false;
    }

    private boolean isKingInCheck(int color) {
        for (Piece king : GamePanel.simPieces) {
            if (king.pieceType == PieceType.KING && king.color == color) {
                for (Piece piece : GamePanel.simPieces) {
                    if (piece.controlSquare(king.col, king.row)  && piece.color != king.color) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCheckmate(int color) {
        if (isKingInCheck(color)) {
            for (Piece piece : GamePanel.pieces) {
                if (piece.color == color) {
                    for (int c = 0; c < 8; c++) {
                        for (int r = 0; r<8; r++) {
                            if ((piece.canMove(c, r))) {
                                // Simulate piece moving to that square
                                piece.col = c; piece.row = r;
                                if (piece.hittingP != null) {
                                    simPieces.remove(piece.hittingP.getIndex());
                                }
                                if (!isKingInCheck(color)) {
                                    piece.hittingP = null;
                                    piece.col = piece.preCol; piece.row = piece.preRow;
                                    copyPieces(pieces, simPieces);
                                    return false;
                                }
                                piece.hittingP = null;
                                piece.col = piece.preCol; piece.row = piece.preRow;
                                copyPieces(pieces, simPieces); 
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean isStalemate() { //Missing implementation
        return false;
    }

    private void promoting() {
        if (mouse.pressed) {
            for (Piece piece : promoPieces) {
                if(piece.col == mouse.x/Board.SQUARE_SIZE && piece.row == mouse.y/Board.SQUARE_SIZE) {
                    switch(piece.pieceType) {
                        case ROOK: simPieces.add(new Rook(currentColor, activeP.col, activeP.row)); break;
                        case KNIGHT: simPieces.add(new Knight(currentColor, activeP.col, activeP.row)); break;
                        case BISHOP: simPieces.add(new Bishop(currentColor, activeP.col, activeP.row)); break;
                        case QUEEN: simPieces.add(new Queen(currentColor, activeP.col, activeP.row)); break;
                        default: break;
                    }
                    simPieces.remove(activeP.getIndex());
                    copyPieces(simPieces, pieces);
                    activeP = null;
                    promotion = false;
                    changePlayer();
                }
            }

        }
    }
    

     private void changePlayer() {
        currentColor = currentColor * (-1);
        activeP = null;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        // Board
        board.draw(g2);

        if (!(LastMove.preCol == LastMove.col && LastMove.preRow == LastMove.row) ) {
            g2.setColor(Color.blue);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
            g2.fillRect(LastMove.col*Board.SQUARE_SIZE, LastMove.row*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
            g2.fillRect(LastMove.preCol*Board.SQUARE_SIZE, LastMove.preRow*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        // Mark a king in check
        if (isKingInCheck(currentColor)) {
            g2.setColor(Color.red);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
            for (Piece king : GamePanel.simPieces) {
                if (king.pieceType == PieceType.KING && king.color == currentColor){
                    g2.fillOval(king.preCol*Board.SQUARE_SIZE, king.preRow*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                }
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        // Pieces
        for (Piece p : simPieces) {
            p.draw(g2);
        }

        // Mark the square a piece is going to
        if (activeP != null) {
            if (canMove) {
                g2.setColor(Color.blue);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2.fillRect(activeP.col*Board.SQUARE_SIZE, activeP.row*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }
            activeP.draw(g2);
        }


        //Status message
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Book Antiqua", Font.PLAIN, 40));
        g2.setColor(Color.WHITE);

        if (promotion) {
            g2.drawString("Promote to", 840, 200);
            for (Piece piece : promoPieces) {
                g2.drawImage(piece.image, piece.getX(piece.col), piece.getY(piece.row),
                    Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
            }
        }
        else {
            if (isKingInCheck(currentColor)) {
                g2.setFont(new Font("Book Antiqua", Font.PLAIN, 30));
                g2.setColor(Color.RED);
                g2.drawString("You are in check", 840, 500);
                g2.setFont(new Font("Book Antiqua", Font.PLAIN, 40));
            }
            g2.setColor(Color.WHITE);
            if (currentColor == WHITE) {
                g2.drawString("White's turn", 840, 200);
            }
            else if (currentColor == BLACK) {
                g2.drawString("Black's turn", 840, 200);
            }
        }
    }

}
