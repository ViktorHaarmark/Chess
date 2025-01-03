package src.main.piece;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.main.ChessBoard;
import src.main.GamePanel;
import src.main.Enums.PieceType;

public abstract class Piece {
    public PieceType pieceType;
    public BufferedImage image;
    public int x, y;
    public int col, row, preCol, preRow;
    public int color;
    public Piece hittingP;

    public Piece(int color, int col, int row) {
        this.color = color;
        this.col = col; this.row = row;
        this.x = getX(col); this.y = getY(row);
        this.preCol = col; this.preRow = row;
    }

    public BufferedImage getImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public int getX(int col) {
        return col * ChessBoard.SQUARE_SIZE;
    }

    public int getY(int row) {
        return row * ChessBoard.SQUARE_SIZE;
    }

    public int getCol(int x) {
        return (x+ChessBoard.HALF_SQUARE_SIZE)/(ChessBoard.SQUARE_SIZE);
    }

    public int getRow(int x) {
        return (y+ChessBoard.HALF_SQUARE_SIZE)/(ChessBoard.SQUARE_SIZE);
    }

    public int getIndex() {
        for (int index = 0; index < GamePanel.simPieces.size(); index++) {
            if (GamePanel.simPieces.get(index) == this) {
                return index;
            }
        }
        return 0;
    }

    public void updatePosition() {
        x = getX(col);
        y = getY(row);
        preCol = getCol(x);
        preRow = getRow(y);
    }

    public void resetPosition() {
        col = preCol;
        row = preRow;
        x = getX(col);
        y = getY(row);
    }

    public abstract Piece clone();

    public abstract boolean canMove(int targetCol, int targetRow);
    

    public abstract boolean controlSquare( int targetCol, int targetRow);

    public boolean isWithinBoard(int targetCol, int targetRow) {
        if (targetCol >= 0 && targetCol <= 7 && targetRow >= 0 && targetRow <= 7) {
            return true; 
        }
        return false;
    }

    public boolean isSameSquare(int targetCol, int targetRow) {
        
        if (targetCol == preCol && targetRow == preRow) {
            return true;
        }
        return false;
    }

    public Piece getHittingP(int targetCol, int targetRow) {
        for (Piece piece : GamePanel.simPieces) {
            if (piece.col == targetCol && piece.row == targetRow && piece != this) {
                return piece;
            }
        }
        return null;
    }

    public boolean isValidSquare(int targetCol, int targetRow) {
        hittingP = getHittingP(targetCol, targetRow);
    
        if (hittingP == null) {
            return true;
        }
        else {
            if (hittingP.color != this.color) {
                return true;
            }
            else {
                hittingP = null;
            }
        }
        return false;
    }

    protected boolean pieceIsOnStraightLine(int targetCol, int targetRow) {

        // When this piece moves left/right
        for (int c = Math.min(preCol, targetCol)+1; c < Math.max(preCol, targetCol); c++) {
            for(Piece piece : GamePanel.pieces) {
                if (piece.col == c && piece.row == targetRow) {
                    hittingP = piece;
                    return true;
                }
            }
        }
        // When this piece moves up/down
        for (int r = Math.min(preRow, targetRow)+1; r < Math.max(preRow, targetRow); r++) {
            for(Piece piece : GamePanel.simPieces) {
                if (piece.col == targetCol && piece.row == r) {
                    hittingP = piece;
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean pieceIsOnDiagonalLine(int targetCol, int targetRow) {

       if (targetRow < preRow) {
        // Up left
        for (int c = preCol - 1; c > targetCol; c--) {
            int diff = Math.abs(c - preCol);
            for (Piece piece : GamePanel.simPieces) {
                if (piece.col == c && piece.row == preRow - diff) {
                    hittingP = piece;
                    return true;
                }
            }
        }
        // Up right
        for (int c = preCol + 1; c < targetCol; c++) {
            int diff = Math.abs(c - preCol);
            for( Piece piece : GamePanel.simPieces) {
                if (piece.col == c && piece.row == preRow - diff) {
                    hittingP = piece;
                    return true;
                }
            }

        }
       }
       if (targetRow > preRow) {
        // Down left
        for (int c = preCol - 1; c > targetCol; c--) {
            int diff = Math.abs(c - preCol);
            for (Piece piece : GamePanel.simPieces) {
                if (piece.col == c && piece.row == preRow + diff) {
                    hittingP = piece;
                    return true;
                }
            }
        }
        // Down right
        for (int c = preCol + 1; c < targetCol; c++) {
            int diff = Math.abs(c - preCol);
            for( Piece piece : GamePanel.simPieces) {
                if (piece.col == c && piece.row == preRow + diff) {
                    hittingP = piece;
                    return true;
                }
            }

        }
    }
        return false;
    }
    

    public void draw(Graphics2D g2) {

        g2.drawImage(image, x, y, ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE, null);

    }

}
