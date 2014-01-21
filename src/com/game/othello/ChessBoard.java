package com.game.othello;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;

public class ChessBoard implements DrawInterface {

    private int x;  //top left location of the board
    private int y;  //top left location of the board
    private int side; //side length of the board
    private int squareWidth;
    private int rows;
    private int columns;
    private Vector<Chess> chesses = new Vector();
    private int chessRaduis;

    public ChessBoard() {
        this.x               = 0;
        this.y               = 0;
        this.side            = 0;
        this.squareWidth     = 0;
        this.rows            = 0;
        this.columns         = 0;
        this.chessRaduis     = 0;
    }

    public ChessBoard(int x, int y, int side, int rows, int columns, int squareWidth) {
        this.x               = x;
        this.y               = y;
        this.side            = side;
        this.squareWidth     = squareWidth;
        this.rows            = rows;
        this.columns         = columns;
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
            if (chess.getColor() == Chess.ChessColor.INVALID) {
                ++count;
            }
        }
        return count;
    }

    public int getWhiteChessCount() {
        int count = 0;
        for (Chess chess : chesses) {
            if (chess.getColor() == Chess.ChessColor.WHITE) {
                ++count;
            }
        }
        return count;
    }

    public int getBlackChessCount() {
        int count = 0;
        for (Chess chess : chesses) {
            if (chess.getColor() == Chess.ChessColor.BLACK) {
                ++count;
            }
        }
        return count;
    }
}
