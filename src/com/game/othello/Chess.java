package com.game.othello;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Chess implements DrawInterface {

    public enum ChessColor {
        INVALID,
        WHITE,
        BLACK
    }

    private ChessColor color = ChessColor.BLACK;
    private ChessBoard board;
    private ChessLocation location;
    private int radius;

    public Chess(ChessBoard board, ChessLocation location, ChessColor color, int radius) {

        this.board      = board;
        this.location   = location;
        this.color      = color;
        this.radius     = radius;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        if (color == ChessColor.INVALID) {
            return;
        } else if (color == ChessColor.WHITE) {
            paint.setColor(Color.WHITE);

        } else if (color == ChessColor.BLACK) {
            paint.setColor(Color.BLACK);
        }
        canvas.drawCircle(board.getX() + location.getX() * board.getSquareWidth() + board.getSquareWidth() / 2,
                          board.getY() + location.getY() * board.getSquareWidth() + board.getSquareWidth() / 2,
                          radius,
                          paint);
    }

    public ChessColor getColor() {
        return color;
    }

    public void setColor(ChessColor color) {
        this.color = color;
    }

    public ChessLocation getLocation() {
        return location;
    }

    public void setLocation(ChessLocation location) {
        this.location = location;
    }

}
