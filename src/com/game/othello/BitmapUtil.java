/**
 * Copyright (c) 2014, WanXiang Bao
 * All rights reserved.
 * Summary : 
 * Author : WanXiang Bao
 */
package com.game.othello;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {
    private static Bitmap bBmp = null;
    private static Bitmap wBmp = null;
    private static Bitmap boardBmp = null;

    public static Bitmap getChessBitmap(Context context, int id) {

        if (id == R.drawable.black_chess) {
            if (bBmp == null) {
                bBmp = BitmapFactory.decodeResource(context.getResources(), id);
            }
            return bBmp;
        }
        if (wBmp == null) {
            wBmp = BitmapFactory.decodeResource(context.getResources(), id);
        }
        return wBmp;
    }

    public static Bitmap getBoardBitmap(Context context, int id) {
        if (boardBmp == null) {
            boardBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.board);
        }
        return boardBmp;
    }
}
