package com.game.othello;

import java.util.Vector;

import com.game.othello.Chess.ChessColor;

/*
 * The smart robot use min-max search to find the best location.
 */
public class ChessRobotSmart extends ChessRobotInterface {

    private static int maxDepth = 7;
    private static int maxBoardValue = Integer.MAX_VALUE;
    private static int minBoardValue = -maxBoardValue;

    public ChessRobotSmart(ChessColor chessColor) {
        super(chessColor);
    }

    private ChessColor getOppositeColor(ChessColor currentColor) {
        ChessColor color = ChessColor.INVALID;
        if (currentColor == ChessColor.BLACK) {
            color = ChessColor.WHITE;
        } else {
            color = ChessColor.BLACK;
        }
        return color;
    }

    private int evaluate(ChessRule rule) {
        ChessColor color = this.getChessColor();
        ChessColor oppositeColor = getOppositeColor(color);
        Vector<ChessLocation> oppositePlayerPossibleMoves = rule.getPossibleLocation(oppositeColor);
        Vector<ChessLocation> possibleMoves = rule.getPossibleLocation(color);
        if ((possibleMoves.size() == 0) && (oppositePlayerPossibleMoves.size() == 0)) {
            int result = rule.getChessCount(color) - rule.getChessCount(oppositeColor);
            int addend = (int)Math.pow(8, 4) + (int)Math.pow(8, 3);
            if (result < 0) {
                addend = -addend;
            }
            return result + addend;
        }
        else {
            int mobility = GetPossibleConvertions(rule, color, possibleMoves) - GetPossibleConvertions(rule, oppositeColor, oppositePlayerPossibleMoves);
            int stablility = (GetStableDiscsCount(rule, color) - GetStableDiscsCount(rule, oppositeColor)) * 8 * 2 / 3;
            return mobility + stablility;
        }
    }

    private int GetStableDiscsCount(ChessRule rule, ChessColor color) {
        return this.GetStableDiscsFromCorner(rule, color)
                + this.GetStableDiscsFromEdge(rule, color);
    }

    private int GetStableDiscsFromEdge(ChessRule rule, ChessColor color) {
        int result = 0;
        
        if (isEdgeFull(rule, 0, true)) {
            boolean oppositeColorDiscsPassed = false;
            for (int i = 0; i < rule.getBoard().getRows(); ++i) {
                ChessColor chessColor = rule.getBoard().getChessByLocation(new ChessLocation(i, 0)).getColor();
                if (chessColor != color) {
                    oppositeColorDiscsPassed = true;
                }
                else if (oppositeColorDiscsPassed) {
                    int consecutiveDicsCount = 0;
                    while((i < rule.getBoard().getRows()) && (chessColor == color)) {
                        consecutiveDicsCount++;
                        i++;
                        if(i < rule.getBoard().getRows()) {
                            chessColor = rule.getBoard().getChessByLocation(new ChessLocation(i, 0)).getColor();
                        }
                    }
                    if (i != rule.getBoard().getRows()) {
                        result += consecutiveDicsCount;
                        oppositeColorDiscsPassed = true;
                    }
                }
            }
        }
        if (isEdgeFull(rule, 7, true)) {
            boolean oppositeColorDiscsPassed = false;
            for (int i = 0; i < rule.getBoard().getRows(); ++i) {
                ChessColor chessColor = rule.getBoard().getChessByLocation(new ChessLocation(i, 7)).getColor();
                if (chessColor != color) {
                    oppositeColorDiscsPassed = true;
                }
                else if (oppositeColorDiscsPassed) {
                    int consecutiveDicsCount = 0;
                    while((i < rule.getBoard().getRows()) && (chessColor == color)) {
                        consecutiveDicsCount++;
                        i++;
                        if(i < rule.getBoard().getRows()) {
                            chessColor = rule.getBoard().getChessByLocation(new ChessLocation(i, 7)).getColor();
                        }
                    }
                    if (i != rule.getBoard().getRows()) {
                        result += consecutiveDicsCount;
                        oppositeColorDiscsPassed = true;
                    }
                }
            }
        }
        if (isEdgeFull(rule, 0, false)) {
            boolean oppositeColorDiscsPassed = false;
            for (int i = 0; i < rule.getBoard().getRows(); ++i) {
                ChessColor chessColor = rule.getBoard().getChessByLocation(new ChessLocation(0, i)).getColor();
                if (chessColor != color) {
                    oppositeColorDiscsPassed = true;
                }
                else if (oppositeColorDiscsPassed) {
                    int consecutiveDicsCount = 0;
                    while((i < rule.getBoard().getRows()) && (chessColor == color)) {
                        consecutiveDicsCount++;
                        i++;
                        if(i < rule.getBoard().getRows()) {
                            chessColor = rule.getBoard().getChessByLocation(new ChessLocation(0, i)).getColor();
                        }
                    }
                    if (i != rule.getBoard().getRows()) {
                        result += consecutiveDicsCount;
                        oppositeColorDiscsPassed = true;
                    }
                }
            }
        }
        if (isEdgeFull(rule, 7, false)) {
            boolean oppositeColorDiscsPassed = false;
            for (int i = 0; i < rule.getBoard().getRows(); ++i) {
                ChessColor chessColor = rule.getBoard().getChessByLocation(new ChessLocation(7, i)).getColor();
                if (chessColor != color) {
                    oppositeColorDiscsPassed = true;
                }
                else if (oppositeColorDiscsPassed) {
                    int consecutiveDicsCount = 0;
                    while((i < rule.getBoard().getRows()) && (chessColor == color)) {
                        consecutiveDicsCount++;
                        i++;
                        if(i < rule.getBoard().getRows()) {
                            chessColor = rule.getBoard().getChessByLocation(new ChessLocation(7, i)).getColor();
                        }
                    }
                    if (i != rule.getBoard().getRows()) {
                        result += consecutiveDicsCount;
                        oppositeColorDiscsPassed = true;
                    }
                }
            }
        }
        return result;
    }

