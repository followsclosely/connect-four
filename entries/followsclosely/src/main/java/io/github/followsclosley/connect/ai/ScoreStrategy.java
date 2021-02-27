package io.github.followsclosley.connect.ai;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;
import io.github.followsclosley.connect.ai.grader.*;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This strategy will assign a score to each option, then select the best option.
 * Wins 99.92% of games against a random AI.
 */
public class ScoreStrategy implements ArtificialIntelligence {

    private int color;
    private int[] opponents;
    private List<Grader> graders = new ArrayList<>();

    public ScoreStrategy(int color, int... opponents) {
        this.color = color;
        this.opponents = opponents;

        graders.add(new CenterColumnGrader());
        graders.add(new WinnerGrader());
        graders.add(new LooseNextTurnGrader());
        graders.add(new WinnerNextTurnGrader());
        graders.add(new OneMorePieceToWinGrader());
        graders.add(new InRowGrader());
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public int yourTurn(Board b) {

        MutableBoard board = new MutableBoard(b);

        int maxIndex = 0;
        int maxScore = -1;
        int[] scores = new int[board.getWidth()];

        for (int x = 0, width = board.getWidth(); x < width; x++) {
            if (board.canDropPiece(x)) {
                board.dropPiece(x, getColor());
                scores[x] = scoreMove(board, board.getLastMove());
                if (maxScore < scores[x]) {
                    maxScore = scores[x];
                    maxIndex = x;
                }
                board.undo();
            }
        }

        return maxIndex;
    }

    public int scoreMove(MutableBoard board, Coordinate lastTurn) {

        int score = 1;

        Turn thisTurn = TurnUtils.getConnections(board);
        for (Grader grader : graders) {
            score += grader.score(board, thisTurn, opponents);
        }

        return score;
    }
}