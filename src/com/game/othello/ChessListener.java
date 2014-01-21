package com.game.othello;

public interface ChessListener {

    public void onGameOver(int white, int black);
    public void onChessColor(Chess.ChessColor current);
    public void onDraw();
    public void onNextDrop(Chess.ChessColor next);
}
