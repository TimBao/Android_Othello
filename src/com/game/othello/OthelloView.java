package com.game.othello;

import com.game.othello.Chess.ChessColor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class OthelloView extends View implements ChessListener{

    private int screenWidth;
    private int screenHeight; 
    private static int squareNum = 8;
    private static int borderWidth = 5;
    private Paint paint = new Paint();
    private ChessBoard board;
    private ChessRule rule;
    private ChessRobot robot;

    public OthelloView(Context context) {
        super(context);
        init();
    }

    private void init() {
        board = new ChessBoard();

        rule = new ChessRule(board, this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(borderWidth);
        super.onDraw(canvas);
        board.onDraw(canvas, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_UP :
            int x = (int)event.getX();
            int y = (int)event.getY();
            rule.dropChess(x, y);
            break;
    }

    return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth  = w;
        screenHeight = h;
        int sideLength = Math.min(screenWidth, screenHeight);
        int squareWidth = (sideLength - 2 * borderWidth) / squareNum;
        int side = squareWidth * squareNum;
        int x = (screenWidth - side) / 2;
        int y = (screenHeight - side) / 2;
        board.setAtrr(x, y, side, 8, 8, squareWidth, squareWidth/2-borderWidth);
        rule.setDefaultBoard();
    }

    @Override
    public void onGameOver(int white, int black) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onChessColor(ChessColor current) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onDraw() {
        invalidate();
    }

    @Override
    public void onNextDrop(ChessColor next) {
        // TODO Auto-generated method stub
        //if (robot != null && robot.getColor() == next) {
            //robot drop next chess
            //isGameOver()
            //isTwiceDrop
        //}
    }
}
