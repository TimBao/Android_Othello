package com.game.othello;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

//@TODO: people vs. machine
//@TODO: code refactoring
//@TODO: UI refactoring
public class DrawView extends View {

    class Score {
        private int blackNum;
        private int whiteNum;

        public Score(int blackNum, int whiteNum) {
            this.blackNum = blackNum;
            this.whiteNum = whiteNum;
        }
    }

    private Paint paint = new Paint();
    private Canvas canvas = new Canvas();

    private int screenWidth;
    private int screenHeight; 
    private int borderWidth = 5;
    private int squareNum = 8;
    private int sideLength;
    private int oneSquareWidth;
    private int length;
    private int x, y;
    private int line2, line3, line4, line5, line6, line7;

    private int chess[][] = new int[squareNum][squareNum];

    public static final int INVALID = 0;
    public static final int WHITE   = 1;
    public static final int BLACK   = 2;
    private int chessColor = BLACK;


    public DrawView(Context context) {
        super(context);

        init();
    }

    private void init() {
        for (int i=0; i<squareNum; ++i) {
            for (int j=0; j<squareNum; ++j) {
                chess[i][j] = INVALID;
            }
        }
        chess[3][3] = WHITE;
        chess[4][4] = WHITE;
        chess[4][3] = BLACK;
        chess[3][4] = BLACK;
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        drawChessBoard();
        drawChess();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        screenWidth  = w;
        screenHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP :
                int xCur = (int)event.getX();
                int yCur = (int)event.getY();

                dropChess(xCur, yCur);
                break;
        }

