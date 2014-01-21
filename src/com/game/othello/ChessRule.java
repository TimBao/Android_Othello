package com.game.othello;

import com.game.othello.Chess.ChessColor;

public class ChessRule {
    private ChessBoard board;
    private ChessListener listener;
    private Chess.ChessColor currentColor = Chess.ChessColor.BLACK;

    public ChessRule(ChessBoard board, ChessListener listener) {
        this.board = board;
        this.listener = listener;
        
    }

    public void setDefaultBoard() {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                ChessLocation location = new ChessLocation(i, j);
                ChessColor color = ChessColor.INVALID;
                if ((i == 3 && j == 3) || (i == 4 && j == 4)) {
                    color = ChessColor.WHITE;
                }
                if ((i == 3 && j == 4) || (i == 4 && j == 3)) {
                    color = ChessColor.BLACK;
                }
                board.addChess(new Chess(board, location, color, board.getChessRaduis()));
            }
        }
    }

    public void dropChess(int pX, int pY) {
        if ((pX > board.getX() && pX < board.getX() + board.getSide()) 
                && (pY > board.getY() && pY < board.getY() + board.getSide())) {
            ChessLocation location = convertXYToChessLocation(pX, pY);
            if (location != null && !isLocationUsed(location)) {
                ChessColor color = getChessColor();
                Chess chess = getChess(location);
                if (canChessDrop(chess)) {
                    chess.setColor(color);
                    //reverseChess(chess);
                    if(isGameOver()) {
                        listener.onGameOver(board.getWhiteChessCount(), board.getBlackChessCount());
                    }
                    if (isTwiceDrop()) {
                        currentColor = color;
                        listener.onChessColor(currentColor);
                    }

                    listener.onDraw(); 
                }
            }
        }
    }

    private Chess getChess(ChessLocation location) {
        for (Chess chess : board.getChesses()) {
            if (chess.getLocation().getX() == location.getX()
                    && chess.getLocation().getY() == location.getY()) {
                return chess;
            }
        }
        return null;
    }

    private boolean isTwiceDrop() {
        //find chess in chesses color == currentColor() can revert chess?
        for (Chess chess : board.getChesses()) {
            if (chess.getColor() != currentColor) {
              //@TODO
            }
        }
        return false;
    }
    private boolean isGameOver() {

        if (board.getInvalidChessCount() == 0) {
            return true;
        }
        if (board.getBlackChessCount() == 0 || board.getWhiteChessCount() == 0) {
            return true;
        }

        return false;
    }

    private ChessColor getChessColor() {
        ChessColor color = currentColor;
        if (color == ChessColor.BLACK) {
            currentColor = ChessColor.WHITE;
        } else {
            currentColor = ChessColor.BLACK;
        }
        return color;
    }

    private ChessLocation convertXYToChessLocation(int pX, int pY) {
        int locationX = (pX - board.getX()) / board.getSquareWidth();
        int locationY = (pY - board.getY()) / board.getSquareWidth();

        return new ChessLocation(locationX, locationY);
    }

    public boolean isLocationUsed(ChessLocation location) {
        for (Chess chess : board.getChesses()) {
            if (chess.getLocation().getX() == location.getX()
                    && chess.getLocation().getY() == location.getY() 
                        && chess.getColor() == Chess.ChessColor.INVALID) {
                return true;
            }
        }
        return false;
    }

    public boolean canChessDrop(Chess dropChess) {
        for (Chess chess : board.getChesses()) {
            //row
            if (chess.getLocation().getY() == dropChess.getLocation().getY()) {
                //@TODO
            }
        }

        return true;
    }
}