    private boolean isEdgeFull(ChessRule rule, int edge, boolean isHorizontal) {
        for (int i = 0; i < rule.getBoard().getRows(); ++i) {
            if ((isHorizontal && (rule.getBoard().getChessByLocation(new ChessLocation(i, edge)).getColor() == ChessColor.INVALID))
                || (!isHorizontal && (rule.getBoard().getChessByLocation(new ChessLocation(edge, i)).getColor() == ChessColor.INVALID))) {
                return false;
            }
        }
        return true;
    }

    private int GetStableDiscsFromCorner(ChessRule rule, ChessColor color) {
        int result = 0;
        ChessBoard board = rule.getBoard();
        for (Chess chess : board.getChesses()) {
            if ((chess.getColor() == color)
                    && ((chess.getLocation().getX() == 0 && chess.getLocation().getY() == 0)
                            || (chess.getLocation().getX() == 0 && chess.getLocation().getY() == 7)
                            || (chess.getLocation().getX() == 7 && chess.getLocation().getY() == 0)
                            || (chess.getLocation().getX() == 7 && chess.getLocation().getY() == 7))) {
                result ++;
            }
        }
        return result;
    }

    private int GetPossibleConvertions(ChessRule rule, ChessColor color,
            Vector<ChessLocation> possibleMoves) {
        int result = 0;
        ChessBoard board = rule.getBoard();
        for (Chess chess : board.getChesses()) {
            if ((chess.getColor() == ChessColor.INVALID)
                    && rule.canChessDrop(chess, getChessColor())) {
                result += rule.getCanReverseLocation().size();
            }
        }
        return result;
    }

    private int findBestLocation(ChessRule rule, ChessColor currentColor, boolean isMaximizing, int currentDepth, int alpha, int beta, ChessLocation location) {

        boolean playerSkipsMove = false;
        Vector<ChessLocation> possibleMoves =  new Vector();
        boolean isFinalMove = currentDepth >= maxDepth;
        if (!isFinalMove)
        {
             possibleMoves = rule.getPossibleLocation(currentColor);
            if (possibleMoves.size() == 0) {
                playerSkipsMove = true;
                possibleMoves = rule.getPossibleLocation(getOppositeColor(currentColor));
            }
            isFinalMove = (possibleMoves.size() == 0);
        }

        if (isFinalMove) {
            location.setX(-1);
            location.setY(-1);
            return evaluate(rule);
        } else {
            int bestBoardValue = isMaximizing ? minBoardValue : maxBoardValue;
            ChessLocation bestMove = new ChessLocation(-1, -1);
            for (ChessLocation l : possibleMoves) {
                ChessRule nextRule = rule.copy();
                nextRule.dropChess(l, currentColor);
                boolean nextIsMaximizing = playerSkipsMove ? isMaximizing : !isMaximizing;
                ChessColor nextColor = playerSkipsMove ? currentColor : getOppositeColor(currentColor);
                int currentBoardValue = findBestLocation(nextRule, nextColor, nextIsMaximizing, currentDepth + 1, alpha, beta, location);
                if (isMaximizing) {
                    if (currentBoardValue > bestBoardValue) {
                        bestBoardValue = currentBoardValue;
                        bestMove.setX(l.getX());
                        bestMove.setY(l.getY());
                        if (bestBoardValue > alpha) {
                            alpha = bestBoardValue;
                        }
                        if (bestBoardValue >= beta) {
                            break;
                        }
                    }
                } else {
                    if (currentBoardValue < bestBoardValue)
                    {
                        bestBoardValue = currentBoardValue;
                        bestMove.setX(l.getX());
                        bestMove.setY(l.getY());

                        if (bestBoardValue < beta)
                        {
                            beta = bestBoardValue;
                        }

                        if (bestBoardValue <= alpha)
                        {
                            break;
                        }
                    }
                }
            }
            location.setX(bestMove.getX());
            location.setY(bestMove.getY());
            return bestBoardValue;
        }
    }

    private ChessLocation getNextMoveLocation(ChessRule rule, ChessColor currentColor) {
        ChessLocation location = new ChessLocation(-1, -1);
        findBestLocation(rule, currentColor, true, 1, maxBoardValue, minBoardValue, location);
        return location;
    }

    @Override
    public ChessLocation getLocation(ChessRule rule) {
        return getNextMoveLocation(rule, rule.getCurrentColor());
    }


}
