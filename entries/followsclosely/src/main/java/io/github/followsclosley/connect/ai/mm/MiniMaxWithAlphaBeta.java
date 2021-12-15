package io.github.followsclosley.connect.ai.mm;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;
import io.github.followsclosley.connect.impl.ai.Dummy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MiniMaxWithAlphaBeta implements ArtificialIntelligence {

    private final BoardEvaluator evaluator = new BoardEvaluator();
    private final int you;
    private final int depth;
    private long timeout = 1;
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    private int opponent;

    public MiniMaxWithAlphaBeta(int color, int depth) {
        this.depth = depth;
        this.you = color;
    }

    @Override
    public int yourTurn(Board b) {

        int size = b.getWidth();

        //If you can win next turn then win.
        int winningColumn = getWinningColumn(new MutableBoard(b));
        if (winningColumn != -1) {
            return winningColumn;
        }

        final ExecutorService executorService = Executors.newFixedThreadPool(size);
        final AtomicInteger bestValueReference = new AtomicInteger(Integer.MIN_VALUE);
        final AtomicInteger bestColumnReference = new AtomicInteger(-1);

        //Play each piece and locate the bestValue play.
        for (int x = 0; x < size; x++) {
            MutableBoard board = new MutableBoard(b);
            if (board.canDropPiece(x)) {
                final int column = x;
                executorService.execute(() -> {
                    if (board.canDropPiece(column)) {
                        board.dropPiece(column, you);
                        int value = min(board, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        synchronized (bestValueReference) {
                            if (value > bestValueReference.get()) {
                                bestValueReference.set(value);
                                bestColumnReference.set(column);
                            }
                        }
                    }
                });
            }
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(timeout, timeUnit)) {
                executorService.shutdownNow();
                System.out.println("ERROR: Timeout has occurred, returning random move! ");
            }

            return bestColumnReference.get();
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            System.out.println("ERROR: InterruptedException has occurred, returning random move! ");
        }

        return new Dummy(you).yourTurn(b);
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

        if (depth == 0 || b.getTurnsLeft() == 0) {
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

        if (depth == 0 || b.getTurnsLeft() == 0) {
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

    private int getWinningColumn(MutableBoard board) {
        for (int x = 0, size = board.getWidth(); x < size; x++) {
            int y = board.dropPiece(x, you);
            if (y >= 0) {
                Turn turn = TurnUtils.getConnections(board);
                if (turn.hasWinningLine(board.getGoal())) {
                    return x;
                }
                board.undo();
            }
        }
        return -1;
    }

    @Override
    public int getColor() {
        return you;
    }

    @Override
    public void initialize(int opponent) {
        this.opponent = opponent;
    }

    public MiniMaxWithAlphaBeta setTimeout(long timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        return this;
    }
}
