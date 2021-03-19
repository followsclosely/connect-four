package io.github.lane;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

import javax.sound.sampled.Line;

public class LaneMiniMax implements ArtificialIntelligence {
    private final int playerColor;
    private final int recursiveDepth;
    private int opponentColor;

    public LaneMiniMax(int playerColor, int recursiveDepth) {
        this.playerColor = playerColor;
        this.recursiveDepth = recursiveDepth;
    }

    @Override
    public int getColor() {
        return playerColor;
    }

    @Override
    public void initialize(int opponent) {
        this.opponentColor = opponent;
    }

    public int getOpponentColor() {
        return opponentColor;
    }

    public int getRecursiveDepth() {
        return recursiveDepth;
    }

    @Override
    public int yourTurn(Board b) {
        MutableBoard board = new MutableBoard(b);
        int[] columnOrder = new int[board.getWidth()];

        for (int i = 0; i < board.getWidth(); i++) {
            columnOrder[i] = board.getWidth()/2 + (1-2*(i%2))*(i+1)/2;
        }

        minimax(board, columnOrder, getRecursiveDepth(), Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        return 0;
    }

    public int minimax(MutableBoard board, int[] columnOrder, int depth, int alpha, int beta, boolean maximizingPlayer) {
        Turn turn = TurnUtils.getConnections(board);
        if (turn.hasWinningLine(board.getGoal()) || depth == 0 || isFull(board)) {
            return evaluation(board);
        }

        int value = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < board.getWidth(); i++) {

            if (board.canDropPiece(columnOrder[i])) {
                board.dropPiece(columnOrder[i], maximizingPlayer ? getColor() : getOpponentColor());

                if (maximizingPlayer) {
                    value = Integer.max(value, minimax(board, columnOrder,depth - 1, alpha, beta, false));
                    alpha = Integer.max(alpha, value);

                } else {
                    value = Integer.min(value, minimax(board, columnOrder, - 1, alpha, beta, true));
                    beta = Integer.min(beta, value);
                }

                if (alpha >= beta) {
                    board.undo();
                    break;
                }

                board.undo();
            }
        }

        return value;
    }

    public int evaluation(MutableBoard board) {
        Turn turn = TurnUtils.getConnections(board);

        if (turn.hasWinningLine(board.getGoal())) {
            return Integer.MAX_VALUE;
        }

        int score = 0;
        return 0;
    }

    public boolean isFull(MutableBoard board) {
        return board.getTurns().size() >= board.getWidth() * board.getHeight();
    }
}
