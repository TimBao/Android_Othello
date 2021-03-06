/**
 * Copyright (c) 2014, WanXiang Bao
 * All rights reserved.
 * Summary : 
 * Author : WanXiang Bao
 */
package com.game.othello;

import com.game.othello.Chess.ChessColor;

/*
 * The easy level only drop chess on the first could drop place.
 */
public class ChessRobotEasy extends ChessRobotInterface {

    public ChessRobotEasy(ChessColor chessColor) {
        super(chessColor);
    }

    @Override
    public ChessLocation getLocation(ChessRule rule) {
        ChessBoard board = rule.getBoard();
        for (Chess chess : board.getChesses()) {
            if (chess.getColor() == ChessColor.INVALID
                    && rule.canChessDrop(chess, getChessColor())) {
                return chess.getLocation();
            }
        }
        return null;
    }

}
