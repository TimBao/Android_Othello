package com.game.othello;

import java.util.Vector;

import com.game.othello.Chess.ChessColor;

public class ChessRule {
    private ChessBoard board;
    private ChessRobotInterface robot;
    private ChessListener listener;
    private ChessColor currentColor = ChessColor.BLACK;
    private Vector<ChessLocation> reverseLocation = new Vector();

    public ChessRule(ChessBoard board, ChessListener listener) {
        this.board = board;
        this.listener = listener;
        
    }

    public ChessRule copy() {
        return new ChessRule(this.board.copy(), null);
    }

    public ChessRobotInterface getRobot() {
        return robot;
    }

    public void setRobot(ChessRobotInterface robot) {
        this.robot = robot;
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
                board.addChess(new Chess(board.getContext(), board, location, color, board.getChessRaduis()));
            }
        }
        setChessHintForHumanPlayer();
        if (robot != null && robot.getChessColor() == ChessColor.BLACK) {
            listener.onChessDropped(currentColor);
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
                        if(listener != null)
                            listener.onGameOver(board.getWhiteChessCount(), board.getBlackChessCount());
                    }

                    if (isTwiceDrop(color)) {
                        currentColor = color;
                    }
                    setChessHintForHumanPlayer();
                    if(listener != null) {
                        listener.onDraw(); 
                        listener.onChessDropped(currentColor);
                    }
                } else {
                    getChessColor();
                }
            }
        }
    }

    public void dropChess(ChessLocation location, ChessColor color) {
        Chess chess = getChess(location);
        if (chess != null && canChessDrop(chess, color)) {
            chess.setColor(color);
            reverseChess(chess);
            if(isGameOver()) {
                if(listener != null)
                    listener.onGameOver(board.getWhiteChessCount(), board.getBlackChessCount());
            }

            if (isTwiceDrop(color)) {
                currentColor = color;
            } else {
                getChessColor();
            }
            setChessHintForHumanPlayer();
            if(listener != null) {
                listener.onDraw(); 
                listener.onChessDropped(currentColor);
            }
        }
    }

    private void setChessHintForHumanPlayer() {
        for (Chess chess : board.getChesses()) {
            if (chess.getColor() == ChessColor.HINT) {
                chess.setColor(ChessColor.INVALID);;
            }
        }
        Vector<ChessLocation> possibleLocation = getPossibleLocation(currentColor);
        if (robot == null) {
            for(int i = 0; i < possibleLocation.size(); ++i) {
                Chess possibleChess = getChess(possibleLocation.elementAt(i));
                if (possibleChess != null) {
                    possibleChess.setColor(ChessColor.HINT);
                }
            }
        } else {
            if (currentColor != robot.getChessColor()) {
                for(int i = 0; i < possibleLocation.size(); ++i) {
                    Chess possibleChess = getChess(possibleLocation.elementAt(i));
                    if (possibleChess != null) {
                        possibleChess.setColor(ChessColor.HINT);
                    }
                }
            }
        }
    }

    private void reverseChess(Chess chess) {
        for(int i = 0; i < reverseLocation.size(); ++i) {
            Chess reverseChess = getChess((ChessLocation)reverseLocation.get(i));
            if (reverseChess != null) {
                reverseChess.setColor(chess.getColor());
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
                    && chess.getColor() != prevColor ) {
                for (Chess next : board.getChesses()) {
                    if (next.getColor() == Chess.ChessColor.INVALID
                            && canChessDrop(next, chess.getColor())) {
                        return false;
                    }
                }
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
        int invalidCount = 0;
        for (Chess chess : board.getChesses()) {
            if (chess.getColor() == Chess.ChessColor.INVALID
                    && !canChessDrop(chess, Chess.ChessColor.BLACK) 
                    && !canChessDrop(chess, Chess.ChessColor.WHITE)) {
                ++invalidCount;
            }
        }
        if (invalidCount == board.getInvalidChessCount()) {
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
                        && (chess.getColor() == Chess.ChessColor.BLACK || chess.getColor() == Chess.ChessColor.WHITE)) {
                return true;
            }
        }
        return false;
    }

    public boolean canChessDrop(Chess dropChess, ChessColor color) {
        boolean canDrop = false;
        reverseLocation.clear();
        canChessDropLeft(dropChess, color);
        canChessDropRight(dropChess, color);
        canChessDropUp(dropChess, color);
        canChessDropDown(dropChess, color);
        canChessDropLeftUp(dropChess, color);
        canChessDropRightDown(dropChess, color);
        canChessDropRightUp(dropChess, color);
        canChessDropLeftDown(dropChess, color);
        if (reverseLocation.size() > 0) {
            canDrop = true;
        }

        return canDrop;
    }

    private void canChessDropLeft(Chess dropChess, ChessColor color) {
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
                    for (int x = location.getX(); x > i; --x) {
                        reverseLocation.addElement(new ChessLocation(x, chess.getLocation().getY()));
                    }
                    return;
                }
            }
        }
    }

    private void canChessDropRight(Chess dropChess, ChessColor color) {
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
                    for (int x = location.getX(); x < i; ++x) {
                        reverseLocation.addElement(new ChessLocation(x, chess.getLocation().getY()));
                    }
                    return;
                }
            }
        }
    }

    private void canChessDropUp(Chess dropChess, ChessColor color) {
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
                    for (int y = location.getY(); y > i; --y) {
                        reverseLocation.addElement(new ChessLocation(chess.getLocation().getX(), y));
                    }
                    return;
                }
            }
        }
    }

    private void canChessDropDown(Chess dropChess, ChessColor color) {
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
                    for (int y = location.getY(); y < i; ++y) {
                        reverseLocation.addElement(new ChessLocation(chess.getLocation().getX(), y));
                    }
                    return;
                }
            }
        }
    }

    private void canChessDropLeftUp(Chess dropChess, ChessColor color) {
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
                    for (int x = location.getX(), y= location.getY(); x > i && y > j; --x, --y) {
                        reverseLocation.addElement(new ChessLocation(x, y));
                    }
                    return;
                }
            }
        }
    }

    private void canChessDropRightDown(Chess dropChess, ChessColor color) {
        ChessLocation location = new ChessLocation(dropChess.getLocation().getX() + 1, dropChess.getLocation().getY() + 1);
        if ((location.getX() < board.getRows() && location.getY() < board.getColumns()) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != color)) {
            for (int i = location.getX() + 1, j = location.getY() + 1; i < board.getRows() && j < board.getColumns(); ++i, ++j) {
                Chess chess = board.getChessByLocation(new ChessLocation(i, j));
                if (chess == null || chess.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (color == chess.getColor()) {
                    for (int x = location.getX(), y = location.getY(); x < i && y < j; ++x, ++y) {
                        reverseLocation.addElement(new ChessLocation(x, y));
                    }
                    return;
                }
            }
        }
    }

    private void canChessDropRightUp(Chess dropChess, ChessColor color) {
        ChessLocation location = new ChessLocation(dropChess.getLocation().getX() + 1, dropChess.getLocation().getY() - 1);
        if ((location.getX() < board.getRows() && location.getY() >= 0) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != color)) {
            for (int i = location.getX() + 1, j = location.getY() - 1; i < board.getRows() && j >= 0; ++i, --j) {
                Chess chess = board.getChessByLocation(new ChessLocation(i, j));
                if (chess == null || chess.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (color == chess.getColor()) {
                    for (int x = location.getX(), y = location.getY(); x < i && y > j; ++x, --y) {
                        reverseLocation.addElement(new ChessLocation(x, y));
                    }
                    return;
                }
            }
        }
    }

    private void canChessDropLeftDown(Chess dropChess, ChessColor color) {
        ChessLocation location = new ChessLocation(dropChess.getLocation().getX() - 1, dropChess.getLocation().getY() + 1);
        if ((location.getX() >= 0 && location.getY() < board.getColumns()) 
                && (board.getChessByLocation(location).getColor() != Chess.ChessColor.INVALID)
                && (board.getChessByLocation(location).getColor() != color)) {
            for (int i = location.getX() - 1, j = location.getY() + 1; i >= 0 && j < board.getColumns(); --i, ++j) {
                Chess chess = board.getChessByLocation(new ChessLocation(i, j));
                if (chess == null || chess.getColor() == Chess.ChessColor.INVALID) {
                    break;
                }
                if (color == chess.getColor()) {
                    for (int x = location.getX() , y = location.getY(); x > i && y < j; --x, ++y) {
                        reverseLocation.addElement(new ChessLocation(x, y));
                    }
                    return;
                }
            }
        }
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    public ChessBoard getBoard() {
        return board;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public Vector<ChessLocation> getCanReverseLocation() {
        return reverseLocation;
    }

    public Vector<ChessLocation> getPossibleLocation(ChessColor color) {

        Vector<ChessLocation> possibleMoves = new Vector();
        for (Chess chess : board.getChesses()) {
            if ((chess.getColor() == ChessColor.INVALID)
                    && canChessDrop(chess, color)) {
                possibleMoves.addElement(chess.getLocation());
            }
        }

        return possibleMoves;
    }

    public int getChessCount(ChessColor color) {
        if (color == ChessColor.BLACK) {
            return board.getBlackChessCount();
        } else if (color == ChessColor.WHITE) {
            return board.getWhiteChessCount();
        } else {
            return 0;
        }
    }
}