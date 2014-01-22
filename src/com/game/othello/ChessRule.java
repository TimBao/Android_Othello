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
                if (canChessDrop(chess, color)) {
                    chess.setColor(color);
                    reverseChess(chess);
                    if(isGameOver()) {
                        listener.onGameOver(board.getWhiteChessCount(), board.getBlackChessCount());
                    }

                    if (isTwiceDrop(color)) {
                        currentColor = color;
                    }

                    listener.onDraw(); 
                    listener.onChessColor(currentColor);
                } else {
                    getChessColor();
                }
            }
        }
    }

    private void reverseChess(Chess chess) {
        reverseLeft(chess);
        reverseRight(chess);
        reverseUp(chess);
        reverseDown(chess);
        reverseLeftUp(chess);
        reverseRightDown(chess);
        reverseRightUp(chess);
        reverseLeftDown(chess);
    }

    private void reverseLeftDown(Chess chess) {
        ChessLocation location = new ChessLocation(chess.getLocation().getX() - 1, chess.getLocation().getY() + 1);
        if ((location.getX() >= 0 && location.getY() < board.getColumns()) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != chess.getColor())) {
            for (int i = location.getY() - 1, j = location.getY() + 1; i >= 0 && j < board.getColumns(); --i, ++j) {
                Chess next = board.getChessByLocation(new ChessLocation(i, j));
                if (next == null || next.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (chess.getColor() == next.getColor()) {
                    for (int x = location.getY() - 1, y = location.getY() + 1; x >= i && y < j; --x, ++y) {
                        board.getChessByLocation(new ChessLocation(x, y)).setColor(chess.getColor());
                    }
                    return;
                }
            }
        }
    }

    private void reverseRightUp(Chess chess) {
        ChessLocation location = new ChessLocation(chess.getLocation().getX() + 1, chess.getLocation().getY() - 1);
        if ((location.getX() < board.getRows() && location.getY() >= 0) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != chess.getColor())) {
            for (int i = location.getY() + 1, j = location.getY() - 1; i < board.getRows() && j >= 0; ++i, --j) {
                Chess next = board.getChessByLocation(new ChessLocation(i, j));
                if (next == null || next.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (chess.getColor() == next.getColor()) {
                    for (int x = location.getY() + 1, y = location.getY() - 1; x < i && y >= j; ++x, --y) {
                        board.getChessByLocation(new ChessLocation(x, y)).setColor(chess.getColor());
                    }
                    return;
                }
            }
        }
    }

    private void reverseRightDown(Chess chess) {
        ChessLocation location = new ChessLocation(chess.getLocation().getX() + 1, chess.getLocation().getY() + 1);
        if ((location.getX() < board.getRows() && location.getY() < board.getColumns()) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != chess.getColor())) {
            for (int i = location.getY() + 1, j = location.getY() + 1; i < board.getRows() && j < board.getColumns(); ++i, ++j) {
                Chess next = board.getChessByLocation(new ChessLocation(i, j));
                if (next == null || next.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (chess.getColor() == next.getColor()) {
                    for (int x = location.getY() + 1, y = location.getY() + 1; x < i && y < j; ++x, ++y) {
                        board.getChessByLocation(new ChessLocation(x, y)).setColor(chess.getColor());
                    }
                    return;
                }
            }
        }
    }

    private void reverseLeftUp(Chess chess) {
        ChessLocation location = new ChessLocation(chess.getLocation().getX() - 1, chess.getLocation().getY() - 1);
        if ((location.getX() >= 0 && location.getY() >= 0) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != chess.getColor())) {
            for (int i = location.getY() - 1, j = location.getY() - 1; i >= 0 && j >= 0; --i, --j) {
                Chess next = board.getChessByLocation(new ChessLocation(i, j));
                if (next == null || next.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (chess.getColor() == chess.getColor()) {
                    for (int x = location.getX() - 1, y= location.getY() - 1; x >= i && y >= j; --x, --y) {
                        board.getChessByLocation(new ChessLocation(x, y)).setColor(chess.getColor());
                    }
                    return;
                }
            }
        }
    }

    private void reverseDown(Chess chess) {
        ChessLocation location = new ChessLocation(chess.getLocation().getX(), chess.getLocation().getY() + 1);
        if ((location.getY() < board.getColumns()) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != chess.getColor())) {
            for (int i = location.getY() + 1; i < board.getColumns(); ++i) {
                Chess next = board.getChessByLocation(new ChessLocation(location.getX(), i));
                if (next == null || next.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (chess.getColor() == next.getColor()) {
                    for (int y = location.getY(); y < i; ++y) {
                        board.getChessByLocation(new ChessLocation(chess.getLocation().getX(), y)).setColor(chess.getColor());
                    }
                    return;
                }
            }
        }
    }

    private void reverseUp(Chess chess) {
        ChessLocation location = new ChessLocation(chess.getLocation().getX(), chess.getLocation().getY() - 1);
        if ((location.getY() >= 0) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != chess.getColor())) {
            for (int i = location.getY() - 1; i >= 0; --i) {
                Chess next = board.getChessByLocation(new ChessLocation(location.getX(), i));
                if (next == null || chess.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (chess.getColor() == next.getColor()) {
                    for (int y = location.getY(); y > i; --y) {
                        board.getChessByLocation(new ChessLocation(chess.getLocation().getX(), y)).setColor(chess.getColor());
                    }
                    return;
                }
            }
        }
    }

    private void reverseRight(Chess chess) {
        ChessLocation location = new ChessLocation(chess.getLocation().getX() + 1, chess.getLocation().getY());
        if ((location.getX() < board.getRows()) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != chess.getColor())) {
            for (int i = location.getX() + 1; i < board.getRows(); ++i) {
                Chess next = board.getChessByLocation(new ChessLocation(i, location.getY()));
                if (next == null || next.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (chess.getColor() == next.getColor()) {
                    for (int x = location.getX(); x < i; ++x) {
                        board.getChessByLocation(new ChessLocation(x, chess.getLocation().getY())).setColor(chess.getColor());
                    }
                    return;
                }
            }
        }
    }

    private void reverseLeft(Chess chess) {
        ChessLocation location = new ChessLocation(chess.getLocation().getX() - 1, chess.getLocation().getY());
        if ((location.getX() >= 0) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != chess.getColor())) {
            for (int i = location.getX() - 1; i >= 0; --i) {
                Chess next = board.getChessByLocation(new ChessLocation(i, location.getY()));
                if (next == null || next.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (chess.getColor() == next.getColor()) {
                    for (int x = location.getX(); x > i; --x) {
                        board.getChessByLocation(new ChessLocation(x, chess.getLocation().getY())).setColor(chess.getColor());
                    }
                    return;
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

    private boolean isTwiceDrop(ChessColor prevColor) {
        for (Chess chess : board.getChesses()) {
            if (chess.getColor() != Chess.ChessColor.INVALID
                    && chess.getColor() != prevColor 
                        && canChessDrop(chess, chess.getColor())) {
                return false;
            }
        }
        return true;
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
                        && chess.getColor() != Chess.ChessColor.INVALID) {
                return true;
            }
        }
        return false;
    }

    public boolean canChessDrop(Chess dropChess, ChessColor color) {
        boolean canDrop = false;
        if (canChessDropLeft(dropChess, color) || canChessDropRight(dropChess, color)
                || canChessDropUp(dropChess, color) || canChessDropDown(dropChess, color)
                || canChessDropLeftUp(dropChess, color) || canChessDropRightDown(dropChess, color)
                || canChessDropRightUp(dropChess, color) || canChessDropLeftDown(dropChess, color)) {
            canDrop = true;
        }

        return canDrop;
    }

    private boolean canChessDropLeft(Chess dropChess, ChessColor color) {
        boolean canDrop = false;
        ChessLocation location = new ChessLocation(dropChess.getLocation().getX() - 1, dropChess.getLocation().getY());
        if ((location.getX() >= 0) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != color)) {
            for (int i = location.getX() - 1; i >= 0; --i) {
                Chess chess = board.getChessByLocation(new ChessLocation(i, location.getY()));
                if (chess == null || chess.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (color == chess.getColor()) {
                    canDrop = true;
                    break;
                }
            }
        }
        return canDrop;
    }

    private boolean canChessDropRight(Chess dropChess, ChessColor color) {
        boolean canDrop = false;
        ChessLocation location = new ChessLocation(dropChess.getLocation().getX() + 1, dropChess.getLocation().getY());
        if ((location.getX() < board.getRows()) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != color)) {
            for (int i = location.getX() + 1; i < board.getRows(); ++i) {
                Chess chess = board.getChessByLocation(new ChessLocation(i, location.getY()));
                if (chess == null || chess.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (color == chess.getColor()) {
                    canDrop = true;
                    break;
                }
            }
        }
        return canDrop;
    }

    private boolean canChessDropUp(Chess dropChess, ChessColor color) {
        boolean canDrop = false;
        ChessLocation location = new ChessLocation(dropChess.getLocation().getX(), dropChess.getLocation().getY() - 1);
        if ((location.getY() >= 0) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != color)) {
            for (int i = location.getY() - 1; i >= 0; --i) {
                Chess chess = board.getChessByLocation(new ChessLocation(location.getX(), i));
                if (chess == null || chess.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (color == chess.getColor()) {
                    canDrop = true;
                    break;
                }
            }
        }
        return canDrop;
    }

    private boolean canChessDropDown(Chess dropChess, ChessColor color) {
        boolean canDrop = false;
        ChessLocation location = new ChessLocation(dropChess.getLocation().getX(), dropChess.getLocation().getY() + 1);
        if ((location.getY() < board.getColumns()) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != color)) {
            for (int i = location.getY() + 1; i < board.getColumns(); ++i) {
                Chess chess = board.getChessByLocation(new ChessLocation(location.getX(), i));
                if (chess == null || chess.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (color == chess.getColor()) {
                    canDrop = true;
                    break;
                }
            }
        }
        return canDrop;
    }

    private boolean canChessDropLeftUp(Chess dropChess, ChessColor color) {
        boolean canDrop = false;
        ChessLocation location = new ChessLocation(dropChess.getLocation().getX() - 1, dropChess.getLocation().getY() - 1);
        if ((location.getX() >= 0 && location.getY() >= 0) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != color)) {
            for (int i = location.getX() - 1, j = location.getY() - 1; i >= 0 && j >= 0; --i, --j) {
                Chess chess = board.getChessByLocation(new ChessLocation(i, j));
                if (chess == null || chess.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (color == chess.getColor()) {
                    canDrop = true;
                    break;
                }
            }
        }
        return canDrop;
    }

    private boolean canChessDropRightDown(Chess dropChess, ChessColor color) {
        boolean canDrop = false;
        ChessLocation location = new ChessLocation(dropChess.getLocation().getX() + 1, dropChess.getLocation().getY() + 1);
        if ((location.getX() < board.getRows() && location.getY() < board.getColumns()) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != color)) {
            for (int i = location.getY() + 1, j = location.getY() + 1; i < board.getRows() && j < board.getColumns(); ++i, ++j) {
                Chess chess = board.getChessByLocation(new ChessLocation(i, j));
                if (chess == null || chess.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (color == chess.getColor()) {
                    canDrop = true;
                    break;
                }
            }
        }
        return canDrop;
    }

    private boolean canChessDropRightUp(Chess dropChess, ChessColor color) {
        boolean canDrop = false;
        ChessLocation location = new ChessLocation(dropChess.getLocation().getX() + 1, dropChess.getLocation().getY() - 1);
        if ((location.getX() < board.getRows() && location.getY() >= 0) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != color)) {
            for (int i = location.getY() + 1, j = location.getY() - 1; i < board.getRows() && j >= 0; ++i, --j) {
                Chess chess = board.getChessByLocation(new ChessLocation(i, j));
                if (chess == null || chess.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (color == chess.getColor()) {
                    canDrop = true;
                    break;
                }
            }
        }
        return canDrop;
    }


    private boolean canChessDropLeftDown(Chess dropChess, ChessColor color) {
        boolean canDrop = false;
        ChessLocation location = new ChessLocation(dropChess.getLocation().getX() - 1, dropChess.getLocation().getY() + 1);
        if ((location.getX() >= 0 && location.getY() < board.getColumns()) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != color)) {
            for (int i = location.getY() - 1, j = location.getY() + 1; i >= 0 && j < board.getColumns(); --i, ++j) {
                Chess chess = board.getChessByLocation(new ChessLocation(i, j));
                if (chess == null || chess.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (color == chess.getColor()) {
                    canDrop = true;
                    break;
                }
            }
        }
        return canDrop;
    }
}