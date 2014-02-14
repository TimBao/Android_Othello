/**
 * Copyright (c) 2014, WanXiang Bao
 * All rights reserved.
 * Summary : 
 * Author : WanXiang Bao
 */
package com.game.othello;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import com.game.othello.Chess.ChessColor;

/*
 * The normal level get the location which can reverse max of chesses.
 */
public class ChessRobotNormal extends ChessRobotInterface {

    public ChessRobotNormal(ChessColor chessColor) {
        super(chessColor);
    }

    @Override
    public ChessLocation getLocation(ChessRule rule) {
        Map<ChessLocation, Integer> map = new HashMap<ChessLocation, Integer>();

        ChessBoard board = rule.getBoard();
        for (Chess chess : board.getChesses()) {
            if ((chess.getColor() == ChessColor.INVALID)
                    && rule.canChessDrop(chess, getChessColor())) {
                map.put(chess.getLocation(), rule.getCanReverseLocation().size());
            }
        }
        int max = 0;
        ChessLocation location = null;
        for (Entry<ChessLocation, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                location = entry.getKey();
                max = entry.getValue();
            }
        }
        if (location != null) {
            return location;
        }
        return null;
    }
}
