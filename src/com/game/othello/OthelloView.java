package com.game.othello;

import com.game.othello.Chess.ChessColor;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class OthelloView extends View implements ChessListener{

    private int screenWidth;
    private int screenHeight; 
    private static int squareNum = 8;
    private static int borderWidth = 5;
    private Paint paint = new Paint();
    private ChessBoard board;
    private ChessRule rule;
    private ChessRobotInterface robot;
    private Handler handler;

    /**
     * Constructs a OthelloView based on inflation from XML
     * 
     * @param context
     * @param attrs
     */
    public OthelloView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(Color.rgb(50, 100, 250));
        init();
    }

    public void addRobot() {
        //easy
//      robot = new ChessRobotEasy(ChessColor.WHITE);
      //normal
      robot = new ChessRobotNormal(ChessColor.WHITE);
    }

    private void init() {
        board = new ChessBoard();

        rule = new ChessRule(board, this);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
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
        AlertDialog.Builder builder = new Builder(this.getContext());
        builder.setMessage("Game Over");
        builder.setTitle("Game Over");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                
            }

         });
         builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 // TODO Auto-generated method stub
                 
             }
         });
         builder.create().show();
    }

    @Override
    public void onChessColor(ChessColor current) {
        Message msg = new Message();
        msg.what = 0;
        String color = "";
        if (current == ChessColor.BLACK) {
            color = "black";
        } else {
            color = "white";
        }
        msg.obj = color;
        msg.arg1 = board.getBlackChessCount();
        msg.arg2 = board.getWhiteChessCount();
        handler.sendMessage(msg);
    }

    @Override
    public void onDraw() {
        invalidate();
    }

    public void robotDropChess() {
        if ((robot != null)
                && (robot.getChessColor() == rule.getCurrentColor())) {
            rule.dropChess(robot.getLocation(rule), robot.getChessColor());
        }
    }
}
