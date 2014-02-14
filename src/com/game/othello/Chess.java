/**
 * Copyright (c) 2014, WanXiang Bao
 * All rights reserved.
 * Summary : 
 * Author : WanXiang Bao
 */
package com.game.othello;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Chess implements DrawInterface {

    public enum ChessColor {
        INVALID,
        WHITE,
        BLACK,
        HINT
    }

    private Context context;
    private ChessColor color = ChessColor.BLACK;
    private ChessBoard board;
    private ChessLocation location;
    private int radius;
    private int value;

    public Chess(Context context, ChessBoard board, ChessLocation location, ChessColor color, int radius) {

        this.board      = board;
        this.location   = location;
        this.color      = color;
        this.radius     = radius;
        this.value      = 0;
        this.context    = context;
    }

    public Chess(Context context, ChessBoard board, ChessLocation location, ChessColor color, int radius, int value) {

        this.board      = board;
        this.location   = location;
        this.color      = color;
        this.radius     = radius;
        this.value      = value;
        this.context    = context;
    }

    public Chess copy() {
        Chess newChess = new Chess(this.context, this.board, this.location, this.color, this.radius, this.value);
        return newChess;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {

        int left = board.getX() + location.getX() * board.getSquareWidth();
        int top = board.getY() + location.getY() * board.getSquareWidth();
        int right = left + board.getSquareWidth();
        int bottom = top + board.getSquareWidth();

        if (color == ChessColor.INVALID) {
            return;
        } else if (color == ChessColor.WHITE) {
            Rect rect = new Rect(left, top, right, bottom);
            canvas.drawBitmap(BitmapUtil.getChessBitmap(context, R.drawable.white_chess), null, rect, null);

        } else if (color == ChessColor.BLACK) {
            Rect rect = new Rect(left, top, right, bottom);
            canvas.drawBitmap(BitmapUtil.getChessBitmap(context, R.drawable.black_chess), null, rect, null);
        } else if (color == ChessColor.HINT) {
            paint.setColor(Color.GREEN);
            canvas.drawCircle(board.getX() + location.getX() * board.getSquareWidth() + board.getSquareWidth() / 2,
                              board.getY() + location.getY() * board.getSquareWidth() + board.getSquareWidth() / 2,
                              radius / 2,
                              paint);
        }
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