        return true;
    }

    private void drawChess() {
        for (int i=0; i<squareNum; ++i) {
            for (int j=0; j<squareNum; ++j) {
                if (chess[i][j] == WHITE) {
                    paint.setColor(Color.WHITE);
                    this.canvas.drawCircle(x+oneSquareWidth/2+(i*oneSquareWidth),
                                           y+oneSquareWidth/2+(j*oneSquareWidth),
                                           oneSquareWidth/2-borderWidth,
                                           paint);
                } else if (chess[i][j] == BLACK) {
                    paint.setColor(Color.BLACK);
                    this.canvas.drawCircle(x+oneSquareWidth/2+(i*oneSquareWidth),
                                           y+oneSquareWidth/2+(j*oneSquareWidth),
                                           oneSquareWidth/2-borderWidth,
                                           paint);
                }
            }
        }
    }

    private void drawChessBoard() {
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(borderWidth);

        sideLength = Math.min(screenWidth, screenHeight);
        oneSquareWidth = (sideLength - 2 * borderWidth) / squareNum;
        length = oneSquareWidth * squareNum;
        x = (screenWidth - length) / 2;
        y = (screenHeight - length) / 2;
        line2 = 2 * oneSquareWidth;
        line3 = 3 * oneSquareWidth;
        line4 = 4 * oneSquareWidth;
        line5 = 5 * oneSquareWidth;
        line6 = 6 * oneSquareWidth;
        line7 = 7 * oneSquareWidth;

        // up side
        this.canvas.drawLine(x, y, x+length, y, paint);
        // left side
        this.canvas.drawLine(x, y, x, y+length, paint);
        // down side
        this.canvas.drawLine(x+length, y, x+length, y+length, paint);
        // right side
        this.canvas.drawLine(x, y+length, x+length, y+length, paint);

        //row1
        this.canvas.drawLine(x, y+oneSquareWidth, x+length, y+oneSquareWidth, paint);
        //row2
        this.canvas.drawLine(x, y+line2, x+length, y+line2, paint);
        //row3
        this.canvas.drawLine(x, y+line3, x+length, y+line3, paint);
        //row4
        this.canvas.drawLine(x, y+line4, x+length, y+line4, paint);
        //row5
        this.canvas.drawLine(x, y+line5, x+length, y+line5, paint);
        //row6
        this.canvas.drawLine(x, y+line6, x+length, y+line6, paint);
        //row7
        this.canvas.drawLine(x, y+line7, x+length, y+line7, paint);

        //column1
        this.canvas.drawLine(x+oneSquareWidth, y, x+oneSquareWidth, y+length, paint);
        //column2
        this.canvas.drawLine(x+line2, y, x+line2, y+length, paint);
        //column3
        this.canvas.drawLine(x+line3, y, x+line3, y+length, paint);
        //column4
        this.canvas.drawLine(x+line4, y, x+line4, y+length, paint);
        //column5
        this.canvas.drawLine(x+line5, y, x+line5, y+length, paint);
        //column6
        this.canvas.drawLine(x+line6, y, x+line6, y+length, paint);
        //column7
        this.canvas.drawLine(x+line7, y, x+line7, y+length, paint);
    }

    private Score getScore() {
        Score score = new Score(0, 0);
        for (int i=0; i<squareNum; ++i) {
            for (int j=0; j<squareNum; ++j) {
                if (chess[i][j] == WHITE) {
                    score.whiteNum++;
                }
                if (chess[i][j] == BLACK) {
                    score.blackNum++;
                }
            }
        }
        return score;
    }

    private boolean decide(ChessLocation location) {
        //@TODO: 如果不能反转棋子，则不能落子，这时要换对方落子，如果双方都不能落子，则结束
        return true;
    }

    private void dropChess(int xCur, int yCur) {
        if ((xCur > x && xCur < x+length) && (yCur > y && yCur < y+length)) {
            ChessLocation location = convertPointToChessLocation(xCur, yCur);
            if (location != null) {
//                if (chess[location.x][location.y] == INVALID) {
//                    chess[location.x][location.y] = getChessColor();
//                    if (decide(location)) {
//                        reverseChess(location);
//                        invalidate();
//                    }
//
//                }
                if (isGameOver()) {
                    Score score = getScore();
                    String message = "Black :" + String.valueOf(score.blackNum) + " White : " + String.valueOf(score.whiteNum);
                    Builder builder = new AlertDialog.Builder(this.getContext()).setTitle("Game Over")
                    .setMessage(message)
                    .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            init();
                        }
                        
                    }).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                        
                    });
                    builder.create().show();
                    return;
                }
            }
        }
    } 

    private int getChessColor() {
        if (chessColor == WHITE) {
            chessColor = BLACK;
            return WHITE;
        } else if (chessColor == BLACK) {
            chessColor = WHITE;
            return BLACK;
        }
        return INVALID;
    }

    private int reverseColor(ChessLocation location) {
//        if (chess[location.x][location.y] == WHITE) {
//            return WHITE;
//        } else if (chess[location.x][location.y] == BLACK) {
//            return BLACK;
//        }
        return INVALID;
    }

    private void reverseChess(ChessLocation curLocation) {
        reverseRow(curLocation);
        reverseColumn(curLocation);
        reverseDiagonal(curLocation);
    }

    private void reverseRightRow(ChessLocation curLocation) {
//        if ((chess[curLocation.x + 1][curLocation.y] != chess[curLocation.x][curLocation.y])) {
//            for (int i = curLocation.x + 2; i < squareNum; ++i) {
//                if (chess[i][curLocation.y] == INVALID) {
//                    break;
//                }
//                if (chess[curLocation.x][curLocation.y] == chess[i][curLocation.y]) {
//                    for (int rx = curLocation.x + 1; rx < i; ++rx) {
//                        chess[rx][curLocation.y] = reverseColor(curLocation);
//                    }
//                    return;
//                }
//            }
//        }
    }

    private void reverseLeftRow(ChessLocation curLocation) {
//        if ((chess[curLocation.x - 1][curLocation.y] != chess[curLocation.x][curLocation.y])) {
//            for (int i = curLocation.x - 2; i >= 0; --i) {
//                if (chess[i][curLocation.y] == INVALID) {
//                    break;
//                }
//                if (chess[curLocation.x][curLocation.y] == chess[i][curLocation.y]) {
//                    for (int rx = curLocation.x - 1; rx > i; --rx) {
//                        chess[rx][curLocation.y] = reverseColor(curLocation);
//                    }
//                    return;
//                }
//            }
//        }
    }

    private void reverseRow(ChessLocation curLocation) {

//        if ((curLocation.x + 1 < squareNum) && (chess[curLocation.x + 1][curLocation.y] != INVALID)) {
//            reverseRightRow(curLocation);
//        }
//        if ( (curLocation.x-1 >= 0) && (chess[curLocation.x-1][curLocation.y] != INVALID)) {
//            reverseLeftRow(curLocation);
//        }
    }

    private void reverseDownColumn(ChessLocation curLocation) {
//        if ((chess[curLocation.x][curLocation.y + 1] != chess[curLocation.x][curLocation.y])) {
//            for (int i = curLocation.y + 2; i < squareNum; ++i) {
//                if (chess[curLocation.x][i] == INVALID) {
//                    break;
//                }
//                if (chess[curLocation.x][curLocation.y] == chess[curLocation.x][i]) {
//                    for (int ry = curLocation.y + 1; ry < i; ++ry) {
//                        chess[curLocation.x][ry] = reverseColor(curLocation);
//                    }
//                    return;
//                }
//            }
//        }
    }

    private void reverseUpColumn(ChessLocation curLocation) {
//        if ((chess[curLocation.x][curLocation.y - 1] != chess[curLocation.x][curLocation.y])) {
//            for (int i = curLocation.y - 2; i >= 0; --i) {
//                if (chess[curLocation.x][i] == INVALID) {
//                    break;
//                }
//                if (chess[curLocation.x][curLocation.y] == chess[curLocation.x][i]) {
//                    for (int ry = curLocation.y - 1; ry > i; --ry) {
//                        chess[curLocation.x][ry] = reverseColor(curLocation);
//                    }
//                    return;
//                }
//            }
//        }
    }

    private void reverseColumn(ChessLocation curLocation) {
//        if ((curLocation.y + 1 < squareNum) && (chess[curLocation.x][curLocation.y+1] != INVALID)) {
//            reverseDownColumn(curLocation);
//        }
//        if ((curLocation.y-1 >= 0) && (chess[curLocation.x][curLocation.y-1] != INVALID)) {
//            reverseUpColumn(curLocation);
//        }
    }

    private void reverseUpBackSlashDiagonal(ChessLocation curLocation) {
//        if ((chess[curLocation.x - 1][curLocation.y - 1] != chess[curLocation.x][curLocation.y])) {
//            for (int i = curLocation.x - 2, j = curLocation.y - 2; i >= 0 && j >=0; --i, --j) {
//                if (chess[i][j] == INVALID) {
//                    break;
//                }
//                if (chess[curLocation.x][curLocation.y] == chess[i][j]) {
//                    for (int rx = curLocation.x - 1, ry = curLocation.y - 1; rx > i && ry > j; --rx, --ry) {
//                        chess[rx][ry] = reverseColor(curLocation);
//                    }
//                    return;
//                }
//            }
//        }
    }

    private void reverseDownBackSlashDiagonal(ChessLocation curLocation) {
//        if ((chess[curLocation.x + 1][curLocation.y + 1] != chess[curLocation.x][curLocation.y])) {
//            for (int i = curLocation.x + 2, j = curLocation.y + 2; i < squareNum && j < squareNum; ++i, ++j) {
//                if (chess[i][i] == INVALID) {
//                    break;
//                }
//                if (chess[curLocation.x][curLocation.y] == chess[i][j]) {
//                    for (int rx = curLocation.x + 1, ry = curLocation.y + 1; rx < i && ry < j; ++rx, ++ry) {
//                        chess[rx][ry] = reverseColor(curLocation);
//                    }
//                    return;
//                }
//            }
//        }
    }

    private void reverseBackSlashDiagonal(ChessLocation curLocation) {
//        if ((curLocation.x + 1 < squareNum) && (curLocation.y + 1 < squareNum) && (chess[curLocation.x + 1][curLocation.y + 1] != INVALID)) {
//            reverseDownBackSlashDiagonal(curLocation);
//        }
//        if ((curLocation.x-1 >= 0) && (curLocation.y-1 >= 0) && (chess[curLocation.x - 1][curLocation.y - 1] != INVALID)) {
//            reverseUpBackSlashDiagonal(curLocation);
//        }
    }

    private void reverseDownSlashesDiagonal(ChessLocation curLocation) {
//        if ((chess[curLocation.x - 1][curLocation.y + 1] != chess[curLocation.x][curLocation.y])) {
//            for (int i = curLocation.x - 2, j = curLocation.y + 2; i >= 0 && j < squareNum; --i, ++j) {
//                if (chess[i][j] == INVALID) {
//                    break;
//                }
//                if (chess[curLocation.x][curLocation.y] == chess[i][j]) {
//                    for (int rx = curLocation.x - 1, ry = curLocation.y + 1; rx > i && ry < j; --rx, ++ry) {
//                        chess[rx][ry] = reverseColor(curLocation);
//                    }
//                    return;
//                }
//            }
//        }
    }

    private void reverseUpSlashesDiagonal(ChessLocation curLocation) {
//        if ((chess[curLocation.x + 1][curLocation.y - 1] != chess[curLocation.x][curLocation.y])) {
//            for (int i = curLocation.x + 2, j = curLocation.y - 2; i < squareNum && j >= 0; ++i, --j) {
//                if (chess[i][j] == INVALID) {
//                    break;
//                }
//                if (chess[curLocation.x][curLocation.y] == chess[i][j]) {
//                    for (int rx = curLocation.x + 1, ry = curLocation.y - 1; rx < i && ry > j; ++rx, --ry) {
//                        chess[rx][ry] = reverseColor(curLocation);
//                    }
//                    return;
//                }
//            }
//        }
    }

    private void reverseSlashesDiagonal(ChessLocation curLocation) {
//        if ((curLocation.x + 1 < squareNum) && (curLocation.y - 1 >= 0) && (chess[curLocation.x + 1][curLocation.y - 1] != INVALID)) {
//            reverseUpSlashesDiagonal(curLocation);
//        }
//        if ((curLocation.x - 1 >= 0) && (curLocation.y + 1 < squareNum) && (chess[curLocation.x - 1][curLocation.y + 1] != INVALID)) {
//            reverseDownSlashesDiagonal(curLocation);
//        }
    }

    private void reverseDiagonal(ChessLocation curLocation) {
        //backslash  '\'
        reverseBackSlashDiagonal(curLocation);
        //slashes    '/'
        reverseSlashesDiagonal(curLocation);
    }

    private ChessLocation convertPointToChessLocation(int xCur, int yCur) {
        int locationX = (xCur - x) / oneSquareWidth + (((xCur - x) % oneSquareWidth) > 0 ? 1 : 0);
        int locationY = (yCur - y) / oneSquareWidth + (((yCur - y) % oneSquareWidth) > 0 ? 1 : 0);
        ChessLocation location = new ChessLocation(locationX-1, locationY-1);
        return location;
    }

    private boolean isGameOver() {
        for (int i=0; i<squareNum; ++i) {
            for (int j=0; j<squareNum; ++j) {
                if (chess[i][j] == INVALID) {
                    return false;
                }
            }
        }
        //@TODO if white = 0 or black = 0; other > 2 --> game over
        return true;
    }
}
