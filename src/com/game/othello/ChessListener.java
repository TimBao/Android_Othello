/**
 * Copyright (c) 2014, WanXiang Bao
 * All rights reserved.
 * Summary : 
 * Author : WanXiang Bao
 */
package com.game.othello;

public interface ChessListener {

    public void onGameOver(int white, int black);
    public void onChessDropped(Chess.ChessColor current);
    public void onDraw();
}
