package com.game.othello;

import java.util.Vector;

import com.game.othello.Chess.ChessColor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class ChessBoard implements DrawInterface {

    private Context context;
    private int x;  //top left location of the board
    private int y;  //top left location of the board
    private int side; //side length of the board
    private int squareWidth;
    private int rows;
    private int columns;
    private Vector<Chess> chesses = new Vector();
    private int chessRaduis;

    public ChessBoard(Context context) {
        this.x               = 0;
        this.y               = 0;
        this.side            = 0;
        this.squareWidth     = 0;
        this.rows            = 0;
        this.columns         = 0;
        this.chessRaduis     = 0;
        this.context         = context;
    }

    public ChessBoard(Context context, int x, int y, int side, int rows, int columns, int squareWidth) {
        this.x               = x;
        this.y               = y;
        this.side            = side;
        this.squareWidth     = squareWidth;
        this.rows            = rows;
        this.columns         = columns;
        this.context         = context;
    }

    public void setAtrr(int x, int y, int side, int rows, int columns, int squareWidth, int radius) {
        this.x               = x;
        this.y               = y;
        this.side            = side;
        this.squareWidth     = squareWidth;
        this.rows            = rows;
        this.columns         = columns;
        this.chessRaduis     = radius;
    }

    public ChessBoard copy() {
        ChessBoard newBoard = new ChessBoard(this.context, this.x, this.y, this.side, this.rows, this.columns, this.squareWidth);
        newBoard.chessRaduis = this.chessRaduis;
        
        Vector<Chess> newChesses = new Vector();
        int chessSize = chesses.size();
        for (int i = 0; i < chessSize; ++i) {
            newChesses.add(this.chesses.elementAt(i).copy());
        }
        newBoard.chesses = newChesses;

        return newBoard;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        drawBoard(canvas, paint);
        drawChesses(canvas, paint);
    }

    private void drawBoard(Canvas canvas, Paint paint) {
        // up side
        canvas.drawLine(x, y, x+side, y, paint);
        // left side
        canvas.drawLine(x, y, x, y+side, paint);
        // down side
        canvas.drawLine(x+side, y, x+side, y+side, paint);
        // right side
        canvas.drawLine(x, y+side, x+side, y+side, paint);

        for (int i = 1; i < rows; ++i) {
            canvas.drawLine(x, y+squareWidth*i, x+side, y+squareWidth*i   , paint);
        }

        for (int j = 1; j< columns; ++j) {
            canvas.drawLine(x+squareWidth*j, y, x+squareWidth*j, y+side, paint);
        }
    }

    private void drawChesses(Canvas canvas, Paint paint) {
        for (Chess chess : chesses) {
            chess.onDraw(canvas, paint);
        }
    }

    public boolean addChess(Chess chess) {
        return chesses.add(chess);
    }

    public Vector<Chess> getChesses() {
        return chesses;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getSquareWidth() {
        return squareWidth;
    }

    public int getChessRaduis() {
        return chessRaduis;
    }

    public int getSide() {
        return side;
    }
    public int getInvalidChessCount() {
        int count = 0;
        for (Chess chess : chesses) {
            if (chess.getColor() != ChessColor.INVALID 
                    && chess.getColor() != ChessColor.HINT) {
                ++count;
            }
        }
        return count;
    }

    public int getWhiteChessCount() {
        int count = 0;
        for (Chess chess : chesses) {
            if (chess.getColor() == ChessColor.WHITE) {
                ++count;
            }
        }
        return count;
    }

    public int getBlackChessCount() {
        int count = 0;
        for (Chess chess : chesses) {
            if (chess.getColor() == ChessColor.BLACK) {
                ++count;
            }
        }
        return count;
    }

    public Chess getChessByLocation(ChessLocation location) {
        for (Chess chess : chesses) {
            if (chess.getLocation().getX() == location.getX()
                    && chess.getLocation().getY() == location.getY()) {
                return chess;
            }
        }
        return null;
    }

    public Context getContext() {
        return context;
    }
}
