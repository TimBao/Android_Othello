package com.game.othello;

public interface ChessListener {

    public void onGameOver(int white, int black);
    public void onChessDropped(Chess.ChessColor current);
    public void onDraw();
}
