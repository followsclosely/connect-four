package io.github.followsclosley.connect.ai.mm;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;
import io.github.followsclosley.connect.impl.ai.Dummy;

import java.awt.image.ColorConvertOp;
import java.util.Arrays;

public class MiniMaxWithAlphaBeta implements ArtificialIntelligence {

    private final BoardEvaluator evaluator = new BoardEvaluator();

    private final int you;
    private final int depth;
    private int opponent;

    public MiniMaxWithAlphaBeta(int color, int depth) {
        this.depth = depth;
        this.you = color;
    }

    @Override
    public int yourTurn(Board b) {

        MutableBoard board = new MutableBoard(b);

        int size = b.getWidth();

        int bestMove = 4;
        int bestValue = Integer.MIN_VALUE;

        int[] scores = new int[size];
        //Play each piece and locate the bestValue play.
        for (int x = 0; x < size; x++) {
            if (board.canDropPiece(x)) {
                board.dropPiece(x, you);

                int moveValue = min(board, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (moveValue > bestValue) {
                    bestMove = x;
                    bestValue = moveValue;
                }
                scores[x] = moveValue;
                board.undo();
            }
        }

        //System.out.println(Arrays.toString(scores));

        return bestMove;
    }

    /**
     * Play moves on the board alternating between playing as <code>shape</code> (maximising player) and
     * <code>opponent</code> (minimizing player), analysing the board each time to return the value of the highest
     * value move for the maximising player. Use variables alpha and  beta as the best alternative for the maximising
     * player and the best alternative for the minimising player respectively. Do not search nodes if player's
     * alternatives are better than the current node. Return the highest value move when a terminal node or the maximum
     * search depth is reached.
     *
     * @param b     Board to play on and evaluate
     * @param depth The maximum depth of the game tree to search
     * @param alpha The best value for the maximising player
     * @param beta  The best value for the minimising player
     * @return Value of the board
     * @see #min(MutableBoard, int, int, int)
     */
    public int max(MutableBoard b, int depth, int alpha, int beta) {

        if (TurnUtils.getConnections(b).hasWinningLine(4)) {
            //System.out.println("min("+currentX+":"+currentX2+"/" + senerios +"): score = " + evaluator.score(b, you) +" - "+ evaluator.score(b, opponent));
            return Integer.MIN_VALUE;
        }

        if (depth == 0 || b.getTurnsLeft() == 0 ) {
            //System.out.println("max("+currentX+":"+currentX2+"/" + senerios +"): score = " + evaluator.score(b, you) +" - "+ evaluator.score(b, opponent));
            return evaluator.score(b, you) - evaluator.score(b, opponent);
        }

        int highestVal = Integer.MIN_VALUE;
        for (int x = 0, size = b.getWidth(); x < size; x++) {
            if (b.canDropPiece(x)) {
                b.dropPiece(x, you);
                highestVal = Math.max(highestVal, min(b, depth - 1, alpha, beta));
                b.undo();
                if ((alpha = Math.max(alpha, highestVal)) >= beta) {
                    return highestVal;
                }
            }
        }

        return highestVal;
    }

    /**
     * Play moves on the board alternating between playing as <code>opponent</code> (minimizing player) and
     * <code>shape</code> (maximising player), analysing the board each time to return the value of the lowest
     * value move for the maximising player. Use variables alpha and beta as the best alternative for the minimising
     * player and the best alternative for the maximising player respectively. Do not search nodes if player's
     * alternatives are better than the current node. Return the lowest value move when a terminal node or the maximum
     * search depth is reached.
     *
     * @param b     Board to play on and evaluate
     * @param depth The maximum depth of the game tree to search
     * @param alpha The best value for the maximising player
     * @param beta  The best value for the minimising player
     * @return Value of the board
     * @see #max(MutableBoard, int, int, int)
     */
    public int min(MutableBoard b, int depth, int alpha, int beta) {

        if (TurnUtils.getConnections(b).hasWinningLine(4)) {
            //System.out.println("min("+currentX+":"+currentX2+"/" + senerios +"): score = " + evaluator.score(b, you) +" - "+ evaluator.score(b, opponent));
            return Integer.MAX_VALUE;
        }

        if (depth == 0 || b.getTurnsLeft() == 0 ) {
            //System.out.println("min("+currentX+":"+currentX2+"/" + senerios +"): score = " + evaluator.score(b, you) +" - "+ evaluator.score(b, opponent));
            return evaluator.score(b, you) - evaluator.score(b, opponent);
        }

        int lowestVal = Integer.MAX_VALUE;
        for (int x = 0, size = b.getWidth(); x < size; x++) {
            if (b.canDropPiece(x)) {
                b.dropPiece(x, opponent);
                lowestVal = Math.min(lowestVal, max(b, depth - 1, alpha, beta));
                b.undo();
                if ((beta = Math.min(beta, lowestVal)) <= alpha) {
                    return lowestVal;
                }
            }
        }

        return lowestVal;
    }

    @Override
    public int getColor() {
        return you;
    }

    @Override
    public void initialize(int opponent) {
        this.opponent = opponent;
    }
}
