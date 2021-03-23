package io.github.followsclosley.connect.ai;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.ai.grader.BoardGrader;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MinMax implements ArtificialIntelligence {

    private final int color;
    private int opponent;
    private int[] recursiveOrder;

    private int maxDepth = 1;

    private BoardGrader boardGrader = new BoardGrader();
    //private ExecutorService executorService = Executors.newFixedThreadPool(7);

    private int counter = 0;

    public MinMax(int color, int depth) {
        this.color = color;
        this.maxDepth = depth;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void initialize(int opponent) {
        this.opponent = opponent;

        //TODO: Change interface to pass in the board to build this.
        recursiveOrder = new int[]{3,2,4,1,5,0,6};
    }

    @Override
    public int yourTurn(Board b) {
        counter = 0;
        int[] tuple = max(new MutableBoard(b), 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println("Returning " + tuple[0]);
        return tuple[0];
    }

    public int[] max(MutableBoard board, int depth, int alpha, int beta) {

        int width = board.getWidth();

        //Look for a win at this level. If found then just return and be done.
        for (int x = 0; x < width; x++) {
            if (board.dropPiece(x, color)  != -1) {
                Turn turn = TurnUtils.getConnections(board);
                board.undo();
                if (turn.hasWinningLine(board.getGoal())) {
                    System.out.printf("\rdepth = %d, counter = %d", depth, ++counter);
                    return new int[]{x, Integer.MAX_VALUE};
                }
            }
        }

        int[] scores = new int[width];
        for (int x = 0; x < width; x++) {
            if (board.canDropPiece(x)) {
                int y = board.dropPiece(x, color);
                System.out.printf("\rdepth = %d, counter = %d", depth, ++counter);

                if (depth >= maxDepth) {
                    scores[x] = boardGrader.grade(board, color, opponent);
                } else {
                    scores[x] = min(board, depth + 1, alpha, beta)[1];
                }

                if( scores[x] > alpha){
                    alpha = scores[x];
                }
                if( beta <= alpha){
                    board.undo();
                    break;
                }

                board.undo();
            }
        }

        int maxIndex = 0;
        int maxScore = Integer.MIN_VALUE;
        for (int x = 0; x < width; x++) {
            if (maxScore < scores[x]) {
                maxScore = scores[x];
                maxIndex = x;
            }
        }

        return new int[]{maxIndex, maxScore};
    }

    public int[] min(MutableBoard board, int depth, int alpha, int beta) {

        int width = board.getWidth();

        //Look for a win at this level. If found then just return and be done.
        for (int x = 0; x < width; x++) {
            if (board.dropPiece(x, opponent)  != -1) {
                Turn turn = TurnUtils.getConnections(board);
                board.undo();
                if (turn.hasWinningLine(board.getGoal())) {
                    System.out.printf("\rdepth = %d, counter = %d", depth, ++counter);
                    return new int[]{x, Integer.MIN_VALUE};
                }
            }
        }

        int[] scores = new int[width];
        for (int x = 0; x < width; x++) {
            if (board.canDropPiece(x)) {
                int y = board.dropPiece(x, opponent);

                System.out.printf("\rdepth = %d, counter = %d", depth, ++counter);

                if (depth >= maxDepth) {
                    scores[x] = boardGrader.grade(board, color, opponent);
                } else {
                    scores[x] = max(board, depth + 1, alpha, beta)[1];
                }

                if( scores[x] < beta){
                    alpha = scores[x];
                }
                if( beta <= alpha){
                    board.undo();
                    break;
                }

                board.undo();
            }
        }

        int minIndex = 0;
        int minScore = Integer.MAX_VALUE;
        for (int x = 0; x < width; x++) {
            if (minScore > scores[x]) {
                minScore = scores[x];
                minIndex = x;
            }
        }

        return new int[]{minIndex, minScore};
    }
}
