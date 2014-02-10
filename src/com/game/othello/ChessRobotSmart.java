package com.game.othello;

import com.game.othello.Chess.ChessColor;

/*
 * The smart robot use min-max search to find the best location.
 */
public class ChessRobotSmart extends ChessRobotInterface {

    private int maxDepth = 6;

    public ChessRobotSmart(ChessColor chessColor) {
        super(chessColor);
    }

//    { 30, -25, 10, 5, 5, 10, -25,  30,}
//    {-25, -25,  1, 1, 1,  1, -25, -25,}
//    { 10,   1,  5, 2, 2,  5,   1,  10,}
//    {  5,   1,  2, 1, 1,  2,   1,   5,}
//    {  5,   1,  2, 1, 1,  2,   1,   5,}
//    { 10,   1,  5, 2, 2,  5,   1,  10,}
//    {-25, -25,  1, 1, 1,  1, -25, -25,}
//    { 30, -25, 10, 5, 5, 10, -25,  30,}
//    public void setChessValue(ChessRule rule) {
//        ChessBoard board = rule.getBoard();
//        for (Chess chess : board.getChesses()) {
//            if (isConner(chess)) {
//                chess.setValue(30);
//            } else if() {
//                chess.setValue(-25);
//            }
//        }
//    }

    // return chess.value
    private int evaluate(Chess chess) {
        return chess.getValue();
    }

    private ChessLocation findBestLocation(ChessBoard board, ChessColor currentColor) {
//      /** maximum depth of search reached, we stop */
//      if(depth >= max_depth) return null;
  //
//      //player = (depth+1)%2 + 1;
  //
//      /** getting a list of moves to chose from */
//      ArrayList <Field> moves = findAllPossibleMoves(gb, player); 
  //
//      Field best_move = null;
  //
//      /** iterating over all possible moves, to find the best one */      
//      for (int i=0; i<moves.size(); i++)
//      {
//          /** board to simulate moves */
//          GameBoard temp_board = new GameBoard(gb);
//          /** getting the current move */
//          Field move = moves.get(i);      
//          /** simulating the move for the current node */
//          game.move(move, temp_board, player);
//          Log.i("board", "Depth:"+depth+" Player:"+player+" Move:"+i+" Rating:"+evaluate(temp_board));
//          Log.i("board", ""+moves.get(i).getX()+","+moves.get(i).getY());         
//          temp_board.printBoard();
//          /** getting to the next inferior node */            
//          Field best_deep_move = findBestMove (temp_board, depth + 1, !player);           
  //
//          /** if the maximum depth is reached, we have a null, so we evaluate */
//          if (best_deep_move == null) 
//          {
//              move.setRating(evaluate (temp_board));
//          }
//          /** if we are not the deepest possible, we get the rating from the lower node */
//          else 
//          {
//              move.setRating(best_deep_move.getRating());         
//              Log.i("eval", ""+best_deep_move.getRating());
//          }           
//          if(best_move == null) 
//          {
//              best_move = move;           
//          }
  //
//          else
//          {   
//              Log.i("update", "Current move rating:"+move.getRating());
//              Log.i("update", "New move rating:"+best_move.getRating());
//              if (depth%2==0)
//              {
//                  Log.i("update", "MAX player");
//                  /** for us, we look for the maximum */
//                  if (best_move.getRating() < move.getRating()) 
//                      {
  //
  //
//                      best_move = move;
  //
//                      }
  //
//              }
//              else
//              {
//                  Log.i("update", "MIN player");
//                  /** for the opponent, we look for the minimum */
//                  if (best_move.getRating() > move.getRating())
//                  { 
  //
  //
//                      best_move = move;
  //
//                  }
//              }
//              Log.i("update", "Updated move rating"+best_move.getRating());
//          }
//      }
  //
//      return best_move;
        return new ChessLocation(0, 0);
    }

    @Override
    public ChessLocation getLocation(ChessRule rule) {
        // TODO Auto-generated method stub
        return super.getLocation(rule);
    }


}
