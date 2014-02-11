package com.game.othello;

import com.game.othello.Chess.ChessColor;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    private static int borderWidth = 2;
    private Paint paint = new Paint();
    private ChessBoard board;
    private ChessRule rule;
    private ChessRobotInterface robot;
    private Handler handler;

    private Rect rect;

    /**
     * Constructs a OthelloView based on inflation from XML
     * 
     * @param context
     * @param attrs
     */
    public OthelloView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(Color.argb(100, 179, 113, 81));
        init();
    }

    public void addRobot(int level) {
        switch(level) {
        case 1:
            robot = new ChessRobotEasy(ChessColor.WHITE);
            break;
        case 2:
            robot = new ChessRobotNormal(ChessColor.WHITE);
            break;
        case 3:
            robot = new ChessRobotSmart(ChessColor.WHITE);
            break;
        default:
            break;
        }
    }

    public void init() {
        board = new ChessBoard(getContext());

        rule = new ChessRule(board, this);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.board);
        
        canvas.drawBitmap(bmp,null,rect,null);  
        
        paint.setColor(Color.BLACK);
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
        rect = new Rect(x, y, x + squareWidth * 8, y + squareWidth * 8);
    }

    @Override
    public void onGameOver(int white, int black) {
        AlertDialog.Builder builder = new Builder(this.getContext());
        String message = "White: " + String.valueOf(white) + " Black: " + String.valueOf(black);
        builder.setMessage(message);
        builder.setTitle("Game Over");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
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
