package com.game.othello;

public class ChessRobotInterface {
    private Chess.ChessColor chessColor;

    public ChessRobotInterface(Chess.ChessColor chessColor) {
        this.chessColor = chessColor;
    }

    public ChessLocation getLocation(ChessRule rule) {
        return null;
    }

    public Chess.ChessColor getChessColor() {
        return chessColor;
    }

    public void setChessColor(Chess.ChessColor chessColor) {
        this.chessColor = chessColor;
    }

}
